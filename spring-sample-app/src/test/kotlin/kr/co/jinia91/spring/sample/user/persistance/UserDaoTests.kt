package kr.co.jinia91.spring.sample.user.persistance

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kr.co.jinia91.spring.sample.user.User
import kr.co.jinia91.spring.sample.user.adapters.persistance.UserDao
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserDaoTests {
    @Autowired
    private lateinit var sut: UserDao

    @BeforeEach
    fun setUp() {
        sut.deleteAll()
    }

    @Test
    fun `테스트 실행시 유저는 없어야 한다`() {
        // when
        val userCount = sut.getCount()
        // then
        userCount shouldBe 0
    }

    @Test
    fun `유저를 추가하면 정상 저장된다`() {
        // given
        val user = User(
            id = "1111",
            name = "jinia",
            password = "123456",
            level = User.Level.BASIC,
            logInCount = 0
        )

        // when
        val foundUser = sut.addAndGet(user)

        // then - 상태검증
        foundUser.shouldNotBeNull()
        foundUser.name shouldBe "jinia"
        foundUser.password shouldBe "123456"
        foundUser.level shouldBe User.Level.BASIC
        foundUser.logInCount shouldBe 0

        val userCount = sut.getCount()
        userCount shouldBe 1
    }


    @Test
    fun `없는 유저를 조회하면 null이 된다`() {
        // when
        val optionalUser = sut.get("1111")

        // then
        optionalUser shouldBe null
    }
}