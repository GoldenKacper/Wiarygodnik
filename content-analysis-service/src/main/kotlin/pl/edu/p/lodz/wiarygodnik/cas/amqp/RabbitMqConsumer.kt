package pl.edu.p.lodz.wiarygodnik.cas.amqp

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.amqp.RabbitMQConfig.Companion.DELETE_ANALYSIS_QUEUE
import pl.edu.p.lodz.wiarygodnik.cas.model.message.DeleteAnalysisMessage
import pl.edu.p.lodz.wiarygodnik.cas.service.AnalysisService

@Component
class RabbitMQConsumer(private val analysisService: AnalysisService) {

    private val log = KotlinLogging.logger {}

    @RabbitListener(queues = [DELETE_ANALYSIS_QUEUE])
    fun contentAnalysisServiceResultsQueue(message: DeleteAnalysisMessage) {
        log.info { "Received message: $message" }
        analysisService.deleteAnalysis(message.requestId)
    }

}