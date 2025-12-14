package pl.edu.p.lodz.wiarygodnik.cas

import org.junit.jupiter.api.Test
import org.assertj.core.api.Assertions.assertThat

class MathTest {

    /**
     * The simplest test for aplication - simply Math Test.
     * Check the correctness of adding two numbers.
     */
    @Test
    fun `should add two numbers correctly`() {
        // given
        val a = 3
        val b = 2

        // when
        val result = a + b

        // then
        assertThat(result).isEqualTo(5)
    }
}