package pl.edu.p.lodz.wiarygodnik.cas.service.analyzer

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.service.ContentAnalyser
import pl.edu.p.lodz.wiarygodnik.cas.service.dto.ContentAnalysis

@Component
class AiContentAnalyser(private val resourceLoader: ResourceLoader, chatModel: ChatModel): ContentAnalyser {

    private val chatClient = ChatClient.create(chatModel)

    override fun analyse(content: String): ContentAnalysis {
        val systemPrompt: String = readSystemPrompt()

        val response = chatClient.prompt()
            .system { system -> system.text(systemPrompt) }
            .user { user -> user.text(content) }
            .call()
            .entity(ContentAnalysis::class.java)

        return response ?: throw RuntimeException("Unexpected response from LLM on content analysis")
    }

    fun readSystemPrompt(): String =
        resourceLoader.getResource("classpath:prompts/summarization_system.txt")
            .inputStream
            .bufferedReader()
            .readText()

}