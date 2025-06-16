import httpx
import asyncio
import contextlib
from typing import List
from contextlib import asynccontextmanager
from fastapi import FastAPI, Body, Depends, HTTPException, status
from sqlalchemy.exc import IntegrityError
from sqlalchemy.ext.asyncio import AsyncSession

from .db import get_db
from .models import Content
from .schemas import FetchRequest, FetchResponse
from .messaging.rabbit import run_consumer


@asynccontextmanager
async def lifespan(app: FastAPI):
    # start-up
    loop = asyncio.get_event_loop()
    consumer_task = loop.create_task(run_consumer())
    yield
    # shut-down
    consumer_task.cancel()
    with contextlib.suppress(asyncio.CancelledError):
        await consumer_task

app = FastAPI(lifespan=lifespan)


@app.post(
    "/fetch",
    response_model=FetchResponse,
    summary="Fetch page content & extract keywords",
    description=(
        "Fetches content from the provided URL, extracts keywords, "
        "and saves the content to the database. Returns the URL, "
        "extracted keywords, page's content, and optionally a report ID (if provided)."
    ),
    status_code=status.HTTP_200_OK,
    responses={
        200: {"description": "Content fetched and keywords extracted"},
        422: {"description": "Validation error"},
        404: {"description": "URL unreachable"},
        409: {"description": "Duplicate entry"},
        500: {"description": "Internal server error"},
    },
)
async def fetch_url(
    body: FetchRequest = Body(
        ...,
        title="FetchRequest schema",
        description=(
            "URL (≤1000 characters) for processing and optional identifier of the report (≤50 characters)."
        ),
        examples=[
            {
                "url": "https://www.lipsum.com/feed/html",
                "report_id": "report_1"
            },
            {
                "url": "https://www.youtube.com/watch?v=X6n3O7up83I",
            }
        ],
        embed=False,
    ),
    db: AsyncSession = Depends(get_db),
):
    """
    **Opis**
    - Przyjmuje URL, pobiera treść (mock), generuje listę słów kluczowych
    - Zapisuje rekord w tabeli `web_contents`
    - Zwraca 201 na sukces oraz url, listę słów kluczowych jako json oraz treść strony, ew. pozostałe kody przy błędach

    **Możliwe błędy**
    - **422** – walidacja Pydantic
    - **404** – strona niedostępna
    - **409** – konflikt (url, report_id)
    - **500** – błąd wewnętrzny serwera
    """
    # ── 1. (Mock) Pobranie treści i ekstrakcja słów kluczowych ──────────────
    try:
        # realnie: resp = await httpx.get(body.url, timeout=10)
        # if resp.status_code >= 400: raise HTTPException(status_code=404, …)
        page_content = (
            "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Vivamus ac."
        )
    except httpx.RequestError as exc:  # sieć/down → 404
        raise HTTPException(status_code=404, detail=str(exc))

    keywords: List[str] = ["keyword1", "keyword2", "keyword3"]

    # W prawdziwej aplikacji tutaj byłby kod do pobrania treści z URL i ekstrakcji słów kluczowych
    # TODO: Implement actual content fetching and keyword extraction logic

    # ── 2. Zapis do bazy z obsługą wyjątków ─────────────────────────────────
    try:
        row = Content(
            url=body.url,
            content=page_content,
            keywords=keywords,
            report_id=body.report_id,
        )
        db.add(row)
        await db.commit()
        await db.refresh(row)

    except IntegrityError as exc:  # unikalność / not null
        await db.rollback()
        raise HTTPException(
            status_code=status.HTTP_409_CONFLICT,
            detail=f"Unexpected conflict (url, report_id)\n{str(exc)}",
        )

    except Exception as exc:
        await db.rollback()
        raise HTTPException(
            status_code=500,
            detail=f"Error writing to database.\n{str(exc)}",
        )

    # ── 3. Zwróć odpowiedź ────────────────────────────────────────────────
    return FetchResponse(
        url=row.url,
        keywords=keywords,
        content=page_content,
        report_id=row.report_id,
    )
