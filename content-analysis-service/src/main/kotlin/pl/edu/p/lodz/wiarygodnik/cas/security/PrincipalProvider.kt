package pl.edu.p.lodz.wiarygodnik.cas.security

import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

object PrincipalProvider {

    fun getCurrentUserId(): String {
        val authentication: Authentication = SecurityContextHolder.getContext().authentication
            ?: throw IllegalStateException("User not authenticated")

        if (!authentication.isAuthenticated) {
            throw IllegalStateException("User not authenticated")
        }

        val principal = authentication.principal
        if (principal !is Jwt) {
            throw IllegalStateException("Authentication principal is not Jwt: ${principal::class.qualifiedName}")
        }

        return principal.subject
    }

}