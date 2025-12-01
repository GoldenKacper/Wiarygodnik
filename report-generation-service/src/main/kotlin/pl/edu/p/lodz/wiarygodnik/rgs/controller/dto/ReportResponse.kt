package pl.edu.p.lodz.wiarygodnik.rgs.controller.dto

data class ReportResponse(
    val requestId: String,
    val sourceUrl: String,
    val content: String
)