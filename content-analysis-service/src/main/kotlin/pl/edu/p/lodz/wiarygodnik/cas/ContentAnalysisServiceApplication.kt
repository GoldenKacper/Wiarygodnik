package pl.edu.p.lodz.wiarygodnik.cas

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ContentAnalysisServiceApplication

fun main(args: Array<String>) {
    runApplication<ContentAnalysisServiceApplication>(*args)
}
