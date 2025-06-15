package pl.edu.p.lodz.wiarygodnik.report.amqp

import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.report.amqp.RabbitMQConfig.Companion.REPORT_CREATED_QUEUE
import pl.edu.p.lodz.wiarygodnik.report.service.ReportService
import pl.edu.p.lodz.wiarygodnik.report.service.dto.ReportContent

@Component
class RabbitMQConsumer(private val reportService: ReportService) {

    @RabbitListener(queues = [REPORT_CREATED_QUEUE])
    fun listen(message: ReportContent) {
        reportService.updateReportContent(message)
        println("Received message: $message")
    }

}