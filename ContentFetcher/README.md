# Content Fetcher Service

> Mikroserwis Projektu **Wiarygodnik** odpowiedzialny za pobieranie treści stron WWW, ekstrakcję słów kluczowych i publikowanie wyniku w architekturze event‑driven (RabbitMQ).

---

## Spis treści

1. [Wymagania](#wymagania)
2. [Szybki start](#szybki-start)
3. [Plik ](#plik-env)[`.env`](#plik-env)
4. [Struktura projektu](#struktura-projektu)
5. [Format komunikatów](#format-komunikatów)
6. [Przydatne komendy](#przydatne-komendy)
7. [Jak podejrzeć bazę danych](#jak-podejrzeć-bazę-danych)

---

## Wymagania

- **Docker 20.10+**
- **Docker Compose v2** (wbudowany w Docker Desktop)
-  Plik `.env` w katalogu `content_fetcher/` (może być skopiowany z `.env.example`).

---

## Szybki start

```bash
# 1. Klonujesz repozytorium (jeśli to zewnętrzny klon)
# git clone ...

# 2. Uzupełniasz zmienne środowiskowe
cp content_fetcher/.env.example content_fetcher/.env
nano content_fetcher/.env   # wstaw hasła / URL brokerów

# 3. Budujesz i uruchamiasz wszystkie serwisy
docker compose up -d --build
```

Po kilku sekundach:

- kontener **postgres** podnosi bazę i stosuje migracje Alembic,
- kontener **content\_fetcher** słucha kolejki `content.fetch.request` oraz wystawia HTTP (`8090`).

---

## Plik `.env`

| Klucz                    | Opis                             | Przykład                                                                 |
| ------------------------ | -------------------------------- | ------------------------------------------------------------------------ |
| `DATABASE_URL`           | URL do bazy PostgreSQL (asyncpg) | `postgresql+asyncpg://wiarygodnik:secret@postgres:5432/contentfetcherdb` |
| `RABBITMQ_URL`           | Adres brokera                    | `amqp://guest:guest@rabbitmq:5672/`                                      |
| `RABBITMQ_REQUEST_QUEUE` | Kolejka wejściowa                | `content.fetch.request`                                                  |
| `RABBITMQ_RESULT_QUEUE`  | Kolejka wyjściowa                | `content.fetch.result`                                                   |
| `RABBITMQ_EXCHANGE`      | Exchange typu **direct**         | `content.events`                                                         |

---

## Struktura projektu

```
Content-Fetcher/
├── docker-compose.yml      # konfiguracja Docker Compose
└── content_fetcher/
    ├── alembic/            # migracje bazy (kopiowane do obrazu)
    │   └── versions/ …
    ├── app/
    │   ├── main.py         # FastAPI + lifespan
    │   ├── messaging/      # kod RabbitMQ (aio‑pika)
    │   ├── services/       # logika biznesowa
    │   ├── models.py       # SQLAlchemy ORM
    │   ├── schemas.py      # Pydantic
    │   └── db.py           # sesja AsyncSession
    ├── requirements.txt    # zależności Pythona
    ├── .env                # plik konfiguracyjny (wymagany)
    ├── alembic.ini         # konfiguracja Alembica
    └── Dockerfile
```

Migracje uruchamiają się automatycznie przy starcie kontenera dzięki poleceniu

```sh
alembic upgrade head
```

---

## Format komunikatów

### Wejście → kolejka `content.fetch.request`

Lista **1..N** obiektów:

```json
[
  {"url": "https://www.lipsum.com/feed/html", "report_id": "rep1"},
  {"url": "https://example.com"},
  {"url": "https://bad.example.com"}
]
```

`url` – max 1000 znaków,\
`report_id` – opcjonalnie, max 50 znaków.

### Wyjście ← kolejka `content.fetch.result`

Również lista; dla każdego elementu wejściowego zwracamy:

```json
[
  {
    "url": "https://www.lipsum.com/feed/html",
    "keywords": ["keyword1", "keyword2", "keyword3"],
    "content": "Lorem ipsum dolor sit amet…",
    "report_id": "rep1"
  },
  {
    "url": "https://example.com",
    "keywords": ["keyword1", "keyword2", "keyword3"],
    "content": "Lorem ipsum dolor sit amet…",
    "report_id": null
  },
  {
    "status": "error",
    "reason": "404 Client Error: Not Found for url",
    "url": "https://bad.example.com",
    "report_id": null
  }
]
```

Sukces → pełny `FetchResponse`.\
Błąd (sieć / DB / duplicate) → obiekt z `status` i `reason`.

---

## Przydatne komendy

```bash
# zatrzymaj i usuń kontenery (+ sieci, ale bez wolumenów)
docker compose down

# wyczyść kontenery i wolumen (uwaga: traci się dane)
docker compose down -v

# podejrzyj logi tylko usługi content_fetcher
docker compose logs -f content_fetcher

# ręczne wywołanie endpointu HTTP
auth_json='{"url": "https://example.com"}'
http POST localhost:8090/fetch url=https://example.com
```

---

## Jak podejrzeć bazę danych

```bash
# wejdź do kontenera Postgresa
docker compose exec postgres bash

# zaloguj się do bazy
psql -U wiarygodnik -d contentfetcherdb

# sprawdź strukturę i dane
d+ web_contents;          -- opis tabeli (struktura)
SELECT COUNT(*) FROM web_contents;
SELECT url, report_id FROM web_contents ORDER BY created_at DESC;
```

Wyjście z `psql` – polecenie `\q`; wyjście z kontenera – `exit`.

> **Tip:** migracje Alembica są idempotentne – ponowny start kontenera nie psuje istniejącej bazy.

---

## FAQ

- **Czy muszę sam uruchamiać migracje?**\
  Nie – `alembic upgrade head` jest odpalane przy starcie `content_fetcher`.
- **Jak dodać nową kolumnę?**
  1. Zmień `models.py`, 2) `alembic revision --autogenerate -m "..."`, 3) `docker compose up -d --build`.
- **Jak przetestować RabbitMQ?**\
  Wejdź na `http://localhost:15672` (guest/guest) i użyj zakładki *Queues* → *Publish message*.

---

> Projekt rozwijany w ramach **studiów magisterskich** 😉

