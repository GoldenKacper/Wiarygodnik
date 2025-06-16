package pl.edu.p.lodz.wiarygodnik.report.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import pl.edu.p.lodz.wiarygodnik.report.controller.dto.UrlRequest
import pl.edu.p.lodz.wiarygodnik.report.service.ReportService

@Controller
@RequestMapping("/report")
class ReportWebController(private val reportService: ReportService) {

    @GetMapping
    fun showReportForm(@RequestParam(required = false) reportId: Long?, model: Model): String {
        reportId?.let {
            try {
                val report = reportService.getReportContent(it)
                model.addAttribute("report", report)
            } catch (e: Exception) {
                model.addAttribute("error", "Nie znaleziono raportu o ID: $it")
            }
            model.addAttribute("reportId", it)
        }
        return "report-form"
    }

    @PostMapping
    fun createReport(@RequestParam url: String): String {
        val reportId = reportService.orderReportCreation(UrlRequest(url))
        return "redirect:/report?reportId=$reportId"
    }

}