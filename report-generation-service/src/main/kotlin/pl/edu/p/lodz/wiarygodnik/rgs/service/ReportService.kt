package pl.edu.p.lodz.wiarygodnik.rgs.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import pl.edu.p.lodz.wiarygodnik.rgs.model.Report
import pl.edu.p.lodz.wiarygodnik.rgs.model.ReportStatus
import pl.edu.p.lodz.wiarygodnik.rgs.model.ReportStatus.FAILED
import pl.edu.p.lodz.wiarygodnik.rgs.model.ReportStatus.GENERATED
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.AnalysisResultMessage
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.ReportGenerationResult
import pl.edu.p.lodz.wiarygodnik.rgs.repo.ReportRepository
import pl.edu.p.lodz.wiarygodnik.rgs.security.PrincipalProvider

@Service
class ReportService(
    private val analysisReportGenerator: AiAnalysisReportGenerator,
    private val reportRepository: ReportRepository
) {

    private val log = KotlinLogging.logger {}

    fun createReport(analysisResult: AnalysisResultMessage) {
        val newReport = Report.fromAnalysisResult(analysisResult)
        val persistedReport = reportRepository.save(newReport)
        log.info { "Initial report persisted to database [reportId: ${persistedReport.id}, requestId: ${analysisResult.requestId}]" }

        try {
            log.info { "Generating report... [reportId: ${persistedReport.id}, requestId: ${analysisResult.requestId}]" }
            val reportGenerationResult: ReportGenerationResult = analysisReportGenerator.generate(analysisResult)
            persistedReport.fillWithGeneratedContent(reportGenerationResult)
            reportRepository.save(persistedReport)
            log.info { "Report generated successfully and persisted to database [reportId: ${persistedReport.id}], requestId: ${analysisResult.requestId}" }
        } catch (e: Exception) {
            log.error(e) { "Generating report failed" }
            persistedReport.status = FAILED
            reportRepository.save(persistedReport)
        }
    }

    fun getReportStatus(requestId: String): ReportStatus {
        return findReportByRequestId(requestId).status
    }

    fun findReportByRequestId(requestId: String): Report {
        val currentUserId = PrincipalProvider.getCurrentUserId()
        return reportRepository.findReportByRequestIdAndUserIdAndStatus(requestId, currentUserId, GENERATED)
            ?: throw NoSuchElementException("Report not found")
    }

    fun findAllReportsForCurrentUser(): List<Report> {
        val currentUserId = PrincipalProvider.getCurrentUserId()
        return reportRepository.findAllByUserIdAndStatus(currentUserId, GENERATED)
    }

}