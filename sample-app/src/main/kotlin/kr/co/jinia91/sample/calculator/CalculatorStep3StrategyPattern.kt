package kr.co.jinia91.sample.calculator

import java.io.BufferedReader
import java.io.FileReader

/**
 * Enum resolver 를 사용한 전략패턴
 *
 * resolver 자체는 템플릿 메서드도 사용할 수 있음
 *
 * 핵심은 공통 로직을 알고 있는 오브젝트는 CalculatorWithStrategy 뿐이라는 것.
 *
 * 템플릿 메서드였다면 하위 구현체 모두가 슈퍼클래스를 상속받으므로 슈퍼 클래스의 공통 로직을 알고 있으며 이에 영향을 받는다는 문제가 있음!
 *
 * 합성을 통해 이러한 문제를 해결한, 일반적으로 좀더 나은 구현이라고 생각한다.
 *
 */
class CalculatorWithStrategy(
    private val strategies: List<CalculatorStrategy>,
) {
    fun sum(path: String): Int {
        return calculate(path, CalculateOperation.SUM)
    }

    fun multiply(path: String): Int {
        return calculate(path, CalculateOperation.MULTIPLY)
    }

    private fun calculate(path: String, operation: CalculateOperation): Int {
        var br: BufferedReader? = null
        try {
            br = BufferedReader(FileReader(path))
            val strategy = strategies.find { it.isSupport() == operation }
                ?: throw IllegalArgumentException("not supported operation. operation: $operation")
            var result = strategy.initValue()
            var line: String?
            while (br.readLine().also { line = it } != null) {
                result = strategy.calculate(result, line!!.toInt())
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
}

enum class CalculateOperation {
    SUM, MULTIPLY
}

interface CalculatorStrategy {
    fun calculate(result: Int, number: Int): Int
    fun initValue(): Int
    fun isSupport(): CalculateOperation
}

class PlusStrategy : CalculatorStrategy {
    override fun calculate(result: Int, number: Int): Int {
        return result + number
    }

    override fun initValue(): Int {
        return 0
    }

    override fun isSupport(): CalculateOperation {
        return CalculateOperation.SUM
    }
}

class MultiplyStrategy : CalculatorStrategy {
    override fun calculate(result: Int, number: Int): Int {
        return result * number
    }

    override fun initValue(): Int {
        return 1
    }

    override fun isSupport(): CalculateOperation {
        return CalculateOperation.MULTIPLY
    }
}