package kr.co.jinia91.springframework.core.utils

import io.kotest.matchers.shouldNotBe
import kotlin.test.Test

private val log = mu.KotlinLogging.logger {}

class ClassPathScannerTest{
    @Test
    fun `주어진 패키지 경로로 모든 클래스 정보를 가져올 수 있다`(){
        val classes = ClassPathScanner.getAllClassesInSrc("kr.co.jinia91.springframework.core")
        classes.size shouldNotBe 0
        classes.forEach {
            log.debug { "class = $it" }
        }
    }

    @Test
    fun `현재 위치의 상대경로로 모든 클래스 정보를 가져올 수 있다`(){
        val packageName = this::class.qualifiedName!!.substringBeforeLast('.')

        val classes = ClassPathScanner.getAllClassesInSrc(packageName)
        classes.size shouldNotBe 0
        classes.forEach {
           log.debug { "class = $it" }
        }
    }

    @Test
    fun `SpringApplication 어노테이션이 붙은 클래스를 찾을 수 있다`(){
        val springApplication = ClassPathScanner.findSpringApplication()
        log.debug { "springApplication = $springApplication" }
        springApplication shouldNotBe null
    }

    @Test
    fun `여러 패키지 경로를 제공하면, 모든 클래스를 찾을 수 있다`() {
        val classes = ClassPathScanner.getAllClassesInSrc(
            arrayOf("kr.co.jinia91.springframework.core.utils", "kr.co.jinia91.springframework.core.annotation")
        )
        classes.size shouldNotBe 0
        classes.forEach {
            log.debug { "class = $it" }
        }
    }
}