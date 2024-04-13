package kr.co.jinia91.spring.sample.user

data class User constructor(
    val id: String,
    var name: String,
    var password: String,
    var level: Level,
) {
    enum class Level {
        BASIC,
        SILVER,
        GOLD
    }

    companion object {
        fun newOne(id: String, name: String, password: String): User {
            require(name.length in 1..10) { throw InvalidUserName() }
            return User(
                id = id,
                name = name,
                password = password,
                level = Level.BASIC
            )
        }
    }
}

