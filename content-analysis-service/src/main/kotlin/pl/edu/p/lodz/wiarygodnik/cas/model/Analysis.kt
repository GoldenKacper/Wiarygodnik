package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.ContentAnalysis
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.ContentComparison

enum class AnalysisStatus {
    ANALYSING_CONTENT, COMPARING_SIMILAR_SOURCES, COMPLETED, FAILED
}

@Entity
class Analysis(
    val requestId: String,
    val userId: String,
    @Column(columnDefinition = "TEXT") var sourceUrl: String,
    @Enumerated(EnumType.STRING) var status: AnalysisStatus,
    @OneToOne(cascade = [CascadeType.ALL]) var sourceSummary: SourceSummary? = null,
    @OneToOne(cascade = [CascadeType.ALL]) var sentimentSummary: SentimentSummary? = null,
    @OneToOne(cascade = [CascadeType.ALL]) var comparisonSummary: ComparisonSummary? = null,
    @Id @GeneratedValue(strategy = IDENTITY) var id: Long? = null
) {

    fun fillWith(contentAnalysis: ContentAnalysis) {
        val sourceSummary = SourceSummary(
            analysis = this,
            summary = contentAnalysis.summarization.description,
            keywords = contentAnalysis.summarization.keywords
        )

        val sentimentSummary = SentimentSummary(
            analysis = this,
            summary = contentAnalysis.sentiment.summary
        )

        val sentimentExamples = contentAnalysis.sentiment.examples.map { example ->
            val sentimentExample = SentimentExample(
                sentimentSummary = sentimentSummary,
                sentiment = example.sentiment
            )

            val quotes = example.quotes.map { (quote, comment) ->
                SentimentExampleQuote(
                    quote = quote,
                    explanation = comment,
                    sentimentExample = sentimentExample
                )
            }.toMutableList()

            sentimentExample.sentimentExampleQuotes = quotes

            sentimentExample
        }.toMutableList()

        sentimentSummary.examples = sentimentExamples

        this.sourceSummary = sourceSummary
        this.sentimentSummary = sentimentSummary
    }

    fun fillWith(contentComparison: ContentComparison) {
        val comparisonSummary = ComparisonSummary(
            analysis = this,
            summary = contentComparison.description
        )

        val comparisonSourceFacts = contentComparison.sourcesFacts.map {
            ComparisonSourceFacts(
                comparisonSummary = comparisonSummary,
                sourceUrl = it.url,
                facts = it.facts
            )
        }.toMutableList()

        comparisonSummary.examples = comparisonSourceFacts

        this.comparisonSummary = comparisonSummary
    }

}