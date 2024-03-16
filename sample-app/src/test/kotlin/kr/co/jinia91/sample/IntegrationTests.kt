package kr.co.jinia91.sample

import kr.co.jinia91.sample.application.User
import kr.co.jinia91.sample.application.UserDao
import kr.co.jinia91.springframework.core.SpringBootApplication
import kotlin.test.Test

class IntegrationTests {
    @Test
    fun `test run`() {
        val context = SpringBootApplication.run()
        val userDao = context.getBean(UserDao::class.java.name) as UserDao
        val user = userDao.add(
            User(
                id = "1111",
                name = "jinia",
                password = "1234"
            )
        )
        println("user = $user 등록 성공")

        val findUser = userDao.get("1111")
        println("findUser = $findUser")
    }
}