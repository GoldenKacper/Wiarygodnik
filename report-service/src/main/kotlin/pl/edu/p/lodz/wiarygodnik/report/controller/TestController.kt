package pl.edu.p.lodz.wiarygodnik.report.controller

import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.p.lodz.wiarygodnik.report.amqp.RabbitMQConfig.Companion.EXCHANGE_NAME
import pl.edu.p.lodz.wiarygodnik.report.amqp.RabbitMQProducer
import pl.edu.p.lodz.wiarygodnik.report.controller.dto.ReportResponse
import pl.edu.p.lodz.wiarygodnik.report.controller.dto.UrlRequest
import pl.edu.p.lodz.wiarygodnik.report.model.Report
import pl.edu.p.lodz.wiarygodnik.report.service.ReportService
import pl.edu.p.lodz.wiarygodnik.report.service.dto.ReportContent

@RestController
@RequestMapping("/api/mock/report")
class TestController(val rabbitTemplate: RabbitTemplate) {

    @PostMapping
    fun mockCreatedReport(@RequestBody reportContent: ReportContent): ResponseEntity<String> {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, "report.created.key", reportContent)
        return ResponseEntity.ok().build()
    }

}