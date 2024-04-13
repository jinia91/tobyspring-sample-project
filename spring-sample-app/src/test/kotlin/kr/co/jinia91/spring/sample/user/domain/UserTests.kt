package kr.co.jinia91.spring.sample.user.domain

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import sample.sample.validUser

class UserTests {
    @Test
    fun `로그인 횟수가 50 이상인 유저는 SILVER로 업그레이드된다`() {
        // given
        val user = validUser().apply {
            logInCount = User.UserLevelUpgradePolicy.MIN_LOG_COUNT_FOR_SILVER
        }

        // when
        user.tryUpgradeLevel()

        // then
        user.level shouldBe User.Level.SILVER
    }
}