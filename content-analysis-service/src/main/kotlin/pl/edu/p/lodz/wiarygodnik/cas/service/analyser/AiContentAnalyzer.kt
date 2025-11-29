package pl.edu.p.lodz.wiarygodnik.cas.service.analyser

import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.service.agent.ContentCleanerAgent
import pl.edu.p.lodz.wiarygodnik.cas.service.agent.ContentSentimentAgent
import pl.edu.p.lodz.wiarygodnik.cas.service.agent.ContentSummarizationAgent
import pl.edu.p.lodz.wiarygodnik.cas.model.CleanedContent
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentAnalysis
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentSentiment
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentSummarization

@Component
class AiContentAnalyzer(
    private val contentCleanerAgent: ContentCleanerAgent,
    private val contentSummarizationAgent: ContentSummarizationAgent,
    private val contentSentimentAgent: ContentSentimentAgent,
): ContentAnalyser {

    override fun analyse(content: String): ContentAnalysis {
        val cleanedContent: CleanedContent = contentCleanerAgent.work(content)
        val summarization: ContentSummarization = contentSummarizationAgent.work(cleanedContent.text)
        val sentiment: ContentSentiment = contentSentimentAgent.work(cleanedContent.text)
        return ContentAnalysis(summarization, sentiment)
    }

}