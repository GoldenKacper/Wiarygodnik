package pl.edu.p.lodz.wiarygodnik.cas.model

data class ContentComparison(val description: String, val sourcesFacts: List<SourceFacts>)
data class SourceFacts(val url: String, val facts: List<String>)