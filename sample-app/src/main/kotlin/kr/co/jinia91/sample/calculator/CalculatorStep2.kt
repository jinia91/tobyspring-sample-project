package kr.co.jinia91.sample.calculator

import java.io.BufferedReader
import java.io.FileReader

/**
 * 리소스 해제를 try-with-resources인 use 함수로 대체
 *
 * - use 함수도 템플릿 콜백 패턴임
 *
 * 여전히
 * - 예외 핸들링
 * - 비즈니스 로직중 공통 로직
 *
 * 두가지 공통 부분이 남아 있다.
 */
class CalculatorStep2 {
    fun sum(path: String): Int {
        try {
            return BufferedReader(FileReader(path)).use {
                var sum = 0
                var line: String?
                while (it.readLine().also { line = it } != null) {
                    sum += line!!.toInt()
                }
                sum
            }
        } catch (e: Exception) {
            println("An error occurred. $e")
            throw e
        }
    }

    fun multiply(path: String): Int {
        try {
            return BufferedReader(FileReader(path)).use {
                var sum = 1
                var line: String?
                while (it.readLine().also { line = it } != null) {
                    sum *= line!!.toInt()
                }
                sum
            }
        } catch (e: Exception) {
            println("An error occurred. $e")
            throw e
        }
    }
}