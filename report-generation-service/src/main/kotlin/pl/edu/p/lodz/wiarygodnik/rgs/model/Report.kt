package pl.edu.p.lodz.wiarygodnik.rgs.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

enum class ReportStatus {
    GENERATING, GENERATED
}

@Entity
class Report(
    @Id @GeneratedValue(strategy = IDENTITY) var id: Long = 0,
    var requestId: String = "",
    var sourceUrl: String = "",
    @Enumerated(EnumType.STRING) var status: ReportStatus = ReportStatus.GENERATING,
    @Lob @Column(columnDefinition = "TEXT") var content: String = "Not generated yet",
) {
    fun fillWithGeneratedContent(generatedContent: String) {
        this.content = generatedContent
        this.status = ReportStatus.GENERATED
    }
}