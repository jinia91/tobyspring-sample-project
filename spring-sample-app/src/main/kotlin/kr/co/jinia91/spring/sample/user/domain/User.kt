package kr.co.jinia91.spring.sample.user.domain

data class User constructor(
    val id: String,
    var name: String,
    var password: String,
    var level: Level,
    var logInCount: Int,
    var postCount: Int,
    var email: String?,
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
        require(password.matches(PASSWORD_REGEX)) { throw InvalidPassword() }
    }

    fun tryUpgradeLevel(policy: UserLevelUpgradePolicy) {
        val canUpgrade = policy.canUpgradeLevel(this)
        if (canUpgrade) {
            level = level.nextLevel()
        }
    }

    companion object {
        private val PASSWORD_REGEX = Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\da-zA-Z]).{8,16}\$")
        fun newOne(id: String, name: String, password: String, email: String?): User {
            return User(
                id = id,
                name = name,
                password = password,
                level = Level.BASIC,
                logInCount = 0,
                postCount = 0,
                email = email
            )
        }
    }
}

