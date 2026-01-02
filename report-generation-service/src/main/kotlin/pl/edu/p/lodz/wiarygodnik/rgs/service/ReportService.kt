package pl.edu.p.lodz.wiarygodnik.rgs.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation.REQUIRES_NEW
import org.springframework.transaction.annotation.Transactional
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

    @Transactional(propagation = REQUIRES_NEW)
    fun initializeReport(analysisResult: AnalysisResultMessage): Report {
        val newReport = Report.fromAnalysisResult(analysisResult)
        val persistedReport = reportRepository.save(newReport)
        log.info { "Initial report persisted to database [reportId: ${persistedReport.id}, requestId: ${analysisResult.requestId}]" }
        return persistedReport
    }

    @Transactional(propagation = REQUIRES_NEW)
    fun generateReportContent(report: Report, analysisResult: AnalysisResultMessage) {
        try {
            log.info { "Generating report... [reportId: ${report.id}, requestId: ${analysisResult.requestId}]" }
            val reportGenerationResult: ReportGenerationResult = analysisReportGenerator.generate(analysisResult)
            report.fillWithGeneratedContent(reportGenerationResult)
            reportRepository.save(report)
            log.info { "Report generated successfully and persisted to database [reportId: ${report.id}], requestId: ${analysisResult.requestId}" }
        } catch (e: Exception) {
            log.error(e) { "Generating report failed" }
            report.status = FAILED
            reportRepository.save(report)
        }
    }

    fun getReportStatus(requestId: String): ReportStatus {
        val currentUserId = PrincipalProvider.getCurrentUserId()
        val report: Report = reportRepository.findReportByRequestIdAndUserId(requestId, currentUserId)
            ?: throw NoSuchElementException("Report not found")
        return report.status
    }

    fun findGeneratedReportByRequestId(requestId: String): Report {
        val currentUserId = PrincipalProvider.getCurrentUserId()
        return reportRepository.findReportByRequestIdAndUserIdAndStatus(requestId, currentUserId, GENERATED)
            ?: throw NoSuchElementException("Report not found")
    }

    fun findAllReportsForCurrentUser(): List<Report> {
        val currentUserId = PrincipalProvider.getCurrentUserId()
        return reportRepository.findAllByUserIdAndStatus(currentUserId, GENERATED)
    }

}