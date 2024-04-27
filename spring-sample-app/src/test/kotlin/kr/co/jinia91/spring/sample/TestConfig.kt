package kr.co.jinia91.spring.sample

import io.mockk.spyk
import kr.co.jinia91.spring.sample.user.adapters.reminder.EmailSender
import kr.co.jinia91.spring.sample.user.domain.Reminder
import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import sample.sample.UserFakeRepository

@Configuration
class TestConfig {
    @Bean
    @Primary
    fun mailSenderSpy(): Reminder {
        return spyk(EmailSender())
    }
}

@SpringBootTest
class SpringAppTest {
    @Test
    fun contextLoads() {
    }
}