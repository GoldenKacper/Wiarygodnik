package pl.edu.p.lodz.wiarygodnik.rgs

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class ReportGenerationServiceApplication

fun main(args: Array<String>) {
    runApplication<ReportGenerationServiceApplication>(*args)
}
