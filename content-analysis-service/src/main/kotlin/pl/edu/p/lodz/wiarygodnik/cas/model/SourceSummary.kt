package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.SEQUENCE

@Entity
class SourceSummary(
    @OneToOne(mappedBy = "sourceSummary") val analysis: Analysis,
    @Column(columnDefinition = "TEXT") val summary: String,
    @ElementCollection @CollectionTable(name = "source_keywords") val keywords: Set<String> = mutableSetOf(),
    @Id @GeneratedValue(strategy = SEQUENCE) val id: Long? = null
)