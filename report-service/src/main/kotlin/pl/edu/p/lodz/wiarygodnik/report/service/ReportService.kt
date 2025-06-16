package pl.edu.p.lodz.wiarygodnik.report.service

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import pl.edu.p.lodz.wiarygodnik.report.amqp.RabbitMQProducer
import pl.edu.p.lodz.wiarygodnik.report.controller.dto.UrlRequest
import pl.edu.p.lodz.wiarygodnik.report.model.Report
import pl.edu.p.lodz.wiarygodnik.report.repo.ReportRepository
import pl.edu.p.lodz.wiarygodnik.report.service.dto.ReportContent
import pl.edu.p.lodz.wiarygodnik.report.service.dto.ReportCreationOrder

@Service
class ReportService(
    private val producer: RabbitMQProducer,
    private val reportRepository: ReportRepository
) {

    fun orderReportCreation(urlRequest: UrlRequest): Long {
        val newReport = Report(sourceUrl = urlRequest.url)
        val persistedReport = reportRepository.save(newReport)

        val reportCreationOrder = ReportCreationOrder(persistedReport.id, persistedReport.sourceUrl)
        producer.sendReportCreationOrder(reportCreationOrder)
        return persistedReport.id
    }

    fun updateReportContent(reportContent: ReportContent) {
        reportRepository.findByIdOrNull(reportContent.reportId)?.let {
            it.content = reportContent.content
            reportRepository.save(it)
        }
    }

    fun getReportContent(reportId: Long): String {
        val report = reportRepository.findByIdOrNull(reportId) ?: throw NoSuchElementException("Report not found")
        return report.content
    }

}