package pl.edu.p.lodz.wiarygodnik.cas.service

import pl.edu.p.lodz.wiarygodnik.cas.service.dto.ScrapedWebContent

interface WebScraper {
    fun scrape(url: String): ScrapedWebContent
}
