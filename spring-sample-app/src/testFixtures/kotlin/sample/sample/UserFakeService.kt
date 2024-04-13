package sample.sample

import javax.sql.DataSource
import kr.co.jinia91.spring.sample.user.application.UserServiceImpl
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradePolicy
import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.springframework.transaction.PlatformTransactionManager

class UserFakeService(
    userRepository: UserRepository,
    userLevelUpgradePolicy: List<UserLevelUpgradePolicy>,
): UserServiceImpl(userRepository, userLevelUpgradePolicy) {
    private lateinit var exId: String

    fun setExId(id: String) {
        exId = id
    }

    override fun upgradeUserLevel(it: User, policy: UserLevelUpgradePolicy) {
        if (it.id == exId) {
            throw IllegalArgumentException("transaction위한 강제 예외 처리")
        }
        super.upgradeUserLevel(it, policy)
    }
}