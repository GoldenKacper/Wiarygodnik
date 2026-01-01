package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*

@Entity
class ComparisonSourceFacts(
    @ManyToOne @JoinColumn(name = "comparison_summary_id") val comparisonSummary: ComparisonSummary,
    @Column(columnDefinition = "TEXT") val sourceUrl: String,
    @ElementCollection @CollectionTable(name = "source_facts") val facts: Set<String> = mutableSetOf(),
    @Id @GeneratedValue val id: Long? = null
)
