package kr.co.jinia91.spring.sample.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import javax.sql.DataSource
import kr.co.jinia91.spring.sample.user.application.UserServiceImpl
import kr.co.jinia91.spring.sample.user.application.UserServiceTxImpl
import kr.co.jinia91.spring.sample.user.application.UserUserCases
import kr.co.jinia91.spring.sample.user.domain.Reminder
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradePolicy
import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.annotation.Transactional
import sample.sample.UserFakeService

@TestConfiguration
class TransactionTestConfig {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userLevelUpgradePolicy: List<UserLevelUpgradePolicy>

    @Autowired
    lateinit var reminder: Reminder

    @Autowired
    lateinit var transactionManager: PlatformTransactionManager

    @Bean
    @Qualifier("userTransactionService")
    fun userService(): UserUserCases {
        val fake = UserFakeService(userRepository, userLevelUpgradePolicy, reminder).apply {
            setExId("4")
        }

        return UserServiceTxImpl(fake, transactionManager)
    }
}

@SpringBootTest(
    classes = [TransactionTestConfig::class]
)
class TransactionTests {

    @Autowired
    @Qualifier("userTransactionService")
    private lateinit var sut: UserUserCases

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `유저 레벨 업그레이드 중 에러가 발생하면 롤백된다`() {
        // given
        withClue("유저 5명이 순서대로 존재한다") { given5UserExpectingToUpgrade() }

        // when
        shouldThrow<IllegalArgumentException> { sut.upgradeUserLevels() }

        // then
        val users = userRepository.findAll()
        users.size shouldBe 5
        users.forEach {
            withClue("유저 ${it.id}의 레벨은 BASIC이다") {
                it.level shouldBe User.Level.BASIC
            }
        }

        // tearDown
        userRepository.deleteAll()
    }

    private fun given5UserExpectingToUpgrade() {
        val users = (1..5).map {
            User.newOne(
                id = it.toString(),
                name = "jinia$it",
                password = "1Q2w3e4r1!",
                email = null
            ).apply {
                logInCount = 50
                level = User.Level.BASIC
            }
        }
        users.forEach {
            userRepository.save(it)
        }
    }
}