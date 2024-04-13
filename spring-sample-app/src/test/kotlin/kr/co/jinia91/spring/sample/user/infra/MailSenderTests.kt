package kr.co.jinia91.spring.sample.user.infra

import kr.co.jinia91.spring.sample.user.domain.MailSender
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MailSenderTests {
    @Autowired
    private lateinit var sut : MailSender
    @Test
    fun `원하는 대상에게 메일을 보내야한다`(){}
}