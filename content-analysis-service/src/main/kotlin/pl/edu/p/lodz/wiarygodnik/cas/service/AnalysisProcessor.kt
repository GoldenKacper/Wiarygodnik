package pl.edu.p.lodz.wiarygodnik.cas.service

import io.github.oshai.kotlinlogging.KotlinLogging
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.amqp.RabbitMQProducer
import pl.edu.p.lodz.wiarygodnik.cas.model.Analysis
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisStatus.*
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.AnalysisResultMessage
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.ContentAnalysis
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.ContentComparison
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.ScrapedWebContent
import pl.edu.p.lodz.wiarygodnik.cas.service.analyser.ContentAnalyser
import pl.edu.p.lodz.wiarygodnik.cas.service.comparator.ContentComparator
import pl.edu.p.lodz.wiarygodnik.cas.service.scraper.WebScraper
import pl.edu.p.lodz.wiarygodnik.cas.service.searcher.KeywordWebSearcher
import kotlin.coroutines.CoroutineContext

@Component
class AnalysisProcessor(
    private val webScraper: WebScraper,
    private val contentAnalyzer: ContentAnalyser,
    private val keywordWebSearcher: KeywordWebSearcher,
    private val contentComparator: ContentComparator,
    private val analysisService: AnalysisService,
    private val producer: RabbitMQProducer
) : CoroutineScope {

    private val log = KotlinLogging.logger {}

    override val coroutineContext: CoroutineContext = SupervisorJob() + Dispatchers.IO

    fun analyse(url: String): Analysis {
        val persistedAnalysis = analysisService.initializeAnalysis(url)
        asyncProcessAnalysis(persistedAnalysis)
        return persistedAnalysis
    }

    private fun asyncProcessAnalysis(analysis: Analysis) = launch {
        try {
            log.info { "Analysis for url: ${analysis.sourceUrl} process started. [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]" }
            val contentAnalysis: ContentAnalysis = analyseContent(analysis)
            analysis.fillWith(contentAnalysis)

            analysis.status = COMPARING_SIMILAR_SOURCES
            val updatedAnalysis: Analysis = analysisService.update(analysis)

            val contentComparison: ContentComparison? = compareSimilarSources(updatedAnalysis, contentAnalysis)
            contentComparison?.let { updatedAnalysis.fillWith(it) }

            updatedAnalysis.status = COMPLETED
            analysisService.update(updatedAnalysis)
            log.info { "Analysis process finished. [analysisId: ${updatedAnalysis.id}, requestId: ${updatedAnalysis.requestId}]" }

            sendAnalysisResult(updatedAnalysis, contentAnalysis, contentComparison)
        } catch (e: Exception) {
            log.error { "Error while analysing content [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]: $e" }
            analysis.status = FAILED
            analysisService.updateStatus(analysis.requestId, FAILED)
        }
    }

    private fun analyseContent(analysis: Analysis): ContentAnalysis {
        log.info { "Scraping source web page. [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]" }
        val scrapedWebContent: ScrapedWebContent = webScraper.scrape(analysis.sourceUrl)

        log.info { "Analysing content of scraped web page. [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]" }
        val contentAnalysis: ContentAnalysis = contentAnalyzer.analyse(scrapedWebContent.text)

        return contentAnalysis
    }

    private fun compareSimilarSources(analysis: Analysis, contentAnalysis: ContentAnalysis): ContentComparison? {
        log.info { "Searching for top matching urls to the keywords. [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]" }
        val topSimilarUrls: List<String> = keywordWebSearcher.searchTopUrls(contentAnalysis.summarization.keywords)

        log.info { "Scraping top matched urls [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]" }
        val scrapedSimilarWebContents: List<ScrapedWebContent> = topSimilarUrls
            .map { webScraper.scrape(it) }
            .filter { it.url != analysis.sourceUrl }
            .filter { it.text.isNotBlank() }

        if (scrapedSimilarWebContents.isEmpty()) {
            log.info { "No valid matched sources were found [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]" }
            return null
        }

        log.info { "Comparing top matched sources [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]" }
        val contentComparison: ContentComparison = contentComparator.compare(
            contentAnalysis.summarization.description, scrapedSimilarWebContents
        )

        return contentComparison
    }

    private fun sendAnalysisResult(
        analysis: Analysis,
        contentAnalysis: ContentAnalysis,
        contentComparison: ContentComparison?
    ) {
        val result = AnalysisResultMessage(
            analysis.requestId, analysis.userId, analysis.sourceUrl,
            contentAnalysis, contentComparison
        )
        log.info { "Sending result for report generation. [analysisId: ${analysis.id}, requestId: ${analysis.requestId}]" }
        producer.sendAnalysis(result)
    }

}