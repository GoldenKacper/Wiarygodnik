package pl.edu.p.lodz.wiarygodnik.rgs.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
class SimilarSource(
    @Id @GeneratedValue(strategy = IDENTITY) var id: Long = 0,
    @Version var version: Long = 0,
    var sourceUrl: String = "",
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "report_id")
    var report: Report? = null,
)