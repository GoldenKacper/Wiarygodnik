package pl.edu.p.lodz.wiarygodnik.cas

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import pl.edu.p.lodz.wiarygodnik.cas.ContentAnalysisServiceApplication
import org.springframework.context.ApplicationContext
import org.junit.jupiter.api.Disabled

@Disabled("Application context requires external dependencies")
@SpringBootTest(classes = [ContentAnalysisServiceApplication::class])
class ContentAnalysisServiceApplicationTests {
    /**
     * Smoke test – verifies that Spring Boot application context
     * loads correctly without errors.
     */
    @Test
    fun contextLoads() {}

}
