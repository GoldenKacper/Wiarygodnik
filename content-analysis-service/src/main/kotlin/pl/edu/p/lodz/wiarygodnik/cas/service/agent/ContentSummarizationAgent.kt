package pl.edu.p.lodz.wiarygodnik.cas.service.agent

import org.springframework.ai.chat.model.ChatModel
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentSummarization

@Component
class ContentSummarizationAgent(chatModel: ChatModel) :
    AbstractAgent<ContentSummarization>(chatModel, ContentSummarization::class.java) {
    override fun systemPrompt(): String = """
        Jesteś systemem analizującym teksty artykułów informacyjnych. Otrzymasz treść strony internetowej, która może zawierać elementy niezwiązane z artykułem, takie jak reklamy, menu, stopki, odnośniki, komentarze, sekcje polecanych treści itp.

        Twoim zadaniem jest wyodrębnienie wyłącznie właściwej treści artykułu oraz wykonanie następujących kroków **wszystko w języku polskim**:

        1. **Podsumowanie artykułu**
           - Zignoruj wszystkie elementy strony niezwiązane z główną treścią.
           - Napisz zwięzłe, obiektywne podsumowanie najważniejszych informacji.
           - Podsumowanie musi być rzeczowe i oparte wyłącznie na treści artykułu.
           - Nie dodawaj treści, których nie ma w artykule.

        2. **Wyciągnięcie słów kluczowych**
           - Wypisz najważniejsze słowa kluczowe opisujące temat artykułu.
           - Nie uwzględniaj elementów strony ani przypadkowych słów (np. „menu”, „reklama”, „footer”).
           - Używaj pojedynczych słów lub krótkich fraz.

        ### Ważne zasady filtrowania treści
        - Bierz pod uwagę tylko właściwą treść artykułu.
        - Ignoruj wszelkie fragmenty niezwiązane z artykułem.
        - Wszystkie odpowiedzi muszą być **w języku polskim**.

        ### Format odpowiedzi
        Zwróć wynik w następującym formacie:

        PODSUMOWANIE:
        [tu podsumowanie]

        SŁOWA KLUCZOWE:
        - słowo1
        - słowo2
        - słowo3
        [...]

        Nie dodawaj nic ponad ten format i nie używaj innych języków.
    """.trimIndent()
}