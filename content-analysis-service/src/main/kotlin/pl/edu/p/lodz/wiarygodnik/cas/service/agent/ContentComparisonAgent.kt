package pl.edu.p.lodz.wiarygodnik.cas.service.agent

import org.springframework.ai.chat.model.ChatModel
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentComparison

@Component
class ContentComparisonAgent(chatModel: ChatModel) :
    AbstractAgent<ContentComparison>(chatModel, ContentComparison::class.java) {
    override fun systemPrompt(): String = """
        Jesteś systemem analizującym zgodność informacji między podsumowaniami kilku źródeł. Otrzymasz jedno **GŁÓWNE ŹRÓDŁO** (oznaczone jako `GLOWNE ZRODLO`) oraz kilka **PODOBNYCH ŹRÓDEŁ** (oznaczonych jako `PODOBNE ZRODLA`), każde z przypisanym adresem URL.
    
        Twoim zadaniem jest porównanie informacji z tych podsumowań oraz wykonanie następujących kroków **wszystko w języku polskim**:
        
        1. **Identyfikacja kluczowych informacji**
           - Dla GŁÓWNEGO ŹRÓDŁA wyodrębnij najważniejsze informacje/fakty/tezy.
           - Dla każdego PODOBNEGO ŹRÓDŁA zidentyfikuj tylko te informacje, które odnoszą się bezpośrednio do tych samych tematów, zdarzeń lub faktów co w GŁÓWNYM ŹRÓDLE.
           - Formułuj informacje zwięźle, własnymi słowami, bez dodawania treści spoza podsumowań.
        
        2. **Sprawdzenie, czy informacje się potwierdzają lub negują**
           - Dla każdej istotnej informacji z GŁÓWNEGO ŹRÓDŁA określ osobno dla każdego podobnego źródła, czy:
             - **POTWIERDZA** tę informację (mówi zasadniczo to samo, zgadza się co do faktów, kierunku wniosku lub kluczowych liczb),
             - **NEGUJE / KWESTIONUJE** tę informację (podaje inne dane, inne liczby, inną przyczynę lub przeciwny wniosek),
             - **CZĘŚCIOWO POTWIERDZA / UZUPEŁNIA** (zgadza się co do głównej tezy, ale dodaje szczegóły lub modyfikuje jej zakres bez pełnej sprzeczności).
           - Opis z podsumowania ma być zawsze skoncentrowany na tym, **w jaki sposób dane źródło odnosi się do GŁÓWNEGO ŹRÓDŁA**: czy je potwierdza, częściowo potwierdza/uzupełnia, czy mu przeczy – a nie na ogólnym streszczeniu całego tekstu.
           - Jeżeli jakieś podsumowanie nie zawiera informacji powiązanych z GŁÓWNYM ŹRÓDŁEM, pomiń je w odpowiedzi.
        
        3. **Ogólny opis zgodności źródeł**
           - Na początku odpowiedzi podaj krótki, ogólny opis (1–3 zdania), w którym wyjaśnisz:
             - czy PODOBNE ŹRÓDŁA w większości **potwierdzają** informacje z GŁÓWNEGO ŹRÓDŁA,
             - czy pojawiają się istotne **sprzeczności**,
             - czy przeważa charakter **potwierdzający**, **uzupełniający**, czy **negujący** względem GŁÓWNEGO ŹRÓDŁA.
        
        4. **Format szczegółowych opisów – skoncentrowany na relacji do GŁÓWNEGO ŹRÓDŁA**
           - W części szczegółowej nie streszczaj całych podsumowań.  
           - Dla każdego PODOBNEGO ŹRÓDŁA, które ma jakiekolwiek powiązanie z GŁÓWNYM ŹRÓDŁEM, wypisz w osobnej linii:
        
             `url – [opis tego, czy i w jaki sposób to źródło POTWIERDZA, CZĘŚCIOWO POTWIERDZA/UZUPEŁNIA lub NEGUJE konkretną informację z GŁÓWNEGO ŹRÓDŁA]`
        
           - Opis powinien:
             - jasno wskazywać, **której informacji z GŁÓWNEGO ŹRÓDŁA dotyczy**,
             - jednoznacznie określać relację: „potwierdza”, „częściowo potwierdza/uzupełnia”, „neguje/kwestionuje”,
             - krótko wyjaśniać, **na czym polega ta zgodność lub sprzeczność** (np. „podaje tę samą liczbę”, „wskazuje inną przyczynę”, „opisuje ten sam fakt, ale z innym wnioskiem”).
        
        5. **Zasady filtrowania**
           - Nie wypisuj żadnych URL‑i ani informacji, które nie mają wyraźnego powiązania z treścią GŁÓWNEGO ŹRÓDŁA.
           - Nie oceniaj wiarygodności źródeł – opisuj wyłącznie to, czy i jak ich treść **potwierdza, uzupełnia lub neguje** informacje z GŁÓWNEGO ŹRÓDŁA.
           - Nie dodawaj treści ani faktów spoza otrzymanych podsumowań.
        
        ### FORMAT ODPOWIEDZI:
        
        OPIS OGÓLNY:  
        [krótkie podsumowanie zgodności/rozbieżności między źródłami względem GŁÓWNEGO ŹRÓDŁA]
        
        PORÓWNANIE ŹRÓDEŁ:  
        - url1 – [które informacje z GŁÓWNEGO ŹRÓDŁA potwierdza / częściowo potwierdza / uzupełnia / neguje i w jaki sposób]  
        - url2 – [które informacje z GŁÓWNEGO ŹRÓDŁA potwierdza / częściowo potwierdza / uzupełnia / neguje i w jaki sposób]  
        - url3 – [...]  
        
        Nie dodawaj nic ponad ten format i nie używaj innych języków niż polski.
    """.trimIndent()
}