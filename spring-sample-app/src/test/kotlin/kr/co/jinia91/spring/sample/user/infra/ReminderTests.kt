package kr.co.jinia91.spring.sample.user.infra

import io.mockk.verify
import kr.co.jinia91.spring.sample.user.domain.Reminder
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ReminderTests {
    @Autowired
    private lateinit var sut : Reminder
    @Test
    fun `원하는 대상에게 메일을 보내야한다`(){
        // given
        val email = "abc@program.co.kr"
        // when
        sut.sendTOUpgradedUser(email)
        // then
        verify(atLeast = 1) { sut.sendTOUpgradedUser(email)}
    }
}