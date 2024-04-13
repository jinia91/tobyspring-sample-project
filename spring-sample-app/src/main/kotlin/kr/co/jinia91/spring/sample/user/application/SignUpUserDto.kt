package kr.co.jinia91.spring.sample.user.application

import kr.co.jinia91.spring.sample.user.domain.User

data class SignUpUserCommand(
    val id: String,
    val name: String,
    val password: String,
)

data class SignUpUserInfo(
    val id: String,
    val name: String,
    val password: String,
    val level: User.Level,
    val logInCount : Int = 0,
)
