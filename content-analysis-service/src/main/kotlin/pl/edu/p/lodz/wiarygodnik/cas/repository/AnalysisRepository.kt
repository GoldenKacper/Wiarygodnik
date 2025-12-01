package pl.edu.p.lodz.wiarygodnik.cas.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisEntity

@Transactional
interface AnalysisRepository : JpaRepository<AnalysisEntity, Long> {
    fun findAnalysisEntityByRequestId(requestId: String): AnalysisEntity?
}