package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import pl.edu.p.lodz.wiarygodnik.cas.model.AnalysisStatus.ANALYSING_CONTENT

enum class AnalysisStatus {
    ANALYSING_CONTENT, COMPARING_SIMILAR_SOURCES, COMPLETED, FAILED
}

@Entity
class AnalysisEntity(
    @Id @GeneratedValue(strategy = IDENTITY) var id: Long = 0,
    val requestId: String = "",
    var sourceUrl: String = "",
    @Enumerated(EnumType.STRING) var status: AnalysisStatus = ANALYSING_CONTENT,
)