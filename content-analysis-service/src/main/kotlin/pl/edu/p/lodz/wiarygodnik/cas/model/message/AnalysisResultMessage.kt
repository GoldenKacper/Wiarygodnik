package pl.edu.p.lodz.wiarygodnik.cas.model.message

import pl.edu.p.lodz.wiarygodnik.cas.model.dto.ContentAnalysis
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.ContentComparison

data class AnalysisResultMessage(
    val requestId: String,
    val userId: String,
    val sourceUrl: String,
    val contentAnalysis: ContentAnalysis,
    val contentComparison: ContentComparison?
)
