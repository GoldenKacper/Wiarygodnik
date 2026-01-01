package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*

@Entity
class SentimentExampleQuote(
    @ManyToOne @JoinColumn(name = "sentiment_example_id") val sentimentExample: SentimentExample,
    @Column(columnDefinition = "TEXT") val quote: String,
    @Column(columnDefinition = "TEXT") val explanation: String,
    @Id @GeneratedValue val id: Long? = null
)
