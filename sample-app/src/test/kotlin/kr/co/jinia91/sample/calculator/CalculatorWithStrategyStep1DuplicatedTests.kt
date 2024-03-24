package kr.co.jinia91.sample.calculator

import io.kotest.matchers.shouldBe
import kr.co.jinia91.sample.calculator.CalculatorStep1Duplicated
import kotlin.test.Test

/**
 * 클라이언트로서 사용 예제
 */
class CalculatorWithStrategyStep1DuplicatedTests {
    @Test
    fun testSum() {
        val calculatorStep1Duplicated = CalculatorStep1Duplicated()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1Duplicated.sum(path)
        result shouldBe 10
    }

    @Test
    fun testMultiply() {
        val calculatorStep1Duplicated = CalculatorStep1Duplicated()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1Duplicated.multiply(path)
        result shouldBe 36
    }
}