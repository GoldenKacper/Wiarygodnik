package pl.edu.p.lodz.wiarygodnik.cas.model.dto

enum class Sentiment {
    NEUTRAL, POSITIVE, NEGATIVE, ALARMIST, IRONIC, PERSUASIVE, AGGRESSIVE, FORMAL
}

data class SentimentSummaryDto(val summary: String, val examples: List<SentimentExampleDto>)
data class SentimentExampleDto(val sentiment: Sentiment, val quotes: List<SentimentExampleQuoteDto>)
data class SentimentExampleQuoteDto(val quote: String, val explanation: String)
