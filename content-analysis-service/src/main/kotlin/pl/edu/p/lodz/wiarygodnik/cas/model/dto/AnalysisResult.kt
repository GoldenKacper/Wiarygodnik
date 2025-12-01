package pl.edu.p.lodz.wiarygodnik.cas.model.dto

data class AnalysisResult(
    val requestId: String,
    val sourceUrl: String,
    val contentAnalysis: ContentAnalysis,
    val contentComparison: ContentComparison
)
