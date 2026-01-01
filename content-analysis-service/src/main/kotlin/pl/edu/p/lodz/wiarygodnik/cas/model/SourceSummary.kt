package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*

@Entity
class SourceSummary(
    @OneToOne val analysis: Analysis,
    @Column(columnDefinition = "TEXT") val summary: String,
    @ElementCollection @CollectionTable(name = "source_keywords") val keywords: Set<String> = mutableSetOf(),
    @Id @GeneratedValue val id: Long? = null
)