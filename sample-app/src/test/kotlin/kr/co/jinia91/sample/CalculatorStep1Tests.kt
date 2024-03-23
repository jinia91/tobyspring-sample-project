package kr.co.jinia91.sample

import io.kotest.matchers.shouldBe
import kr.co.jinia91.sample.calculator.CalculatorStep1
import kotlin.test.Test

class CalculatorStep1Tests {
    @Test
    fun testSum() {
        val calculatorStep1 = CalculatorStep1()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.sum(path)
        result shouldBe 10
    }

    @Test
    fun testMultiply() {
        val calculatorStep1 = CalculatorStep1()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.multiply(path)
        result shouldBe 36
    }
}