package kr.co.jinia91.spring.sample.user

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.withClue
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserServiceTests {
    @Autowired
    private lateinit var sut: UserService

    @Autowired
    private lateinit var userRepository: UserRepository

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
    @Nested
    inner class `사용자 가입 유즈케이스 테스트` {
        @Test
        fun `유효한 사용자 정보가 주어지면 사용자는 정상적으로 가입되고 BASIC 레벨이다`() {
            // given
            val command = withClue("유효한 사용자 정보") {
                SignUpUserCommand(
                    id = "jinia91",
                    name = "jinia",
                    password = "1q2w3e4r1!",
                )
            }

            // when
            val signUpUserInfo = sut.signUp(command)

            // then
            signUpUserInfo.id shouldBe command.id
            signUpUserInfo.name shouldBe command.name
            signUpUserInfo.password shouldBe command.password
            signUpUserInfo.level shouldBe User.Level.BASIC
        }

        @Test
        fun `아이디가 중복되면 가입에 실패한다`() {
            // given
            withClue("jinia91 아이디가 존재한다") {
                val command = SignUpUserCommand(
                    id = "jinia91",
                    name = "jinia",
                    password = "1q2w3e4r1!",
                )
                sut.signUp(command)
            }
            val command = withClue("동일 아이디로 가입 준비한다") {
                SignUpUserCommand(
                    id = "jinia91",
                    name = "jinia",
                    password = "1q2w3e4r1!",
                )
            }

            // when, then
            shouldThrow<AlreadyUserIdExist> {
                sut.signUp(command)
            }
        }

        @Test
        fun `이름이 11자 이상이면 가입에 실패한다`() {
            // given
            val invalidCommand = SignUpUserCommand(
                id = "jinia91",
                name = "12345678901",
                password = "1q2w3e4r1!",
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
            }

            @Test
            fun `비밀번호가 16자 초과이면 가입에 실패한다`() {
            }

            @Test
            fun `비밀번호가 소문자를 포함하지 않으면 가입에 실패한다`() {
            }

            @Test
            fun `비밀번호가 대문자를 포함하지 않으면 가입에 실패한다`() {
            }

            @Test
            fun `비밀번호가 숫자를 포함하지 않으면 가입에 실패한다`() {
            }

            @Test
            fun `비밀번호가 특수문자를 포함하지 않으면 가입에 실패한다`() {
            }

            @Test
            fun `비밀번호가 소문자, 대문자, 숫자, 특수문자를 모두 포함하면 가입에 성공한다`() {
            }
        }
    }

    /**
     * 사용자 레벨 관리 유즈케이스
     *
     * - 사용자는 유저 레벨이 존재하며 BASIC, SILVER, GOLD 세가지 있다
     * - 가입 후 50회 이상 로그인을 하면 BASIC에서 SILVER로 레벨이 업그레이드 된다
     * - SILVER 레벨에서 30회 이상 게시글을 작성하면 GOLD로 레벨이 업그레이드 된다
     * - 사용자 레벨의 변경작업은 일정한 주기를 가지고 일괄적으로 진행된다. 변경 작업 전에는 조건을 충족하더라도 레벨의 변경이 일어나지않는다.(배치작업)
     *   (유즈케이스의 관심사가 아님, 어댑터 레이어에서 제어하면 된다)
     */
    @Nested
    inner class `사용자 레벨 관리 유즈케이스` {
        @Test
        fun `BASIC 사용자는 50회 이상 로그인을 하면 SILVER 레벨로 업그레이드 된다`() {
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
    }

    @BeforeEach
    fun setUp() {
        userRepository.deleteAll()
    }
}