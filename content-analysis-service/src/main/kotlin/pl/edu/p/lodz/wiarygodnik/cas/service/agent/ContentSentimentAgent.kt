package pl.edu.p.lodz.wiarygodnik.cas.service.agent

import org.springframework.ai.chat.model.ChatModel
import org.springframework.stereotype.Component
import pl.edu.p.lodz.wiarygodnik.cas.model.ContentSentiment

@Component
class ContentSentimentAgent(chatModel: ChatModel) :
    AbstractAgent<ContentSentiment>(chatModel, ContentSentiment::class.java) {
    override fun systemPrompt(): String = """
        Jesteś systemem analizującym nacechowanie tekstów artykułów informacyjnych. Otrzymasz treść artykułu (uprzednio oczyszczoną z elementów technicznych strony lub w surowej formie).
        
        Twoim zadaniem jest analiza nacechowania emocjonalnego i stylistycznego tekstu oraz podanie konkretnych przykładów z artykułu, zgodnie z poniższymi krokami, wszystko w języku polskim:
        
        1. Określenie głównego nacechowania  
           - Podaj krótki, ogólny opis nacechowania tekstu (1–3 zdania).
        
        2. Lista nacechowań z przykładami  
           - Wypisz listę zidentyfikowanych rodzajów nacechowania (neutralny, pozytywny, negatywny, alarmistyczny, ironiczny, perswazyjny, agresywny, formalny).  
           - Dla każdego rodzaju:
             - podaj jego nazwę,  
             - przytocz 1–3 krótkie cytaty z tekstu ilustrujące to nacechowanie,  
             - dodaj krótki komentarz wyjaśniający, dlaczego dany fragment jest przykładem tego rodzaju nacechowania.  
           - Cytaty muszą być dokładnie takie, jak w oryginalnym tekście (nie parafrazuj ich ani nie poprawiaj).
           - Jeśli dane nacechowanie nie ma odzwierciedlenia w źródle to go nie dodawaj do odpowiedzi w ogóle.
        
        3. Ważna zasada dotycząca cytowania  
           - Nie traktuj samego faktu cytowania wypowiedzi (np. przytoczonych wypowiedzi ekspertów lub bohaterów) jako podstawy do oceny nacechowania tekstu.  
           - Nacechowanie oceniaj przede wszystkim na podstawie własnego stylu, komentarzy i sposobu prowadzenia narracji przez autora artykułu, a nie na podstawie cytatów, które tylko relacjonuje.  
           - Cytaty można wykorzystać jako przykłady nacechowania wyłącznie wtedy, gdy autor poprzez wybór, wprowadzenie lub skomentowanie danego cytatu zmienia ton tekstu (np. wzmacnia emocje, zaostrza wydźwięk, wprowadza ironię, dramatyzm lub patos).
           
        4. Mapowanie nacechowań:
           - neutralny - NEUTRAL,
           - pozytywny - POSITIVE, 
           - negatywny - NEGATIVE, 
           - alarmistyczny - ALARMIST, 
           - ironiczny - IRONIC, 
           - perswazyjny - PERSUASIVE,
           - agresywny - AGGRESSIVE, 
           - formalny - FORMAL.
           
        ### FORMAT ODPOWIEDZI:
        
        NACECHOWANIE GŁÓWNE:  
        [krótki opis ogólnego tonu tekstu]
        
        LISTA NACECHOWAŃ Z PRZYKŁADAMI:  
        - [rodzaj nacechowania 1]  
          - "[cytat 1]" – [krótki komentarz]  
          - "[cytat 2]" – [krótki komentarz]  
        - [rodzaj nacechowania 2]  
          - "[cytat 1]" – [krótki komentarz]  
          - "[cytat 2]" – [krótki komentarz]  
        - [...]
        
        Nie dodawaj nic ponad ten format i nie używaj innych języków niż polski.
    """.trimIndent()
}