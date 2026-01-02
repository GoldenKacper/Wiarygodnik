package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*

@Entity
class ComparisonSummary(
    @OneToOne(mappedBy = "comparisonSummary") val analysis: Analysis,
    @Column(columnDefinition = "TEXT") val summary: String,
    @OneToMany(
        mappedBy = "comparisonSummary",
        cascade = [CascadeType.ALL]
    ) var examples: MutableList<ComparisonSourceFacts> = ArrayList(),
    @Id @GeneratedValue val id: Long? = null
)
