package pl.edu.p.lodz.wiarygodnik.cas.service

import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisResult
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentAnalysis
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentComparison
import pl.edu.p.lodz.wiarygodnik.cas.model.ScrapedWebContent
import pl.edu.p.lodz.wiarygodnik.cas.service.analyser.ContentAnalyser
import pl.edu.p.lodz.wiarygodnik.cas.service.comparator.ContentComparator
import pl.edu.p.lodz.wiarygodnik.cas.service.scraper.WebScraper
import pl.edu.p.lodz.wiarygodnik.cas.service.searcher.KeywordWebSearcher

@Component
class AnalysisProcessor(
    private val webScraper: WebScraper,
    private val contentAnalyzer: ContentAnalyser,
    private val keywordWebSearcher: KeywordWebSearcher,
    private val contentComparator: ContentComparator
) {

    fun analyse(url: String): AnalysisResult {
        val scrapedWebContent: ScrapedWebContent = webScraper.scrape(url)
        val analysis: ContentAnalysis = contentAnalyzer.analyse(scrapedWebContent.text)
        val urls: List<String> = keywordWebSearcher.searchTopUrls(analysis.summarization.keywords)
        val scrapedSimilarWebContents: List<ScrapedWebContent> = urls.map { webScraper.scrape(it) }
        val comparison: ContentComparison =
            contentComparator.compare(analysis.summarization.description, scrapedSimilarWebContents)
        return AnalysisResult(analysis, comparison)
    }
}