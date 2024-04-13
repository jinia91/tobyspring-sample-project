package kr.co.jinia91.spring.sample

import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Configuration
import sample.sample.UserFakeRepository

@Configuration
class TestConfig {
//    @Bean
//    @Primary
    fun userFakeRepository(): UserRepository {
        return UserFakeRepository()
    }
}

@SpringBootTest
class SpringAppTest {
    @Test
    fun contextLoads() {
    }
}