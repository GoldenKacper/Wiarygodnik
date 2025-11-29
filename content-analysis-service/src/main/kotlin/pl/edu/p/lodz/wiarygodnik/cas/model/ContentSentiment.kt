package pl.edu.p.lodz.wiarygodnik.cas.model

data class ContentSentiment(val description: String, val examples: List<SentimentExample>)
data class SentimentExample(val sentiment: Sentiment, val quotes: List<CommentedQuote>)
data class CommentedQuote(val quote: String, val comment: String)
