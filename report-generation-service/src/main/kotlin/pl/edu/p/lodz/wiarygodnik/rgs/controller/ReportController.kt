package pl.edu.p.lodz.wiarygodnik.rgs.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.p.lodz.wiarygodnik.rgs.controller.dto.ReportContentResponse
import pl.edu.p.lodz.wiarygodnik.rgs.controller.dto.ReportListItemResponse
import pl.edu.p.lodz.wiarygodnik.rgs.controller.dto.ReportStatusResponse
import pl.edu.p.lodz.wiarygodnik.rgs.model.Report
import pl.edu.p.lodz.wiarygodnik.rgs.model.ReportStatus
import pl.edu.p.lodz.wiarygodnik.rgs.service.ReportService


@RestController
@RequestMapping("/api/report")
class ReportController(val reportService: ReportService) {

    @GetMapping
    fun getMyAllReports(): ResponseEntity<List<ReportListItemResponse>> {
        val reports: List<Report> = reportService.findAllReportsForCurrentUser()
        val response = reports.map { ReportListItemResponse(it.requestId, it.title) }
        return ResponseEntity.ok(response)
    }

    @GetMapping("/{requestId}")
    fun getMyReport(@PathVariable requestId: String): ResponseEntity<ReportContentResponse> {
        val report: Report = reportService.findGeneratedReportByRequestId(requestId)
        val response = ReportContentResponse.from(report)
        return ResponseEntity.ok(response)
    }

    @GetMapping("/status/{requestId}")
    fun getReportStatus(@PathVariable requestId: String): ResponseEntity<ReportStatusResponse> {
        val status: ReportStatus = reportService.getReportStatus(requestId)
        val response = ReportStatusResponse(requestId, status)
        return ResponseEntity.ok(response)
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun handleNoSuchException(ex: NoSuchElementException): ProblemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message)

}