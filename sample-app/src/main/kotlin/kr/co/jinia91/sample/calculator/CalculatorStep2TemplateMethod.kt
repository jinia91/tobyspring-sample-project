package kr.co.jinia91.sample.calculator

import java.io.BufferedReader
import java.io.FileReader

/**
 * 템플릿 메서드를 사용한 구조
 *
 * 공통코드를 슈퍼 클래스로 올리고 구현체가 필요한 부분만 구현하도록 한다.
 *
 * - 하위 클레스가 슈퍼 클래스의 모든 메서드를 알고 있어야하고, 강결합 문제
 */
abstract class CalculatorTemplate {
    abstract val initialValue: Int

    fun calculate(path: String): Int {
        var br: BufferedReader? = null
        try {
            br = BufferedReader(FileReader(path))
            var result = initialValue
            var line: String?
            while (br.readLine().also { line = it } != null) {
                result = calculateInternal(result, line!!.toInt())
            }
            br.close()
            return result
        } catch (e: Exception) {
            println("An error occurred. $e")
            throw e
        } finally {
            try {
                br?.close()
            } catch (e: Exception) {
                println("An error occurred. $e")
                throw e
            }
        }
    }
    abstract fun calculateInternal(result: Int, number: Int) : Int

}

class PlusCalculator(override val initialValue: Int = 0) : CalculatorTemplate() {
    override fun calculateInternal(result: Int, number: Int): Int {
        return result + number
    }
}

class MultiplyCalculator(override val initialValue: Int = 1) : CalculatorTemplate() {
    override fun calculateInternal(result: Int, number: Int): Int {
        return result * number
    }
}
