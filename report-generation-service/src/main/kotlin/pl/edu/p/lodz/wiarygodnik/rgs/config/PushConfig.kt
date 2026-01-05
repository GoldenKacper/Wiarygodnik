package pl.edu.p.lodz.wiarygodnik.rgs.config

import jakarta.annotation.PostConstruct
import nl.martijndwars.webpush.PushService
import org.bouncycastle.jce.provider.BouncyCastleProvider
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.security.Security

@Configuration
class PushConfig {

    @PostConstruct
    fun init() {
        if (Security.getProvider("BC") == null) {
            Security.addProvider(BouncyCastleProvider())
        }
    }

    @Bean
    fun pushService(
        @Value("\${push.vapid.public-key}") publicKey: String,
        @Value("\${push.vapid.private-key}") privateKey: String,
        @Value("\${push.vapid.subject}") subject: String
    ): PushService = PushService().apply {
        setPublicKey(publicKey)
        setPrivateKey(privateKey)
        setSubject(subject)
    }

}