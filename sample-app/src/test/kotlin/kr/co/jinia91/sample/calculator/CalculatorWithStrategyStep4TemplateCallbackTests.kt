package kr.co.jinia91.sample.calculator

import io.kotest.matchers.shouldBe
import kotlin.test.Test

class CalculatorWithStrategyStep4TemplateCallbackTests {
    @Test
    fun testSum() {
        val calculatorStep1 = CalculatorStep4TemplateCallback()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.sum(path)
        result shouldBe 10
    }

    @Test
    fun testMultiply() {
        val calculatorStep1 = CalculatorStep4TemplateCallback()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.multiply(path)
        result shouldBe 36
    }

    /**
     * 템플릿 콜백패턴의 경우 클라이언트에게 콜백 함수 정의 권한을 열어 줄 수 있어, 훨씬 유연한 사용이 가능하다.
     *
     * 다만 클라이언트가 주입할 콜백을 알고 있으므로 계층적 침투라고 볼 수 도 있을까?
     * e.g) 애플리케이션 레이어에서 도메인 레이어에 있는 템플릿에 콜백을 주입한다면, 이건 비즈니스 로직이 애플리케이션 레이어에 정의된건 아닌가?
     *
     */
    @Test
    fun testSubtract() {
        val calculatorStep1 = CalculatorStep4TemplateCallback()
        val path = "src/test/resources/numbers.txt"
        val result = calculatorStep1.doCalculatorCommonTemplate(path, { line, sum -> sum - line.toInt() }, 0)
        result shouldBe -10
    }
}