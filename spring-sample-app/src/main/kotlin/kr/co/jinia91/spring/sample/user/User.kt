package kr.co.jinia91.spring.sample.user

data class User constructor(
    val id: String,
    var name: String,
    var password: String,
    var level: Level,
    var logInCount: Int
) {
    enum class Level {
        BASIC,
        SILVER,
        GOLD
        ;

        fun nextLevel(): Level {
            return when (this) {
                BASIC -> SILVER
                SILVER -> GOLD
                GOLD -> GOLD // 최고레벨은 자신을 반환
            }
        }
    }

    init {
        validateInvariants()
    }

    private fun validateInvariants() {
        require(name.length in 1..10) { throw InvalidUserName() }
        require(password.matches(Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,16}\$"))) { throw InvalidPassword() }
    }

    fun tryUpgradeLevel() {
        val canUpgrade = UserLevelUpgradePolicy.canUpgradeLevel(this)
        if (canUpgrade) {
            level = level.nextLevel()
        }
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
    object UserLevelUpgradePolicy {
        fun canUpgradeLevel(user: User): Boolean {
            return when (user.level) {
                Level.BASIC -> user.logInCount >= 50
                Level.SILVER -> false
                Level.GOLD -> false
            }
        }
    }
}

