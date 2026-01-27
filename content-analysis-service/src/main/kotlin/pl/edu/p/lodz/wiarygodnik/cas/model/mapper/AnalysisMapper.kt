package pl.edu.p.lodz.wiarygodnik.cas.model.mapper

import pl.edu.p.lodz.wiarygodnik.cas.model.Analysis
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.*

class AnalysisMapper {

    fun mapToDto(analysis: Analysis): AnalysisData {
        val sourceSummary = requireNotNull(analysis.sourceSummary) {
            "sourceSummary is required to map AnalysisData"
        }
        val sentimentSummary = requireNotNull(analysis.sentimentSummary) {
            "sentimentSummary is required to map AnalysisData"
        }
        val comparisonSummary = requireNotNull(analysis.comparisonSummary) {
            "comparisonSummary is required to map AnalysisData"
        }

        return AnalysisData(
            requestId = analysis.requestId,
            sourceUrl = analysis.sourceUrl,
            contentAnalysis = ContentAnalysis(
                summarization = ContentSummarization(
                    description = sourceSummary.summary,
                    keywords = sourceSummary.keywords
                ),
                sentiment = SentimentSummaryDto(
                    summary = sentimentSummary.summary,
                    examples = sentimentSummary.examples.map { example ->
                        SentimentExampleDto(
                            sentiment = example.sentiment,
                            quotes = example.sentimentExampleQuotes.map { quote ->
                                SentimentExampleQuoteDto(
                                    quote = quote.quote,
                                    explanation = quote.explanation
                                )
                            }
                        )
                    }
                )
            ),
            contentComparison = ContentComparison(
                description = comparisonSummary.summary,
                sourcesFacts = comparisonSummary.examples.map { example ->
                    SourceFacts(
                        url = example.sourceUrl,
                        facts = example.facts
                    )
                }
            )
        )
    }


}