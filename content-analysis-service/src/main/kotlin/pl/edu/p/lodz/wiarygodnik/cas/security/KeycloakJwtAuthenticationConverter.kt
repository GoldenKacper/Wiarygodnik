package pl.edu.p.lodz.wiarygodnik.cas.security

import org.springframework.core.convert.converter.Converter
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.jwt.Jwt
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken

class KeycloakJwtAuthenticationConverter : Converter<Jwt, AbstractAuthenticationToken> {

    override fun convert(source: Jwt): AbstractAuthenticationToken {
        val authorities = (extractRealmRoles(source)).toSet()
        return JwtAuthenticationToken(source, authorities)
    }

    private fun extractRealmRoles(jwt: Jwt): Collection<GrantedAuthority> {
        val realmAccess = jwt.claims["realm_access"] as? Map<*, *> ?: return emptyList()
        val roles = realmAccess["roles"] as? Collection<*> ?: return emptyList()

        return roles
            .filterIsInstance<String>()
            .map { SimpleGrantedAuthority("ROLE_${it.replace("-", "_").uppercase()}") }
    }

}
