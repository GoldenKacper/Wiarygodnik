package pl.edu.p.lodz.wiarygodnik.cas.service

interface KeywordWebSearcher {
    fun searchTopUrls(keywords: List<String>): List<String>
}