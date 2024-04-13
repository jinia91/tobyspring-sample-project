package kr.co.jinia91.spring.sample.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import kr.co.jinia91.spring.sample.user.application.UserService
import kr.co.jinia91.spring.sample.user.domain.AlreadyUserIdExist
import kr.co.jinia91.spring.sample.user.domain.InvalidPassword
import kr.co.jinia91.spring.sample.user.domain.InvalidUserName
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import sample.sample.validSignUpUserCommand

/**
 * 사용자 가입 유즈케이스 명세 정리
 *
 * - 사용자는 아이디, 이름, 비밀번호를 입력하면 아이디가 중복되지 않을경우 가입될 수 있다.
 *
 *   x 아이디가 중복되면 안된다
 *
 *   x 이름이 1~10자 사이여야 한다
 *
 *   x 비밀번호가 8자 미만, 16자 초과이면 안된다
 *
 *  x 비밀번호가 소문자, 대문자, 숫자, 특수문자를 모두 포함해야 한다
 *
 * - 사용자는 유저 레벨이 존재하며 BASIC, SILVER, GOLD 세가지 있다
 * - 사용자는 최초 가입시 BASIC 레벨이고 , 이후 활동에 따라 한단계씩 업그레이드 될 수 있다
 */
@SpringBootTest
class SignUpUserTests {
    @Autowired
    private lateinit var sut: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `유효한 사용자 정보가 주어지면 사용자는 정상적으로 가입되고 BASIC 레벨이다`() {
        // given
        val command = validSignUpUserCommand

        // when
        val signUpUserInfo = sut.signUp(command)

        // then
        signUpUserInfo.id shouldBe command.id
        signUpUserInfo.name shouldBe command.name
        signUpUserInfo.password shouldBe command.password
        signUpUserInfo.level shouldBe User.Level.BASIC
        signUpUserInfo.logInCount shouldBe 0
    }

    @Test
    fun `아이디가 중복되면 가입에 실패한다`() {
        // given
        withClue("jinia91 아이디가 존재한다") {
            val command = validSignUpUserCommand
            sut.signUp(command)
        }
        val command = withClue("동일 아이디로 가입 준비한다") { validSignUpUserCommand }

        // when, then
        shouldThrow<AlreadyUserIdExist> {
            sut.signUp(command)
        }
    }

    @Test
    fun `이름이 11자 이상이면 가입에 실패한다`() {
        // given
        val invalidCommand = validSignUpUserCommand.copy(
            name = "12345678901"
        )

        // when, then
        shouldThrow<InvalidUserName> {
            sut.signUp(invalidCommand)
        }
    }

    @Test
    fun `이름이 1자 미만이면 가입에 실패한다`() {
        // given
        val invalidCommand = validSignUpUserCommand.copy(
            name = ""
        )

        // when, then
        shouldThrow<InvalidUserName> {
            sut.signUp(invalidCommand)
        }
    }

    /**
     * 비밀번호 검증 테스트 케이스
     *
     * - 너무 많은 경우의수를 모두 테스트하기보단 MC/DC를 적용하여 휴리스틱하게 N +1 테스트 케이스를 작성한다
     * - 조건은 총 5개이므로 2^5 = 32개의 테스트 케이스가 필요하지만 MC/DC를 적용하여 6개 + 경계값 테스트를 더해 7개 작성한다
     */
    @Nested
    inner class `비밀번호 검증 테스트 케이스` {
        @Test
        fun `비밀번호가 8자 미만이면 가입에 실패한다`() {
            // given
            val invalidCommand = validSignUpUserCommand.copy(
                password = "1Q2w3e!"
            )

            // when, then
            shouldThrow<InvalidPassword> {
                sut.signUp(invalidCommand)
            }
        }

        @Test
        fun `비밀번호가 16자 초과이면 가입에 실패한다`() {
            // given
            val invalidCommand = validSignUpUserCommand.copy(
                password = "1Q2w3e4r1!1q2w3e4r"
            )

            // when, then
            shouldThrow<InvalidPassword> {
                sut.signUp(invalidCommand)
            }
        }

        @Test
        fun `비밀번호가 소문자를 포함하지 않으면 가입에 실패한다`() {
            // given
            val invalidCommand = validSignUpUserCommand.copy(
                password = "QWERTYUI1!"
            )

            // when, then
            shouldThrow<InvalidPassword> {
                sut.signUp(invalidCommand)
            }
        }

        @Test
        fun `비밀번호가 대문자를 포함하지 않으면 가입에 실패한다`() {
            // given
            val invalidCommand = validSignUpUserCommand.copy(
                password = "qwertyui1!",
            )

            // when, then
            shouldThrow<InvalidPassword> {
                sut.signUp(invalidCommand)
            }
        }

        @Test
        fun `비밀번호가 숫자를 포함하지 않으면 가입에 실패한다`() {
            // given
            val invalidCommand = validSignUpUserCommand.copy(
                password = "QWERTYUI!"
            )

            // when, then
            shouldThrow<InvalidPassword> {
                sut.signUp(invalidCommand)
            }
        }

        @Test
        fun `비밀번호가 특수문자를 포함하지 않으면 가입에 실패한다`() {
            // given
            val invalidCommand = validSignUpUserCommand.copy(
                password = "QWEr1YUI1"
            )

            // when, then
            shouldThrow<InvalidPassword> {
                sut.signUp(invalidCommand)
            }
        }
    }

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }
}