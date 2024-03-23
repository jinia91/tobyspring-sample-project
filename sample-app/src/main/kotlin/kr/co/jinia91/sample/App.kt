package kr.co.jinia91.sample

import kr.co.jinia91.springframework.core.SimpleApplicationContext
import kr.co.jinia91.sample.application.User
import kr.co.jinia91.sample.application.UserDao
import kr.co.jinia91.springframework.core.SpringBootApplication
import kr.co.jinia91.springframework.core.annotation.SpringApplication

@SpringApplication
class SampleApp

fun main() {
    val context = SpringBootApplication.run()
}