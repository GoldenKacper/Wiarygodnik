import json
import asyncio
from contextlib import asynccontextmanager
from typing import List, Dict, Optional, Coroutine, Any

from aio_pika import connect_robust, Message, IncomingMessage, ExchangeType
from pydantic import ValidationError
import httpx
from sqlalchemy.exc import IntegrityError
from sqlalchemy.ext.asyncio import AsyncSession

from ..schemas import FetchRequest, FetchResponse
from ..db import AsyncSessionLocal
from ..models import Content
from ..config import settings   # DATABASE_URL & RABBITMQ_URL

REQUEST_QUEUE = settings.RABBITMQ_REQUEST_QUEUE
RESULT_QUEUE  = settings.RABBITMQ_RESULT_QUEUE
EXCHANGE      = settings.RABBITMQ_EXCHANGE

# ---------- narzędzie do pobierania i ekstrakcji (mock) ----------
async def _fetch_and_extract(url: str) -> tuple[str, List[str]]:
    # TODO: zastąpić realnym httpx + beautifulsoup + yake
    page_content = (
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac."
    )
    keywords = ["keyword1", "keyword2", "keyword3"]
    return page_content, keywords


# ---------- DB helper ----------
@asynccontextmanager
async def db_session():
    async with AsyncSessionLocal() as session:
        try:
            yield session
        finally:
            await session.close()

# ---------- główny handler wiadomości ----------
async def handle_request(msg: IncomingMessage) -> Coroutine[Any, Any, None]:
    async with msg.process(ignore_processed=True):
        try:
            print("[*] Received message:", msg.body.decode())
            raw = json.loads(msg.body.decode())

            # ujednolicamy: jeśli przyszedł pojedynczy obiekt, ubieramy w listę
            if not isinstance(raw, list):
                raw = [raw]

            batch: List[FetchRequest] = [FetchRequest(**item) for item in raw]
            print(batch)
        except (json.JSONDecodeError, ValidationError) as exc:
            print(f"[x] Invalid payload: {exc}")
            return

        results: List[Dict[str, Any]] = []
        async with db_session() as db:
            for req in batch:
                # 1) pobieranie
                try:
                    content, keywords = await _fetch_and_extract(req.url)
                except httpx.RequestError as exc:
                    print(f"[x] Request error: {exc}")
                    results.append(
                        {
                            "status": "error",
                            "reason": str(exc),
                            "url": req.url,
                            "reportId": req.reportId,
                        }
                    )
                    continue

                # 2) zapis do DB
                try:
                    row = Content(
                        url=req.url,
                        content=content,
                        keywords=keywords,
                        report_id=str(req.reportId),
                    )
                    db.add(row)
                    await db.commit()
                    await db.refresh(row)

                    # 3) sukces ⇒ serializujemy schematem Pydantic
                    results.append(
                        FetchResponse(
                            url=row.url,
                            keywords=keywords,
                            content=content,
                            reportId=int(req.reportId),
                        ).model_dump()
                    )

                except IntegrityError as exc:
                    print(f"[x] Integrity error: {exc}")
                    await db.rollback()
                    results.append(
                        {
                            "status": "conflict",
                            "reason": "duplicate (url, report_id)",
                            "url": req.url,
                            "reportId": req.reportId,
                        }
                    )

                except Exception as exc:
                    print(f"[x] DB error: {exc}")
                    await db.rollback()
                    results.append(
                        {
                            "status": "error",
                            "reason": f"DB error: {exc}",
                            "url": req.url,
                            "reportId": req.reportId,
                        }
                    )

        await publish_result(results)

# ---------- publish helper ----------
async def publish_result(body: list[dict]):
    connection = await connect_robust(host="rabbitmq")
    async with connection:
        channel = await connection.channel()
        await channel.declare_exchange(EXCHANGE, ExchangeType.TOPIC, durable=True)
        result_queue = await channel.declare_queue(RESULT_QUEUE, durable=True)
        await result_queue.bind(EXCHANGE, routing_key=RESULT_QUEUE)
        await channel.default_exchange.publish(
            Message(json.dumps(body).encode()),
            routing_key=RESULT_QUEUE,
        )

# ---------- główna pętla konsumenta ----------
async def run_consumer() -> None:
    print("[*] Starting RabbitMQ consumer...")
    try:
        connection = await connect_robust(host="rabbitmq")
    except Exception as exc:
        print(f"[x] Failed to connect to RabbitMQ: {exc}")
        return
    print("[*] Connected to RabbitMQ at", settings.RABBITMQ_URL)
    async with connection:
        try:
            channel = await connection.channel()
            await channel.declare_exchange(EXCHANGE, ExchangeType.TOPIC, durable=True)
            queue = await channel.declare_queue(REQUEST_QUEUE, durable=True)
            await queue.bind(EXCHANGE, routing_key=REQUEST_QUEUE)
            print("[*] Waiting for messages (batch mode). To exit press CTRL+C")
            await queue.consume(handle_request)
            await asyncio.Future()            # run forever
        except Exception as exc:
            print(f"[x] Error in consumer: {exc}")
            await connection.close()
            raise


# Przykładowy payload do kolejki `content.fetch.request`
# [
#   {"url": "https://www.lipsum.com/feed/html", "report_id": "rep1"},
#   {"url": "https://example.com"},
#   {"url": "https://bad.example.com"}      // zasymuluje błąd 404
# ]

# Wynik w kolejce `content.fetch.result` będzie wyglądał tak:
# [
#   {
#     "url": "https://www.lipsum.com/feed/html",
#     "keywords": ["keyword1", "keyword2", "keyword3"],
#     "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac.",
#     "report_id": "rep1"
#   },
#   {
#     "url": "https://example.com",
#     "keywords": ["keyword1", "keyword2", "keyword3"],
#     "content": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac.",
#     "report_id": null
#   },
#   {
#     "status": "error",
#     "reason": "404 Client Error: Not Found for url",
#     "url": "https://bad.example.com",
#     "report_id": null
#   }
# ]