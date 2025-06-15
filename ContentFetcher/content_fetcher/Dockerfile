FROM python:3.13-slim

LABEL version="1.0"
LABEL description="Content Fetcher Service"
LABEL org.opencontainers.image.authors="kacperjagodzinski@op.pl"
LABEL author="Kacper Jagodziński"

WORKDIR /app

COPY requirements.txt .

RUN pip install --no-cache-dir -r requirements.txt

COPY app/ ./app/

COPY alembic.ini .
COPY alembic/ ./alembic/

CMD ["uvicorn", "app.main:app", "--host", "0.0.0.0", "--port", "8080"]
