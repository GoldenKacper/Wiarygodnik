package pl.edu.p.lodz.wiarygodnik.cas.service.comparator

import pl.edu.p.lodz.wiarygodnik.cas.model.ContentComparison
import pl.edu.p.lodz.wiarygodnik.cas.model.ScrapedWebContent

interface ContentComparator {
    fun compare(mainSourceDescription: String, similarSourceContents: List<ScrapedWebContent>): ContentComparison
}