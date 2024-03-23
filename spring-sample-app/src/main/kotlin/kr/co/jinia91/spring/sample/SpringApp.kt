package kr.co.jinia91.spring.sample

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(UserDaoSqlDefinition::class)
class SpringApp

fun main() {
    runApplication<SpringApp>()
}