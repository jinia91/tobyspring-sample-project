package kr.co.jinia91.springframework.core

import kr.co.jinia91.springframework.core.annotation.SpringApplication
import kotlin.test.Test

@SpringApplication
class TestApplication

// SpringApplication 은 반드시 하나만 존재해야 한다.
//@SpringApplication
//class TestApplication2

class TestApplicationContextTests {

    @Test
    fun `test context loads`() {
        SpringBootApplication.run()
    }
}