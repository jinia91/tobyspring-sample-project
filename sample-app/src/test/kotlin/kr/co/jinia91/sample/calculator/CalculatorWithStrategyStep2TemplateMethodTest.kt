package kr.co.jinia91.sample.calculator

import org.junit.jupiter.api.Assertions.*
import kotlin.test.Test

/**
 * 클라이언트 코드가 하위 클래스를 직접 알아야하는데...
 *
 * ENUM 등으로 넘기고 중간에 리졸브 해주는 리졸버가 있어야할까? -> 이미 이렇게 되면 전략패턴에 가까워짐
 *
 */
class CalculatorWithStrategyStep2TemplateMethodTest{
    @Test
    fun testSum() {
        val calculator = PlusCalculator()
        val path = "src/test/resources/numbers.txt"
        val result = calculator.calculate(path)
        assertEquals(10, result)
    }

    @Test
    fun testMultiply() {
        val calculator = MultiplyCalculator()
        val path = "src/test/resources/numbers.txt"
        val result = calculator.calculate(path)
        assertEquals(36, result)
    }
}