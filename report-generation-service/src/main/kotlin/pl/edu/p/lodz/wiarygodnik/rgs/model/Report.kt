package pl.edu.p.lodz.wiarygodnik.rgs.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.AnalysisResultMessage
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.ReportGenerationResult

enum class ReportStatus {
    GENERATING, GENERATED, FAILED
}

enum class CredibilityLevel {
    HIGH, MEDIUM, LOW
}

@Entity
class Report(
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE) var id: Long = 0,
    @Version var version: Long = 0,
    var requestId: String,
    var userId: String,
    @Column(columnDefinition = "TEXT") var sourceUrl: String,
    @Enumerated(EnumType.STRING) var status: ReportStatus,
    var title: String = "Not generated yet",
    @Enumerated(EnumType.STRING) var credibilityLevel: CredibilityLevel = CredibilityLevel.LOW,
    @Column(columnDefinition = "TEXT") var content: String = "Not generated yet"
) {

    companion object {
        fun fromAnalysisResult(analysisResult: AnalysisResultMessage): Report {
            val initialReport = Report(
                requestId = analysisResult.requestId,
                userId = analysisResult.userId,
                sourceUrl = analysisResult.sourceUrl,
                status = ReportStatus.GENERATING
            )
            return initialReport
        }
    }

    fun fillWithGeneratedContent(reportGenerationResult: ReportGenerationResult) {
        this.title = reportGenerationResult.title
        this.content = reportGenerationResult.content
        this.credibilityLevel = reportGenerationResult.credibilityLevel
        this.status = ReportStatus.GENERATED
    }

}