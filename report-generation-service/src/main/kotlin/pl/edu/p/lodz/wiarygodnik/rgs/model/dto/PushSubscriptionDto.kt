package pl.edu.p.lodz.wiarygodnik.rgs.model.dto

import pl.edu.p.lodz.wiarygodnik.rgs.model.PushSubscription

data class PushSubscriptionDto(val endpoint: String, val keys: Keys) {
    data class Keys(val p256dh: String, val auth: String)
}

fun PushSubscriptionDto.toEntity(userId: String) = PushSubscription(
    userId = userId,
    endpoint = this.endpoint,
    p256dh = this.keys.p256dh,
    auth = this.keys.auth
)   