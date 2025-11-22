package pl.edu.p.lodz.wiarygodnik.cas.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class BeanConfig {
    @Bean
    fun webClient(): WebClient = WebClient.builder().build()
}