from pydantic_settings import BaseSettings, SettingsConfigDict

class Settings(BaseSettings):
    DATABASE_URL: str
    RABBITMQ_URL: str
    RABBITMQ_REQUEST_QUEUE: str = "content.fetch.request"
    RABBITMQ_RESULT_QUEUE: str = "content.fetch.result"
    RABBITMQ_EXCHANGE: str = "content.events"

    model_config = SettingsConfigDict(env_file=".env")

settings = Settings()
