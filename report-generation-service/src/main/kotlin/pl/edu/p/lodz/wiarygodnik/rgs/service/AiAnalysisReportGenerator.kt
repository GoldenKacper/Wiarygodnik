package pl.edu.p.lodz.wiarygodnik.rgs.service

import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.model.ChatModel
import org.springframework.core.io.ResourceLoader
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.AnalysisResultMessage
import pl.edu.p.lodz.wiarygodnik.rgs.model.dto.ReportGenerationResult

@Component
class AiAnalysisReportGenerator(chatModel: ChatModel, private val resourceLoader: ResourceLoader) {

    private val chatClient = ChatClient.create(chatModel)

    fun generate(input: AnalysisResultMessage): ReportGenerationResult {
        val analysisReportGenerationPrompt: String = prepareAnalysisReportGenerationPrompt(input)
        return callChatGeneration(analysisReportGenerationPrompt)
    }

    private fun callChatGeneration(input: String): ReportGenerationResult = chatClient.prompt()
        .system { system -> system.text(readSystemPrompt()) }
        .user { user -> user.text(input) }
        .call()
        .entity(ReportGenerationResult::class.java)
        ?: throw RuntimeException("LLM returned a null object while generating a report.")

    private fun readSystemPrompt(): String =
        resourceLoader.getResource("classpath:prompts/analysis_report_generation_system.txt")
            .inputStream
            .bufferedReader()
            .readText()

    private fun prepareAnalysisReportGenerationPrompt(analysisResult: AnalysisResultMessage): String = """
        ### Analiza nacechowania:
        ${analysisResult.contentAnalysis.sentiment.summary}
        
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
        
        ${
        analysisResult.contentComparison?.let {
            """
        ### Porównanie z innymi źródłami:
        ${it.description}

        ### Przykłady porównań:
        ${
                it.sourcesFacts.map { source ->
                    """
                
                Źródło: ${source.url}
                Fakty: ${source.facts.joinToString { it }}
                
            """.trimIndent()
                }
            }
            """
        }
    }
    """.trimIndent()

}