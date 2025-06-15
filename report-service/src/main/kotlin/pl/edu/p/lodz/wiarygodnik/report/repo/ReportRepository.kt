package pl.edu.p.lodz.wiarygodnik.report.repo

import org.springframework.data.jpa.repository.JpaRepository
import pl.edu.p.lodz.wiarygodnik.report.model.Report

interface ReportRepository: JpaRepository<Report, Long>