package pl.edu.p.lodz.wiarygodnik.cas.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.invoke
import org.springframework.security.config.http.SessionCreationPolicy.STATELESS
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http {
            cors { }
            csrf { disable() }
            authorizeHttpRequests {
                authorize("/api/analysis/**", hasRole("USER"))
            }
            oauth2ResourceServer {
                jwt {
                    jwtAuthenticationConverter = KeycloakJwtAuthenticationConverter()
                }
            }
            sessionManagement {
                sessionCreationPolicy = STATELESS
            }
        }
        return http.build()
    }

}