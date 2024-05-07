package kr.co.jinia91.spring.sample

import kr.co.jinia91.spring.sample.user.adapters.persistance.sql.UserDaoSqlDefinition
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(UserDaoSqlDefinition::class)
class SpringApp

fun main() {
    runApplication<SpringApp>()
}