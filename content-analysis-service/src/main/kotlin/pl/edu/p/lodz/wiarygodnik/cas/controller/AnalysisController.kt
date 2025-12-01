package pl.edu.p.lodz.wiarygodnik.cas.controller

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisStatus
import pl.edu.p.lodz.wiarygodnik.cas.service.AnalysisProcessor
import pl.edu.p.lodz.wiarygodnik.cas.service.AnalysisService

@RestController
@RequestMapping("/api/analysis")
class AnalysisController(
    private val analysisProcessor: AnalysisProcessor,
    private val analysisService: AnalysisService
) {

    @PostMapping("/process")
    fun analyze(@RequestBody request: AnalyseRequest): ResponseEntity<AnalyseResponse> {
        val analysisEntity = analysisProcessor.analyse(request.url)
        return ResponseEntity.status(CREATED).body(AnalyseResponse(analysisEntity.requestId))
    }

    @GetMapping("/status/{requestId}")
    fun getAnalysis(@PathVariable requestId: String): ResponseEntity<AnalysisStatusResponse> =
        ResponseEntity.ok(AnalysisStatusResponse(analysisService.getAnalysisStatus(requestId)))

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    fun handleNoSuchException(ex: NoSuchElementException): ProblemDetail =
        ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.message)

    data class AnalyseRequest(val url: String)
    data class AnalyseResponse(val requestId: String)
    data class AnalysisStatusResponse(val status: AnalysisStatus)
}
