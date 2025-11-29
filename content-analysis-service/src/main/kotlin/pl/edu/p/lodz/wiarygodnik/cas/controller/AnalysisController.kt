package pl.edu.p.lodz.wiarygodnik.cas.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisResult
import pl.edu.p.lodz.wiarygodnik.cas.service.AnalysisProcessor

@RestController
@RequestMapping("/api")
class AnalysisController(private val analysisProcessor: AnalysisProcessor) {

    @PostMapping("/analyse")
    fun analyze(@RequestBody request: AnalyseRequest): ResponseEntity<AnalysisResult> {
        return ResponseEntity.ok(analysisProcessor.analyse(request.url))
    }

    data class AnalyseRequest(val url: String)
}
