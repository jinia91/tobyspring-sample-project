package org.ptudy.spring_kotlin.src

import org.ptudy.spring_kotlin.di_container.ApplicationContext
import org.ptudy.spring_kotlin.src.application.User
import org.ptudy.spring_kotlin.src.application.UserDao

fun main() {
    val context = ApplicationContext()
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