package pl.edu.p.lodz.wiarygodnik.rgs.model.message

enum class Sentiment {
    NEUTRAL, POSITIVE, NEGATIVE, ALARMIST, IRONIC, PERSUASIVE, AGGRESSIVE, FORMAL
}

data class AnalysisResultMessage(
    val requestId: String,
    val userId: String,
    val sourceUrl: String,
    val contentAnalysis: ContentAnalysis,
    val contentComparison: ContentComparison?
)

data class ContentAnalysis(val summarization: ContentSummarization, val sentiment: SentimentSummaryDto)
data class ContentSummarization(val description: String, val keywords: Set<String>)

data class SentimentSummaryDto(val summary: String, val examples: List<SentimentExampleDto>)
data class SentimentExampleDto(val sentiment: Sentiment, val quotes: List<SentimentExampleQuoteDto>)
data class SentimentExampleQuoteDto(val quote: String, val explanation: String)

data class ContentComparison(val description: String, val sourcesFacts: List<SourceFacts>)
data class SourceFacts(val url: String, val facts: Set<String>)
