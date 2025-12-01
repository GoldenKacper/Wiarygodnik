package pl.edu.p.lodz.wiarygodnik.rgs.amqp

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.rgs.amqp.RabbitMQConfig.Companion.ANALYSIS_RESULTS_QUEUE
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.AnalysisResult
import pl.edu.p.lodz.wiarygodnik.rgs.service.ReportService

@Component
class RabbitMQConsumer(private val reportService: ReportService) {

    private val log = KotlinLogging.logger {}

    @RabbitListener(queues = [ANALYSIS_RESULTS_QUEUE])
    fun contentAnalysisServiceResultsQueue(message: AnalysisResult) {
        log.info { "Received message: $message" }
        reportService.createReport(message)
    }

}