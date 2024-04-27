package kr.co.jinia91.spring.sample.user.adapters.reminder

import kr.co.jinia91.spring.sample.user.domain.Reminder
import org.springframework.stereotype.Component

@Component
class EmailSender : Reminder {
    override fun sendTOUpgradedUser(email: String) {
        println("send email to $email")
    }
}