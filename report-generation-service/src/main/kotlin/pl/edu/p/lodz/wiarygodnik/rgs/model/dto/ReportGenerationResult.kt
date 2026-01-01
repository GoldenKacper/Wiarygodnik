package pl.edu.p.lodz.wiarygodnik.rgs.model.dto

import pl.edu.p.lodz.wiarygodnik.rgs.model.CredibilityLevel

data class ReportGenerationResult(val title: String, val content: String, val credibilityLevel: CredibilityLevel)
