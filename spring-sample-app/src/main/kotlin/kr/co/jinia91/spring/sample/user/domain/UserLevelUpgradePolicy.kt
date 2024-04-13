package kr.co.jinia91.spring.sample.user.domain

import org.springframework.stereotype.Component

interface UserLevelUpgradePolicy {
    val supportingEventStatus: EventStatus
    fun canUpgradeLevel(user: User): Boolean
}

var EVENT_STATUS = EventStatus.DEFAULT

enum class EventStatus {
    DEFAULT, EVENT
}

@Component
object UserLevelUpgradeDefaultPolicy : UserLevelUpgradePolicy {
    const val MIN_LOG_COUNT_FOR_SILVER = 50
    const val MIN_POST_COUNT_FOR_GOLD = 30

    override val supportingEventStatus: EventStatus = EventStatus.DEFAULT

    override fun canUpgradeLevel(user: User): Boolean {
        return when (user.level) {
            User.Level.BASIC -> user.logInCount >= MIN_LOG_COUNT_FOR_SILVER
            User.Level.SILVER -> user.postCount >= MIN_POST_COUNT_FOR_GOLD
            User.Level.GOLD -> false
        }
    }
}

@Component
object UserLevelUpgradeEventPolicy : UserLevelUpgradePolicy {
    const val MIN_LOG_COUNT_FOR_SILVER = 5
    const val MIN_EVENT_COUNT_FOR_GOLD = 3

    override val supportingEventStatus: EventStatus = EventStatus.EVENT

    override fun canUpgradeLevel(user: User): Boolean {
        return when (user.level) {
            User.Level.BASIC -> user.logInCount >= MIN_LOG_COUNT_FOR_SILVER
            User.Level.SILVER -> user.postCount >= MIN_EVENT_COUNT_FOR_GOLD
            User.Level.GOLD -> false
        }
    }
}