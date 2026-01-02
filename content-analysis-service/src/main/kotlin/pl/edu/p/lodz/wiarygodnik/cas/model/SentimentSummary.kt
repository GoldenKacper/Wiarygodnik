package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*

@Entity
class SentimentSummary(
    @OneToOne(mappedBy = "sentimentSummary") val analysis: Analysis,
    @Column(columnDefinition = "TEXT") val summary: String,
    @OneToMany(
        mappedBy = "sentimentSummary",
        cascade = [CascadeType.ALL]
    ) var examples: MutableList<SentimentExample> = ArrayList(),
    @Id @GeneratedValue val id: Long? = null
)
