package pl.edu.p.lodz.wiarygodnik.rgs.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.SEQUENCE
import jakarta.persistence.Id

@Entity
data class PushSubscription(
    @Column(nullable = false, length = 100) val userId: String,
    @Column(nullable = false, length = 500) val endpoint: String,
    @Column(nullable = false, length = 200) val p256dh: String,
    @Column(nullable = false, length = 100) val auth: String,
    @Id @GeneratedValue(strategy = SEQUENCE) val id: Long? = null
)
