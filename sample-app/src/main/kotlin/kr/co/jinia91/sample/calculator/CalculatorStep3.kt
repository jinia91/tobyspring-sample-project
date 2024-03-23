package kr.co.jinia91.sample.calculator

import java.io.BufferedReader
import java.io.FileReader

/**
 * 기존 step2에서 비즈니스로직의 공통부분과 예외핸들링을 템플릿으로 추출하고 순수하게 관심분야인 람다만 콜백으로 넘기게 변경
 *
 * @constructor Create empty Calculator3
 */
class CalculatorStep3 {
    fun sum(path: String): Int {
        return doCalculatorCommonTemplate(path, { line, sum -> sum + line.toInt() }, 0)
    }

    fun multiply(path: String): Int {
        return doCalculatorCommonTemplate(path, { line, sum -> sum * line.toInt() }, 1)
    }

    private fun <T> doCalculatorCommonTemplate(
        path: String,
        callBack: (String, T) -> T,
        result: T
    ): T {
        try {
            BufferedReader(FileReader(path)).use { reader ->
                var line: String?
                var result: T = result
                while (reader.readLine().also { line = it } != null) {
                    result = callBack(line!!, result)
                }
                return result!!
            }
        } catch (e: Exception) {
            println("An error occurred. $e")
            throw e
        }
    }
}