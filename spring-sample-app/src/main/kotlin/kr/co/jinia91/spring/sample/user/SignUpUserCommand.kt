package kr.co.jinia91.spring.sample.user

data class SignUpUserCommand(
    val id: String,
    val name: String,
    val password: String,
)
