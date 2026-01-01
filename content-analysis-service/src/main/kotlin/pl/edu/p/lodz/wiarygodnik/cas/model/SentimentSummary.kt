package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*

@Entity
class SentimentSummary(
    @OneToOne val analysis: Analysis,
    @Column(columnDefinition = "TEXT") val summary: String,
    @OneToMany(
        mappedBy = "sentimentSummary",
        cascade = [CascadeType.ALL]
    ) var examples: MutableList<SentimentExample> = ArrayList(),
    @Id @GeneratedValue val id: Long? = null
)
