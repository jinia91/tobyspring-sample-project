package kr.co.jinia91.spring.sample.user.application

import kr.co.jinia91.spring.sample.user.domain.AlreadyUserIdExist
import kr.co.jinia91.spring.sample.user.domain.EVENT_STATUS
import kr.co.jinia91.spring.sample.user.domain.Reminder
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradePolicy
import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

interface UserUserCases {
    fun signUp(command: SignUpUserCommand): SignUpUserInfo
    fun upgradeUserLevels(): UpgradeUserLevelsInfo
}

@Service
open class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userLevelUpgradePolicy: List<UserLevelUpgradePolicy>,
    private val reminder: Reminder
) : UserUserCases {
    override fun signUp(command: SignUpUserCommand): SignUpUserInfo {
        command.validate()
        val user = command.toNewUser()
        userRepository.save(user)
        return SignUpUserInfo(
            id = user.id,
            name = user.name,
            password = user.password,
            level = user.level
        )
    }

    private fun SignUpUserCommand.validate() {
        if (userRepository.findById(id) != null) {
            throw AlreadyUserIdExist()
        }
    }

    private fun SignUpUserCommand.toNewUser(): User {
        return User.newOne(
            id = id,
            name = name,
            password = password,
            email = email
        )
    }

    override fun upgradeUserLevels(): UpgradeUserLevelsInfo {
        val policy = userLevelUpgradePolicy.find {
            EVENT_STATUS == it.supportingEventStatus
        } ?: throw IllegalArgumentException("No policy found for $EVENT_STATUS")

        val targetUsers = userRepository.findAll()
            .sortedBy { it.id }
            .filter { policy.canUpgradeLevel(it) }

        targetUsers.forEach { upgradeUserLevel(it, policy) }
        return buildInfo(targetUsers)
    }

    protected fun upgradeUserLevel(
        user: User,
        policy: UserLevelUpgradePolicy,
    ) {
        user.tryUpgradeLevel(policy)
        userRepository.save(user)
        user.email?.let { reminder.sendTOUpgradedUser(it) }
    }

    private fun buildInfo(targetUsers: List<User>): UpgradeUserLevelsInfo {
        return UpgradeUserLevelsInfo(targetUsers.map { UpgradeUserLevelsDto(it.id, it.level) })
    }
}

