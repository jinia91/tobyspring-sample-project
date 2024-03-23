package kr.co.jinia91.sample

import com.navercorp.fixturemonkey.FixtureMonkey
import com.navercorp.fixturemonkey.jakarta.validation.plugin.JakartaValidationPlugin
import com.navercorp.fixturemonkey.kotlin.KotlinPlugin
import com.navercorp.fixturemonkey.kotlin.giveMeOne
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kr.co.jinia91.sample.application.User
import kr.co.jinia91.sample.application.UserDao
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.RepeatedTest
import kotlin.test.Test

private val log = mu.KotlinLogging.logger {}

class IntegrationDaoTests {

    private val sut = context.getBean(UserDao::class.java.name) as UserDao

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
            password = "123456"
        )

        // when
        val foundUser = sut.addAndGet(user)

        // then - 상태검증
        foundUser.shouldNotBeNull()
        foundUser.name shouldBe "jinia"
        foundUser.password shouldBe "123456"

        val userCount = sut.getCount()
        userCount shouldBe 1
    }

    @RepeatedTest(10)
    fun `유저 여러명을 추가하면 정상 저장된다 - fixtures 사용`() {
        // given
        val monkey = FixtureMonkey.builder()
            .plugin(KotlinPlugin())
            .plugin(JakartaValidationPlugin())
            .build()

        var user1: User
        var user2: User

        try {
            user1 = monkey.giveMeOne<User>()
            user2 = monkey.giveMeOne<User>()
        } catch (e: Exception) {
            log.error { e }
            throw e
        }


        // when
        sut.add(user1)
        sut.add(user2)

        // then - 상태검증
        val userCount = sut.getCount()
        userCount shouldBe 2
    }

    @Test
    fun `없는 유저를 조회하면 예외가 발생한다`() {
        // when, then
        shouldThrow<IllegalArgumentException> {
            sut.get("1111")
        }
    }
}