package kr.co.jinia91.sample

import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import kr.co.jinia91.sample.application.User
import kr.co.jinia91.sample.application.UserDao
import kr.co.jinia91.springframework.core.SpringBootApplication
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class IntegrationDaoTests {

    private val sut = context.getBean(UserDao::class.java.name) as UserDao
    @BeforeEach
    fun setUp() {
        sut.deleteAll()
    }

    @Test
    fun `유저를 추가하면 정상 저장된다`() {
        sut.add(
            User(
                id = "1111",
                name = "jinia",
                password = "1234"
            )
        )

        val findUser = sut.get("1111")

        findUser.shouldNotBeNull()
        findUser.name shouldBe "jinia"
        findUser.password shouldBe "1234"
    }

    companion object {
        private var context = SpringBootApplication.run()
    }
}