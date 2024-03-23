package kr.co.jinia91.sample

import io.kotest.matchers.shouldBe
import kr.co.jinia91.sample.calculator.CalculatorStep1
import kr.co.jinia91.sample.calculator.CalculatorStep2
import kr.co.jinia91.sample.calculator.CalculatorStep3
import kotlin.test.Test

class CalculatorStep3Tests {
    @Test
    fun testSum() {
        val calculatorStep1 = CalculatorStep3()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.sum(path)
        result shouldBe 10
    }

    @Test
    fun testMultiply() {
        val calculatorStep1 = CalculatorStep3()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.multiply(path)
        result shouldBe 36
    }
}