package pl.edu.p.lodz.wiarygodnik.rgs.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.PushSubscriptionDto
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.toEntity
import pl.edu.p.lodz.wiarygodnik.rgs.repo.PushSubscriptionRepository
import pl.edu.p.lodz.wiarygodnik.rgs.security.PrincipalProvider

@RestController
@RequestMapping("/api/push")
class PushController(private val repository: PushSubscriptionRepository) {

    @PostMapping("/subscribe")
    fun subscribe(@RequestBody request: PushSubscriptionDto) {
        val existing = repository.findByEndpoint(request.endpoint)
        if (existing == null) {
            val currentUserId: String = PrincipalProvider.getCurrentUserId()
            repository.save(request.toEntity(currentUserId))
        }
    }

}
