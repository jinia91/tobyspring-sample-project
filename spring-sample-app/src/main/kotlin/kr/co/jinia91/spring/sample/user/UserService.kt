package kr.co.jinia91.spring.sample.user

import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {
    fun signUp(command: SignUpUserCommand): SignUpUserInfo {
        val user = command.toNewUser()
        userRepository.save(user)
        return SignUpUserInfo(
            id = user.id,
            name = user.name,
            password = user.password,
            level = user.level
        )
    }
    private fun SignUpUserCommand.toNewUser(): User {
        return User(
            id = id,
            name = name,
            password = password,
            level = User.Level.BASIC
        )
    }
}

