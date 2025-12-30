package pl.edu.p.lodz.wiarygodnik.rgs.repo

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.transaction.annotation.Transactional
import pl.edu.p.lodz.wiarygodnik.rgs.model.Report
import pl.edu.p.lodz.wiarygodnik.rgs.model.ReportStatus

@Transactional
interface ReportRepository : JpaRepository<Report, Long> {
    fun findReportByRequestIdAndUserIdAndStatus(requestId: String, userId: String, status: ReportStatus): Report?
    fun findAllByUserIdAndStatus(userId: String, status: ReportStatus): List<Report>
}