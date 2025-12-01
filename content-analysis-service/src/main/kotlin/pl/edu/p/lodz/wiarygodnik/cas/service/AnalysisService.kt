package pl.edu.p.lodz.wiarygodnik.cas.service

import org.springframework.stereotype.Service
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisEntity
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisStatus
import pl.edu.p.lodz.wiarygodnik.cas.repository.AnalysisRepository

@Service
class AnalysisService(private val analysisRepository: AnalysisRepository) {

    fun getAnalysisStatus(requestId: String): AnalysisStatus {
        val analysis: AnalysisEntity = analysisRepository.findAnalysisEntityByRequestId(requestId)
            ?: throw NoSuchElementException("Analysis not found")
        return analysis.status
    }

}