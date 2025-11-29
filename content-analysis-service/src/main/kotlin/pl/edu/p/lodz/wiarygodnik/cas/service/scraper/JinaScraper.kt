package pl.edu.p.lodz.wiarygodnik.cas.service.scraper

import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import pl.edu.p.lodz.wiarygodnik.cas.model.ScrapedWebContent

@Service
class JinaScraper(
    webClientBuilder: WebClient.Builder,
    @Value("\${jina.url}") private val baseUrl: String,
    @Value("\${jina.api-key}") private val apiKey: String
) : WebScraper {

    private val webClient: WebClient = webClientBuilder.baseUrl(baseUrl).build()

    override fun scrape(url: String): ScrapedWebContent = webClient.get()
        .uri { builder -> builder.path(url).build() }
        .header(HttpHeaders.AUTHORIZATION, "Bearer: $apiKey")
        .header("X-Return-Format", "text")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToMono(JinaWrapper::class.java)
        .blockOptional()
        .map { it.data }
        .orElseThrow { IllegalStateException("Missing data in Jina.ai API response") }

    data class JinaWrapper(val data: ScrapedWebContent)

}