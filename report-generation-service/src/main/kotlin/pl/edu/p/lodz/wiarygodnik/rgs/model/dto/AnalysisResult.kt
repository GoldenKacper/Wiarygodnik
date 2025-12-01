package pl.edu.p.lodz.wiarygodnik.rgs.model.dto

enum class Sentiment {
    NEUTRAL, POSITIVE, NEGATIVE, ALARMIST, IRONIC, PERSUASIVE, AGGRESSIVE, FORMAL
}

data class AnalysisResult(
    val requestId: String,
    val sourceUrl: String,
    val contentAnalysis: ContentAnalysis,
    val contentComparison: ContentComparison
)

data class ContentAnalysis(val summarization: ContentSummarization, val sentiment: ContentSentiment)
data class ContentSummarization(val description: String, val keywords: List<String>)

data class ContentSentiment(val description: String, val examples: List<SentimentExample>)
data class SentimentExample(val sentiment: Sentiment, val quotes: List<CommentedQuote>)
data class CommentedQuote(val quote: String, val comment: String)

data class ContentComparison(val description: String, val sourcesFacts: List<SourceFacts>)
data class SourceFacts(val url: String, val facts: List<String>)



