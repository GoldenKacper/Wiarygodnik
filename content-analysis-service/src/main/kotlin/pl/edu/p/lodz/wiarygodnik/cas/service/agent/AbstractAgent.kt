package pl.edu.p.lodz.wiarygodnik.cas.service.agent

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.client.advisor.StructuredOutputValidationAdvisor
import org.springframework.ai.chat.client.advisor.api.BaseAdvisor
import org.springframework.ai.chat.model.ChatModel

abstract class AbstractAgent<T>(chatModel: ChatModel, private val responseType: Class<T>) {

    private val chatClient = ChatClient.create(chatModel)
    private val outputValidationAdvisor = StructuredOutputValidationAdvisor.builder()
        .outputType(responseType)
        .maxRepeatAttempts(3)
        .advisorOrder(BaseAdvisor.HIGHEST_PRECEDENCE + 1000)
        .build()

    protected abstract fun systemPrompt(): String

    fun work(input: String): T = chatClient.prompt()
        .system { system -> system.text(systemPrompt()) }
        .user { user -> user.text(input) }
        .advisors(outputValidationAdvisor)
        .call()
        .entity(responseType)
        ?: throw RuntimeException("Agent ${responseType.name} returned a null object.")

}