package kr.co.jinia91.sample.application

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.BeforeEach
import kotlin.test.Test

class UserDaoTests {
    private val userDao = UserDao().apply {
        connectionMaker = DConnectionMaker()
    }

    @BeforeEach
    fun setUp() {
        userDao.deleteAll()
    }

    @Test
    fun `테스트 실행시 유저는 없어야 한다`() {
        // when
        val userCount = userDao.getCount()
        // then
        userCount shouldBe 0
    }
}