package pl.edu.p.lodz.wiarygodnik.rgs.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.AnalysisResult

@Component
class AiAnalysisReportGenerator(chatModel: ChatModel, private val resourceLoader: ResourceLoader) {

    private val chatClient = ChatClient.create(chatModel)

    fun generate(input: AnalysisResult): String {
        val analysisReportGenerationPrompt: String = prepareAnalysisReportGenerationPrompt(input)
        return callChatGeneration(analysisReportGenerationPrompt)
    }

    private fun callChatGeneration(input: String): String = chatClient.prompt()
        .system { system -> system.text(readSystemPrompt()) }
        .user { user -> user.text(input) }
        .call()
        .content()
        ?: throw RuntimeException("LLM returned a null object while generating a report.")

    private fun readSystemPrompt(): String =
        resourceLoader.getResource("classpath:prompts/analysis_report_generation_system.txt")
            .inputStream
            .bufferedReader()
            .readText()

    private fun prepareAnalysisReportGenerationPrompt(analysisResult: AnalysisResult): String = """
        ### Analiza nacechowania:
        ${analysisResult.contentAnalysis.sentiment.description}
        
        ### Przykłady nacechowania:
        ${
        analysisResult.contentAnalysis.sentiment.examples.map {
            """
                
                Rodzaj nacechowania: ${it.sentiment}
                Przykłady:
                ${
                it.quotes.map { (quote, comment) ->
                    """
                    Cytat: ${quote}
                    Komentarz: ${comment}
                    
                """.trimIndent()
                }
            }
                
            """.trimIndent()
        }
    }
        
        ### Porównanie z innymi źródłami:
        ${analysisResult.contentComparison.description}
        
        ### Przykłady porównań:
        ${
        analysisResult.contentComparison.sourcesFacts.map { source ->
            """
                
                Źródło: ${source.url}
                Fakty: ${source.facts.joinToString { it }}
                
            """.trimIndent()
        }
    }
    """.trimIndent()

}