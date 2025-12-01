package pl.edu.p.lodz.wiarygodnik.rgs.controller.dto

import pl.edu.p.lodz.wiarygodnik.rgs.model.ReportStatus

data class ReportStatusResponse(
    val requestId: String,
    val status: ReportStatus,
)