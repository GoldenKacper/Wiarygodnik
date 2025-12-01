package pl.edu.p.lodz.wiarygodnik

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import pl.edu.p.lodz.wiarygodnik.rgs.ReportGenerationServiceApplication

@SpringBootTest(classes = [ReportGenerationServiceApplication::class])
class ReportApplicationTests {

    @Test
    fun contextLoads() {
    }

}
