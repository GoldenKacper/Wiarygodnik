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
    @Id @GeneratedValue(strategy = IDENTITY) var id: Long = 0,
    @Version var version: Long = 0,
    var requestId: String,
    var userId: String,
    var sourceUrl: String,
    @Enumerated(EnumType.STRING) var status: ReportStatus,
    var title: String = "Not generated yet",
    @Enumerated(EnumType.STRING) var credibilityLevel: CredibilityLevel = CredibilityLevel.LOW,
    @Lob @Column(columnDefinition = "TEXT") var content: String = "Not generated yet",
    @OneToMany(
        cascade = [CascadeType.ALL],
        mappedBy = "report"
    ) var similarSources: MutableList<SimilarSource> = ArrayList()
) {

    companion object {
        fun fromAnalysisResult(analysisResult: AnalysisResultMessage): Report {
            val initialReport = Report(
                requestId = analysisResult.requestId,
                userId = analysisResult.userId,
                sourceUrl = analysisResult.sourceUrl,
                status = ReportStatus.GENERATING
            )
            analysisResult.contentComparison?.let { contentComparison ->
                initialReport.similarSources = contentComparison.sourcesFacts
                    .map { SimilarSource(sourceUrl = it.url, report = initialReport) }
                    .toMutableList()
            }
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