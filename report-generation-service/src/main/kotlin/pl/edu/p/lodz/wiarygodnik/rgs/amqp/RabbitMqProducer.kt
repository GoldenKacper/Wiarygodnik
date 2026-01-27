package pl.edu.p.lodz.wiarygodnik.rgs.amqp

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.rgs.amqp.RabbitMQConfig.Companion.DELETE_ANALYSIS_ROUTING_KEY
import pl.edu.p.lodz.wiarygodnik.rgs.amqp.RabbitMQConfig.Companion.EXCHANGE_NAME
import pl.edu.p.lodz.wiarygodnik.rgs.model.message.DeleteAnalysisMessage

@Component
class RabbitMQProducer(val rabbitTemplate: RabbitTemplate) {

    private val log = KotlinLogging.logger {}

    fun sendDeleteEvent(message: DeleteAnalysisMessage) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, DELETE_ANALYSIS_ROUTING_KEY, message)
        log.info { "Sent analysis: $message" }
    }

}