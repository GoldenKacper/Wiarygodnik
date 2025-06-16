from pydantic import BaseModel, constr, Field
from typing import List, Optional

class FetchRequest(BaseModel):
    url: constr(max_length=100) = Field(..., examples=["https://example.com"])
    report_id: Optional[constr(max_length=50)] = Field(
        None, examples=["REP-2025-0001"]
    )

class FetchResponse(BaseModel):
    url: constr(max_length=100)
    keywords: List[constr(strip_whitespace=True)]
    content: Optional[str] = None
    report_id: Optional[constr(max_length=50)] = Field(
        None, examples=["REP-2025-0001"]
    )

