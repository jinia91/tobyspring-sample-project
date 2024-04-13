package sample.sample

import kr.co.jinia91.spring.sample.user.SignUpUserCommand
import kr.co.jinia91.spring.sample.user.User

val validSignUpUserCommand = SignUpUserCommand(
    id = "jinia91",
    name = "jinia",
    password = "1Q2w3e4r1!",
)

val validUser = User.newOne(
    id = "jinia91",
    name = "jinia",
    password = "1Q2w3e4r1!",
)