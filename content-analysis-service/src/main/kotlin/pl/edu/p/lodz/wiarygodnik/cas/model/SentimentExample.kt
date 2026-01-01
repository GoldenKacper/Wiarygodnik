package pl.edu.p.lodz.wiarygodnik.cas.model

import jakarta.persistence.*
import pl.edu.p.lodz.wiarygodnik.cas.model.dto.Sentiment

@Entity
class SentimentExample(
    @ManyToOne @JoinColumn(name = "sentiment_summary_id") val sentimentSummary: SentimentSummary,
    @Enumerated(EnumType.STRING) val sentiment: Sentiment,
    @OneToMany(
        mappedBy = "sentimentExample",
        cascade = [CascadeType.ALL]
    ) var sentimentExampleQuotes: MutableList<SentimentExampleQuote> = ArrayList(),
    @Id @GeneratedValue val id: Long? = null
)
