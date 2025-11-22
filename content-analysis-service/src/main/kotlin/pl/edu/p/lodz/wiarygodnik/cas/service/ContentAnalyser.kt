package pl.edu.p.lodz.wiarygodnik.cas.service

import pl.edu.p.lodz.wiarygodnik.cas.service.dto.ContentAnalysis

interface ContentAnalyser {
    fun analyse(content: String): ContentAnalysis
}