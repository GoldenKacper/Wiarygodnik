package pl.edu.p.lodz.wiarygodnik.cas.service.dto

data class ContentAnalysis(val summarization: String, val keywords: List<String>, val sentiment: Sentiment)
