package pl.edu.p.lodz.wiarygodnik.rgs.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import pl.edu.p.lodz.wiarygodnik.rgs.model.Report

@Transactional
interface ReportRepository : JpaRepository<Report, Long> {
    fun findReportByRequestId(requestId: String): Report?
}