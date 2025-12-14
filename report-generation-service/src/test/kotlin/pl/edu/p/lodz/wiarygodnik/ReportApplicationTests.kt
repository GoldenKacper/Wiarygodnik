package pl.edu.p.lodz.wiarygodnik

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import pl.edu.p.lodz.wiarygodnik.rgs.ReportGenerationServiceApplication
import org.assertj.core.api.Assertions.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.ApplicationContext

@SpringBootTest(classes = [ReportGenerationServiceApplication::class])
class ReportApplicationTests {

    @Autowired
    lateinit var context: ApplicationContext

    /**
     * Smoke test – verifies that Spring Boot application context
     * loads correctly without errors.
     */
    @Test
    fun contextLoads() {
        assertThat(context).isNotNull
    }
}
