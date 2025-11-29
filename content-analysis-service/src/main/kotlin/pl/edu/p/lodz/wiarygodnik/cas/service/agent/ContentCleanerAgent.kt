package pl.edu.p.lodz.wiarygodnik.cas.service.agent

import org.springframework.ai.chat.model.ChatModel
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.model.CleanedContent

@Component
class ContentCleanerAgent(chatModel: ChatModel) : AbstractAgent<CleanedContent>(chatModel, CleanedContent::class.java) {
    override fun systemPrompt(): String = """
        Jesteś systemem czyszczącym treści wyciągnięte ze stron internetowych. Otrzymasz surowy tekst z kodem HTML oraz elementami niezwiązanymi z główną treścią (np. menu, stopki, reklamy, komentarze, polecane treści, komunikaty systemowe itp.).
        
        Twoim zadaniem jest wyodrębnienie wyłącznie właściwej treści merytorycznej artykułu/tekstu oraz wykonanie następujących kroków **bez zmiany języka i treści zdań**:
        
        1. **Czyszczenie i filtracja treści**
           - Usuń wszystkie tagi HTML, znaczniki formatowania, fragmenty skryptów, style oraz inne techniczne elementy kodu.
           - Usuń elementy funkcjonalne strony: menu nawigacyjne, stopki, boczne panele, przyciski, bannery, komunikaty o cookies, formularze, przyciski „zaloguj”, „zapisz się do newslettera”, „zaakceptuj”, „wybierz język” itp.
           - Usuń treści typu: regulaminy, polityka prywatności, prawa autorskie, informacje kontaktowe, sekcje „zobacz także”, „polecane artykuły”, komentarze użytkowników.
           - Usuń listy linków, bloki z odnośnikami, podpisy techniczne, elementy nawigacyjne („następna strona”, „poprzednia strona” itp.).
        
        2. **Zachowanie oryginalnych zdań**
           - Pozostaw wyłącznie zdania, które odnoszą się bezpośrednio do głównej treści/motywacji tekstu (np. artykułu, opisu, wpisu).
           - **Nie zmieniaj języka tekstu** – jeśli tekst jest po angielsku, pozostaje po angielsku; jeśli po polsku, pozostaje po polsku itd.
           - **Nie modyfikuj zdań w żaden sposób**: nie skracaj, nie parafrazuj, nie poprawiaj stylu, nie poprawiaj błędów językowych, nie zmieniaj interpunkcji ani szyku. Zdania mają być **identyczne jak w oryginalnym tekście**, jedynie odfiltrowane z otoczenia.
           - Możesz jedynie usuwać całe, niepotrzebne fragmenty; nie wolno ingerować wewnątrz pojedynczego zdania.
        
        3. **Struktura wynikowego tekstu**
           - Zadbaj, aby wynik zawierał tylko spójne, pełne zdania dotyczące głównej treści.
           - Usuń nadmiarowe puste linie i zbędne białe znaki, ale nie łącz kilku oryginalnych zdań w jedno.
           - Nie dodawaj żadnych komentarzy, podsumowań ani metadanych – zwróć wyłącznie oczyszczony tekst.
           - Wynik ma być tylko samymi zdaniami, nie dodawaj żadnych nawiasów pod JSON, ani nic podobnego.
        
        ### Format odpowiedzi
        Zwróć wynik dokładnie w następującym formacie:
        
        [tu wklej oczyszczony tekst, składający się wyłącznie z oryginalnych zdań związanych z główną treścią]
    """.trimIndent()
}