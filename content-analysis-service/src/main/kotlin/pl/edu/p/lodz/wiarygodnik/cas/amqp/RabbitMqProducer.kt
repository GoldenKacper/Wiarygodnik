package pl.edu.p.lodz.wiarygodnik.cas.amqp

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.stereotype.Service
import pl.edu.p.lodz.wiarygodnik.cas.amqp.RabbitMQConfig.Companion.ANALYSIS_ROUTING_KEY
import pl.edu.p.lodz.wiarygodnik.cas.amqp.RabbitMQConfig.Companion.EXCHANGE_NAME
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.AnalysisResultMessage

@Service
class RabbitMQProducer(val rabbitTemplate: RabbitTemplate) {

    private val log = KotlinLogging.logger {}

    fun sendAnalysis(analysisResultMessage: AnalysisResultMessage) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, ANALYSIS_ROUTING_KEY, analysisResultMessage)
        log.info { "Sent analysis: $analysisResultMessage" }
    }

}