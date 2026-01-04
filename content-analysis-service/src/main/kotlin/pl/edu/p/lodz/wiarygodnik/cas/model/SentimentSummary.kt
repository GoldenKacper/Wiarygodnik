package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*
import jakarta.persistence.GenerationType.SEQUENCE

@Entity
class SentimentSummary(
    @OneToOne(mappedBy = "sentimentSummary") val analysis: Analysis,
    @Column(columnDefinition = "TEXT") val summary: String,
    @OneToMany(
        mappedBy = "sentimentSummary",
        cascade = [CascadeType.ALL]
    ) var examples: MutableList<SentimentExample> = ArrayList(),
    @Id @GeneratedValue(strategy = SEQUENCE) val id: Long? = null
)
