package pl.edu.p.lodz.wiarygodnik.rgs.service.notifications

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.context.event.EventListener
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.rgs.model.PushSubscription
import pl.edu.p.lodz.wiarygodnik.rgs.repo.PushSubscriptionRepository
import java.util.function.Consumer

@Component
class ReportGeneratedListener(
    private val pushService: PushNotificationService,
    private val pushSubscriptionRepository: PushSubscriptionRepository
) {

    private val log = KotlinLogging.logger {}

    @EventListener
    fun onReportGenerated(event: ReportGeneratedEvent) {
        log.info { "Received report generated event. Sending push notification. [requestId: ${event.requestId}]" }
        pushSubscriptionRepository.findByUserId(event.userId)
            .forEach(Consumer { sub: PushSubscription ->
                pushService.send(
                    sub,
                    "Analysis completed",
                    "Your report is ready",
                    "/reports/" + event.requestId
                )
            })
    }

}
