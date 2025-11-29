package pl.edu.p.lodz.wiarygodnik.cas.service.scraper

import pl.edu.p.lodz.wiarygodnik.cas.model.ScrapedWebContent

interface WebScraper {
    fun scrape(url: String): ScrapedWebContent
}