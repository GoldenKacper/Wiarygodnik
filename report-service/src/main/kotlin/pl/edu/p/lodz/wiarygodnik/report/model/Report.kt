package pl.edu.p.lodz.wiarygodnik.report.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY

@Entity
class Report(
    @Lob @Column(columnDefinition = "TEXT") var content: String = "Not generated yet",
    var sourceUrl: String,
    @Id @GeneratedValue(strategy = IDENTITY) var id: Long = 0,
)