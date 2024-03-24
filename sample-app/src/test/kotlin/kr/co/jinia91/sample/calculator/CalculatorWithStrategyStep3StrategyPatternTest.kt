package kr.co.jinia91.sample.calculator

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

/**
 * Calculator with strategy step3strategy pattern test
 *
 * @constructor Create empty Calculator with strategy step3strategy pattern test
 */
class CalculatorWithStrategyStep3StrategyPatternTest{
    private val sut = CalculatorWithStrategy(
        listOf(
            PlusStrategy(), MultiplyStrategy()
        )
    )

    @Test
    fun testSum() {
        val path = "src/test/resources/numbers.txt"
        val result = sut.sum(path)
        assertEquals(10, result)
    }

    @Test
    fun testMultiply() {

        val path = "src/test/resources/numbers.txt"
        val result = sut.multiply(path)
        assertEquals(36, result)
    }
}