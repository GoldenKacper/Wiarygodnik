package pl.edu.p.lodz.wiarygodnik.rgs.repo

import org.springframework.data.jpa.repository.JpaRepository
import pl.edu.p.lodz.wiarygodnik.rgs.model.PushSubscription

interface PushSubscriptionRepository : JpaRepository<PushSubscription, Long> {
    fun findByUserId(userId: String): List<PushSubscription>
    fun findByEndpoint(endpoint: String): PushSubscription?
    fun findByUserIdAndEndpoint(userId: String, endpoint: String): PushSubscription?
}
