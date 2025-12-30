package pl.edu.p.lodz.wiarygodnik.rgs.controller.dto

import pl.edu.p.lodz.wiarygodnik.rgs.model.CredibilityLevel
import pl.edu.p.lodz.wiarygodnik.rgs.model.Report

data class ReportContentResponse(
    val requestId: String,
    val sourceUrl: String,
    val title: String,
    val credibilityLevel: CredibilityLevel,
    val content: String,
    val similarSourceUrls: List<String>
) {
    companion object {
        fun from(report: Report): ReportContentResponse {
            return ReportContentResponse(
                requestId = report.requestId,
                sourceUrl = report.sourceUrl,
                title = report.title,
                credibilityLevel = report.credibilityLevel,
                content = report.content,
                similarSourceUrls = report.similarSources.map { it.sourceUrl }
            )
        }
    }
}