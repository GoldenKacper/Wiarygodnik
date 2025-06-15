from sqlalchemy import Column, Integer, String, Text, JSON, TIMESTAMP, func
from .db import Base


class Content(Base):
    __tablename__ = "web_contents"

    id = Column(Integer, primary_key=True, index=True)
    url = Column(String(1000), nullable=False)
    content = Column(Text, nullable=True)
    keywords = Column(JSON, nullable=True)
    created_at = Column(TIMESTAMP(timezone=True), server_default=func.now(), nullable=False)
    report_id = Column(String(50), nullable=True)
