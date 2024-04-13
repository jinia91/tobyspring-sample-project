package kr.co.jinia91.spring.sample.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import javax.sql.DataSource
import kr.co.jinia91.spring.sample.user.application.UserUserCases
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradePolicy
import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import sample.sample.UserFakeService

@TestConfiguration
class TransactionTestConfig {
    @Autowired
    lateinit var userRepository: UserRepository

    @Autowired
    lateinit var userLevelUpgradePolicy: List<UserLevelUpgradePolicy>

    @Autowired
    lateinit var dataSource: DataSource

    @Bean
    @Primary
    fun userService(): UserUserCases {
        return UserFakeService(userRepository, userLevelUpgradePolicy, dataSource)
    }
}

@SpringBootTest(
    classes = [TransactionTestConfig::class]
)
class TransactionTests {

    @Autowired
    private lateinit var sut: UserFakeService

    @Autowired
    private lateinit var userRepository: UserRepository

    private val exId = "4"

    @BeforeEach
    fun setUp() {
        sut.setExId(exId)
    }

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
    }

    private fun given5UserExpectingToUpgrade() {
        val users = (1..5).map {
            User.newOne(
                id = it.toString(),
                name = "jinia$it",
                password = "1Q2w3e4r1!"
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