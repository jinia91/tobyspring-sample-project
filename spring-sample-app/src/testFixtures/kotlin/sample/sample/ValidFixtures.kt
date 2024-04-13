package sample.sample

import kr.co.jinia91.spring.sample.user.application.SignUpUserCommand
import kr.co.jinia91.spring.sample.user.domain.User

fun validSignUpUserCommand() = SignUpUserCommand(
    id = "jinia91",
    name = "jinia",
    password = "1Q2w3e4r1!",
    email = null
)

fun validUser() = User.newOne(
    id = "jinia91",
    name = "jinia",
    password = "1Q2w3e4r1!",
    email = null
)