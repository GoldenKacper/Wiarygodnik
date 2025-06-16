from pydantic import BaseModel, constr, Field
from typing import List, Optional

class FetchRequest(BaseModel):
    url: constr(max_length=100) = Field(..., examples=["https://example.com"])
    reportId: int

class FetchResponse(BaseModel):
    url: constr(max_length=100)
    keywords: List[constr(strip_whitespace=True)]
    content: Optional[str] = None
    reportId: int

