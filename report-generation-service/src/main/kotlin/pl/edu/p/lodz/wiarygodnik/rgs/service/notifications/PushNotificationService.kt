package pl.edu.p.lodz.wiarygodnik.rgs.service.notifications

import nl.martijndwars.webpush.Encoding.AES128GCM
import nl.martijndwars.webpush.Notification
import nl.martijndwars.webpush.PushService
import nl.martijndwars.webpush.Subscription
import org.springframework.stereotype.Service
import pl.edu.p.lodz.wiarygodnik.rgs.model.PushSubscription


@Service
class PushNotificationService(private val pushService: PushService) {

    fun send(sub: PushSubscription, title: String, body: String, url: String) {
        try {
            val subscription = Subscription(sub.endpoint, Subscription.Keys(sub.p256dh, sub.auth));
            val notification = Notification(
                subscription,
                """{ "title": "$title", "body": "$body", "url": "$url" }""".trimIndent()
            )

            pushService.send(notification, AES128GCM)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}
