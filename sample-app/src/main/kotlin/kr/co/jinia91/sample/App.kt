package kr.co.jinia91.sample

import org.ptudy.spring_kotlin.di_container.MyApplicationContext
import kr.co.jinia91.sample.application.User
import kr.co.jinia91.sample.application.UserDao

fun main() {
    val context : MyApplicationContext = MyApplicationContext()

    // for test
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