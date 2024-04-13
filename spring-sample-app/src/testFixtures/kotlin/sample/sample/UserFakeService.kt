package sample.sample

import kr.co.jinia91.spring.sample.user.application.UserServiceImpl
import kr.co.jinia91.spring.sample.user.domain.Reminder
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradePolicy
import kr.co.jinia91.spring.sample.user.domain.UserRepository

class UserFakeService(
    userRepository: UserRepository,
    userLevelUpgradePolicy: List<UserLevelUpgradePolicy>,
    reminder: Reminder
): UserServiceImpl(userRepository, userLevelUpgradePolicy, reminder) {
    private lateinit var exId: String

    fun setExId(id: String) {
        exId = id
    }

    override fun upgradeUserLevel(user: User, policy: UserLevelUpgradePolicy) {
        if (user.id == exId) {
            throw IllegalArgumentException("transaction위한 강제 예외 처리")
        }
        super.upgradeUserLevel(user, policy)
    }
}