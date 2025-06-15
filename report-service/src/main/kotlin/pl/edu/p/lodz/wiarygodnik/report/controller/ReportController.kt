package pl.edu.p.lodz.wiarygodnik.report.controller

import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.p.lodz.wiarygodnik.report.controller.dto.ReportResponse
import pl.edu.p.lodz.wiarygodnik.report.controller.dto.UrlRequest
import pl.edu.p.lodz.wiarygodnik.report.model.Report
import pl.edu.p.lodz.wiarygodnik.report.service.ReportService

@RestController
@RequestMapping("/api/report")
class ReportController(val reportService: ReportService) {

    @PostMapping
    fun reportCreationOrder(@RequestBody request: UrlRequest): ResponseEntity<String> {
        reportService.orderReportCreation(request)
        return ResponseEntity.status(CREATED).build()
    }

    @GetMapping("/{id}")
    fun getReport(@PathVariable id: Long): ResponseEntity<ReportResponse> {
        val reportContent: String = reportService.getReportContent(id)
        return ResponseEntity.ok(ReportResponse(id, reportContent))
    }

}