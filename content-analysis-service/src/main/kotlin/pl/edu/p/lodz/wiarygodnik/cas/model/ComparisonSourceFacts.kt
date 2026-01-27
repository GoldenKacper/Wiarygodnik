package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.SEQUENCE

@Entity
class ComparisonSourceFacts(
    @ManyToOne @JoinColumn(name = "comparison_summary_id") val comparisonSummary: ComparisonSummary,
    @Column(columnDefinition = "TEXT") val sourceUrl: String,
    @ElementCollection @CollectionTable(name = "source_facts") @Column(columnDefinition = "TEXT") val facts: Set<String> = mutableSetOf(),
    @Id @GeneratedValue(strategy = SEQUENCE) val id: Long? = null
)
