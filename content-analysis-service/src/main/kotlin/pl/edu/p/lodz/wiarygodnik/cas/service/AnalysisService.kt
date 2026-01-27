package pl.edu.p.lodz.wiarygodnik.cas.service

import org.springframework.stereotype.Service
import pl.edu.p.lodz.wiarygodnik.cas.model.Analysis
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisStatus
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisStatus.ANALYSING_CONTENT
import pl.edu.p.lodz.wiarygodnik.cas.repository.AnalysisRepository
import pl.edu.p.lodz.wiarygodnik.cas.security.PrincipalProvider
import java.util.*

@Service
class AnalysisService(private val analysisRepository: AnalysisRepository) {

    fun getAnalysisStatus(requestId: String): AnalysisStatus {
        val analysis: Analysis = getAnalysis(requestId)
        return analysis.status
    }

    fun getAnalysis(requestId: String): Analysis {
        val currentUserId: String = PrincipalProvider.getCurrentUserId()
        return analysisRepository.findAnalysisEntityByRequestIdAndUserId(requestId, currentUserId)
            ?: throw NoSuchElementException("Analysis not found")
    }

    fun initializeAnalysis(url: String): Analysis {
        val initialAnalysis = Analysis(
            requestId = UUID.randomUUID().toString(),
            userId = PrincipalProvider.getCurrentUserId(),
            sourceUrl = url,
            status = ANALYSING_CONTENT
        )
        val persistedAnalysis: Analysis = analysisRepository.save(initialAnalysis)
        return persistedAnalysis
    }

    fun update(analysis: Analysis): Analysis {
        return analysisRepository.save(analysis)
    }

    fun updateStatus(requestId: String, status: AnalysisStatus) {
        val analysis: Analysis = analysisRepository.findAnalysisByRequestId(requestId)
            ?: throw NoSuchElementException("Analysis not found")
        analysis.status = status
        analysisRepository.save(analysis)
    }

    fun deleteAnalysis(requestId: String) {
        analysisRepository.findAnalysisByRequestId(requestId)?.let { analysisRepository.delete(it) }
    }

}