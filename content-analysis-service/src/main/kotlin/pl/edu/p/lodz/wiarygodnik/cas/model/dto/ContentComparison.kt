package pl.edu.p.lodz.wiarygodnik.cas.model.dto

data class ContentComparison(val description: String, val sourcesFacts: List<SourceFacts>)
data class SourceFacts(val url: String, val facts: Set<String>)