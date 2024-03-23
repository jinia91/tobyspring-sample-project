package kr.co.jinia91.sample.calculator

import java.io.BufferedReader
import java.io.FileReader

/**
 * 고전적인 bufferedReader 와 fileReader 를 사용하고
 *
 * - 리소스 해제
 * - 예외 처리
 *
 * 두가지를 고려한 계산기
 *
 */
class CalculatorStep1 {
    fun sum(path: String): Int {
        var br: BufferedReader? = null
        try {
        br = BufferedReader(FileReader(path))
            var sum = 0
            var line: String?
            while (br.readLine().also { line = it } != null) {
                sum += line!!.toInt()
            }
            br.close()
            return sum
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

    fun multiply(path: String): Int {
        var br: BufferedReader? = null
        try {
            br = BufferedReader(FileReader(path))
            var sum = 1
            var line: String?
            while (br.readLine().also { line = it } != null) {
                sum *= line!!.toInt()
            }
            br.close()
            return sum
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