package pl.edu.p.lodz.wiarygodnik.rgs.service

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service
import pl.edu.p.lodz.wiarygodnik.rgs.model.Report
import pl.edu.p.lodz.wiarygodnik.rgs.model.ReportStatus
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.AnalysisResult
import pl.edu.p.lodz.wiarygodnik.rgs.repo.ReportRepository

@Service
class ReportService(
    private val analysisReportGenerator: AiAnalysisReportGenerator,
    private val reportRepository: ReportRepository
) {

    private val log = KotlinLogging.logger {}

    fun createReport(analysisResult: AnalysisResult) {
        val newReport = prepareInitialReport(analysisResult)
        val persistedReport = reportRepository.save(newReport)
        log.info { "Initial report persisted to database [reportId: ${persistedReport.id}, requestId: ${analysisResult.requestId}]" }

        log.info { "Generating report... [reportId: ${persistedReport.id}, requestId: ${analysisResult.requestId}]" }
        val generatedReport = analysisReportGenerator.generate(analysisResult)
        persistedReport.fillWithGeneratedContent(generatedReport)
        reportRepository.save(persistedReport)
        log.info { "Report generated successfully and persisted to databse [reportId: ${persistedReport.id}], requestId: ${analysisResult.requestId}" }
    }

    private fun prepareInitialReport(analysisResult: AnalysisResult): Report =
        Report(
            requestId = analysisResult.requestId,
            sourceUrl = analysisResult.sourceUrl
        )

    fun getReportContent(requestId: String): Report =
        reportRepository.findReportByRequestId(requestId) ?: throw NoSuchElementException("Report not found")

    fun getReportStatus(requestId: String): ReportStatus {
        return getReportContent(requestId).status
    }

}