package pl.edu.p.lodz.wiarygodnik.cas.service.searcher

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient

@Service
class SerpKeywordWebSearcher(
    webClientBuilder: WebClient.Builder,
    @Value("\${serpapi.url}") private val baseUrl: String,
    @Value("\${serpapi.limit}") private val limit: Int,
    @Value("\${serpapi.api-key}") private val apiKey: String
) : KeywordWebSearcher {

    private val webClient: WebClient = webClientBuilder.baseUrl(baseUrl).build()

    override fun searchTopUrls(keywords: List<String>): List<String> =
        webClient.get()
            .uri { builder ->
                builder.path("/search.json")
                    .queryParam("q", keywords.joinToString(" "))
                    .queryParam("engine", "google_light")
                    .queryParam("api_key", apiKey)
                    .build()
            }
            .retrieve()
            .bodyToMono(SerpApiResponse::class.java)
            .blockOptional()
            .map { root -> root.organicResults.map { it.link }.take(limit) }
            .orElse(emptyList())

    data class SerpApiResponse(@JsonProperty("organic_results") val organicResults: List<SerpApiResult>)
    data class SerpApiResult(val link: String)

}