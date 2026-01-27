package pl.edu.p.lodz.wiarygodnik.rgs.security

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import pl.edu.p.lodz.wiarygodnik.rgs.config.CorsProperties

@Configuration
@EnableConfigurationProperties(CorsProperties::class)
class CorsConfig(private val corsProperties: CorsProperties) {

    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration().apply {
            allowedOrigins = corsProperties.allowedOrigins
            allowedMethods = corsProperties.allowedMethods
            allowedHeaders = corsProperties.allowedHeaders
            exposedHeaders = corsProperties.exposedHeaders
            allowCredentials = corsProperties.allowCredentials
            maxAge = corsProperties.maxAge
        }

        return UrlBasedCorsConfigurationSource().apply {
            registerCorsConfiguration("/**", configuration)
        }
    }

}