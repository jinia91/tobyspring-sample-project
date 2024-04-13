package kr.co.jinia91.spring.sample.user

data class User(
    val id: String,
    var name: String,
    var password: String,
    var level: Level
){ enum class Level{
        BASIC,
        SILVER,
        GOLD
    }
}

