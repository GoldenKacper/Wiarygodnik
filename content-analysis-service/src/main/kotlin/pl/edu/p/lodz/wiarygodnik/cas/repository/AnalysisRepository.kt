package pl.edu.p.lodz.wiarygodnik.cas.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import pl.edu.p.lodz.wiarygodnik.cas.model.Analysis

@Transactional
interface AnalysisRepository : JpaRepository<Analysis, Long> {
    fun findAnalysisByRequestId(requestId: String): Analysis?
    fun findAnalysisEntityByRequestIdAndUserId(requestId: String, userId: String): Analysis?
}