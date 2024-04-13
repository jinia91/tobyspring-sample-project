package kr.co.jinia91.spring.sample.user

data class User constructor(
    val id: String,
    var name: String,
    var password: String,
    var level: Level,
    var logInCount: Int
) {
    init {
        validateInvariants()
    }

    private fun validateInvariants() {
        require(name.length in 1..10) { throw InvalidUserName() }
        require(password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,16}\$"))) { throw InvalidPassword() }
    }

    enum class Level {
        BASIC,
        SILVER,
        GOLD
    }

    companion object {
        fun newOne(id: String, name: String, password: String): User {
            return User(
                id = id,
                name = name,
                password = password,
                level = Level.BASIC,
                logInCount = 0
            )
        }
    }
}

