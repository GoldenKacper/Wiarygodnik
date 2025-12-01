package pl.edu.p.lodz.wiarygodnik.rgs.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.p.lodz.wiarygodnik.rgs.controller.dto.ReportResponse
import pl.edu.p.lodz.wiarygodnik.rgs.controller.dto.ReportStatusResponse
import pl.edu.p.lodz.wiarygodnik.rgs.model.Report
import pl.edu.p.lodz.wiarygodnik.rgs.model.ReportStatus
import pl.edu.p.lodz.wiarygodnik.rgs.service.ReportService


@RestController
@RequestMapping("/api/report")
class ReportController(val reportService: ReportService) {

    @GetMapping("/{requestId}")
    fun getReport(@PathVariable requestId: String): ResponseEntity<ReportResponse> {
        val report: Report = reportService.getReportContent(requestId)
        val response = ReportResponse(requestId, report.sourceUrl, report.content)
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