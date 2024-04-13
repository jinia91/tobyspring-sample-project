package kr.co.jinia91.spring.sample.user.persistance

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.adapters.persistance.UserDao
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import sample.sample.validUser

@SpringBootTest
@Transactional
class UserDaoTests {
    @Autowired
    private lateinit var sut: UserDao

    @Test
    fun `테스트 실행시 유저는 없어야 한다`() {
        // when
        val userCount = sut.getCount()
        // then
        userCount shouldBe 0
    }

    @Test
    fun `유저를 추가하면 정상 저장된다`() {
        val allUsers = sut.getAll()
        println(allUsers)

        // given
        val user = validUser()

        // when
        val foundUser = sut.addAndGet(user)

        // then - 상태검증
        foundUser.shouldNotBeNull()
        foundUser.id shouldBe user.id
        foundUser.name shouldBe user.name
        foundUser.logInCount shouldBe 0
        foundUser.level shouldBe User.Level.BASIC

        val userCount = sut.getCount()
        userCount shouldBe 1
    }

    @Test
    fun `기존 유저를 업데이트하면 정상적으로 업데이트 된다`() {
        // given
        val user = validUser()
        sut.addAndGet(user)

        val updatedUser = user.copy(
            name = "changed",
            password = "1Q2w3e4r1!",
            level = User.Level.SILVER,
            logInCount = User.UserLevelUpgradePolicy.MIN_LOG_COUNT_FOR_SILVER + 10
        )
        // when
        sut.insertOrUpdate(updatedUser)

        // then
        val foundUser = sut.get(validUser().id)
        foundUser.shouldNotBeNull()
        foundUser.name shouldBe updatedUser.name
        foundUser.password shouldBe updatedUser.password
        foundUser.level shouldBe updatedUser.level
        foundUser.logInCount shouldBe updatedUser.logInCount
    }


    @Test
    fun `없는 유저를 조회하면 null이 된다`() {
        // when
        val optionalUser = sut.get("1111")

        // then
        optionalUser shouldBe null
    }
}