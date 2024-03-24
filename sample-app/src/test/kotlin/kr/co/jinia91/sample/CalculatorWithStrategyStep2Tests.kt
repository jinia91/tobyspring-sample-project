package kr.co.jinia91.sample

import io.kotest.matchers.shouldBe
import kr.co.jinia91.sample.calculator.CalculatorStep2
import kotlin.test.Test

class CalculatorWithStrategyStep2Tests {
    @Test
    fun testSum() {
        val calculatorStep1 = CalculatorStep2()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.sum(path)
        result shouldBe 10
    }

    @Test
    fun testMultiply() {
        val calculatorStep1 = CalculatorStep2()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.multiply(path)
        result shouldBe 36
    }
}