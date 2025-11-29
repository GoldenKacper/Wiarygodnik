package pl.edu.p.lodz.wiarygodnik.cas.service.comparator

import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.service.agent.ContentCleanerAgent
import pl.edu.p.lodz.wiarygodnik.cas.service.agent.ContentComparisonAgent
import pl.edu.p.lodz.wiarygodnik.cas.service.agent.ContentSummarizationAgent
import pl.edu.p.lodz.wiarygodnik.cas.model.CleanedContent
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentComparison
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentSummarization
import pl.edu.p.lodz.wiarygodnik.cas.model.ScrapedWebContent

@Component
class AiContentComparator(
    private val contentCleanerAgent: ContentCleanerAgent,
    private val contentSummarizationAgent: ContentSummarizationAgent,
    private val contentComparisonAgent: ContentComparisonAgent
) : ContentComparator {

    override fun compare(
        mainSourceDescription: String,
        similarSourceContents: List<ScrapedWebContent>
    ): ContentComparison {
        var input: String = """
            GŁÓWNE ŹRÓDŁO:
            ${mainSourceDescription}
            
            PODOBNE ŹRÓDŁA:
            
        """.trimIndent()
        for (content in similarSourceContents) {
            val cleanedContent: CleanedContent = contentCleanerAgent.work(content.text)
            val summarization: ContentSummarization = contentSummarizationAgent.work(cleanedContent.text)
            input += """
                
                URL: ${content.url}
                ${summarization.description}
                
            """.trimIndent()
        }
        return contentComparisonAgent.work(input)
    }

}