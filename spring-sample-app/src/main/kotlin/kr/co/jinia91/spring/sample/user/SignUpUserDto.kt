package kr.co.jinia91.spring.sample.user

data class SignUpUserCommand(
    val id: String,
    val name: String,
    val password: String,
)

data class SignUpUserInfo(
    val id: String,
    val name: String,
    val password: String,
    val level: User.Level
)
