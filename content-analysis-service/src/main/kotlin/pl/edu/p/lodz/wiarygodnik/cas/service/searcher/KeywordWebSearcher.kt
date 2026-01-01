package pl.edu.p.lodz.wiarygodnik.cas.service.searcher

interface KeywordWebSearcher {
    fun searchTopUrls(keywords: Set<String>): List<String>
}