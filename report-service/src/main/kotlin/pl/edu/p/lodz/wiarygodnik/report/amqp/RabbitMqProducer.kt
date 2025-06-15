package pl.edu.p.lodz.wiarygodnik.report.amqp

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import pl.edu.p.lodz.wiarygodnik.report.amqp.RabbitMQConfig.Companion.EXCHANGE_NAME
import pl.edu.p.lodz.wiarygodnik.report.service.dto.ReportCreationOrder

@Service
class RabbitMQProducer(val rabbitTemplate: RabbitTemplate) {

    private val log = KotlinLogging.logger {}

    fun sendReportCreationOrder(order: ReportCreationOrder) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "report.creation.order.key", order)
        log.info { "Sent report creation order: $order" }
    }

}