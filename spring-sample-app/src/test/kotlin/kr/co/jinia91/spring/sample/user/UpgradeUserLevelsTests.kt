package kr.co.jinia91.spring.sample.user

import io.kotest.assertions.withClue
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import sample.sample.validSignUpUserCommand

/**
 * 사용자 레벨 관리 유즈케이스
 *
 * - 사용자는 유저 레벨이 존재하며 BASIC, SILVER, GOLD 세가지 있다
 * - 가입 후 50회 이상 로그인을 하면 BASIC에서 SILVER로 레벨이 업그레이드 된다
 * - SILVER 레벨에서 30회 이상 게시글을 작성하면 GOLD로 레벨이 업그레이드 된다
 * - 사용자 레벨의 변경작업은 일정한 주기를 가지고 일괄적으로 진행된다. 변경 작업 전에는 조건을 충족하더라도 레벨의 변경이 일어나지않는다.(배치작업)
 *   (유즈케이스의 관심사가 아님, 어댑터 레이어에서 제어하면 된다)
 */
@SpringBootTest
class UpgradeUserLevelsTests {
    @Autowired
    private lateinit var sut: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `BASIC 사용자는 50회 이상 로그인을 하면 SILVER 레벨로 업그레이드 된다`() {
        // given
        withClue("BASIC 신규 유저가 존재한다") {
            val userWith50LogInCount = User.newOne(
                id = "11112",
                name = "jinia",
                password = "1Q2w3e4r1!"
            ).apply {
                logInCount = 50
            }
            userRepository.save(userWith50LogInCount)
        }

        // when
        sut.upgradeUserLevels()

        // then
        val user = userRepository.findById("11112")
        user.shouldNotBeNull()
        user.level shouldBe User.Level.SILVER
    }

    @Test
    fun `BASIC 사용자가 아니면 50 이상 로그인해도 SILVER 레벨로 업그레이드 되지 않는다`() {
    }

    @Test
    fun `SILVER 레벨에서 30회 이상 게시글을 작성하면 GOLD 레벨로 업그레이드 된다`() {
    }

    @Test
    fun `30회 이상 게시글을 작성하더라도 SILVER 레벨이 아니면 GOLD 레벨로 업그레이드 되지 않는다`() {
    }

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }
}