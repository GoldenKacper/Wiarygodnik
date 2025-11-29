package pl.edu.p.lodz.wiarygodnik.cas.service.searcher

interface KeywordWebSearcher {
    fun searchTopUrls(keywords: List<String>): List<String>
}