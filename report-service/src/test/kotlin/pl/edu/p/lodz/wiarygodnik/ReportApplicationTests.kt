package pl.edu.p.lodz.wiarygodnik

import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import pl.edu.p.lodz.wiarygodnik.report.ReportApplication

@SpringBootTest(classes = [ReportApplication::class])
class ReportApplicationTests {

	@Test
	fun contextLoads() {
	}

}
