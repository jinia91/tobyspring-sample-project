package kr.co.jinia91.spring.sample.user.application

import javax.sql.DataSource
import kr.co.jinia91.spring.sample.user.domain.AlreadyUserIdExist
import kr.co.jinia91.spring.sample.user.domain.EVENT_STATUS
import kr.co.jinia91.spring.sample.user.domain.User
import kr.co.jinia91.spring.sample.user.domain.UserLevelUpgradePolicy
import kr.co.jinia91.spring.sample.user.domain.UserRepository
import org.springframework.jdbc.datasource.DataSourceUtils
import org.springframework.stereotype.Service
import org.springframework.transaction.support.TransactionSynchronizationManager

interface UserUserCases {
    fun signUp(command: SignUpUserCommand): SignUpUserInfo
    fun upgradeUserLevels(): UpgradeUserLevelsInfo
}

@Service
open class UserServiceImpl(
    private val userRepository: UserRepository,
    private val userLevelUpgradePolicy: List<UserLevelUpgradePolicy>,
    private val dataSource: DataSource,
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
            password = password
        )
    }

    override fun upgradeUserLevels(): UpgradeUserLevelsInfo {
        val policy = userLevelUpgradePolicy.find {
            EVENT_STATUS == it.supportingEventStatus
        } ?: throw IllegalArgumentException("No policy found for $EVENT_STATUS")

        val targetUsers = userRepository.findAll()
            .sortedBy { it.id }
            .filter { policy.canUpgradeLevel(it) }

        TransactionSynchronizationManager.initSynchronization()
        DataSourceUtils.getConnection(dataSource).use { connection ->
            connection.autoCommit = false
            try {
                targetUsers.forEach { upgradeUserLevel(it, policy) }
            } catch (e: Exception) {
                throw e
            } finally {
                TransactionSynchronizationManager.unbindResource(dataSource)
                TransactionSynchronizationManager.clearSynchronization()
            }

        }
        return buildInfo(targetUsers)
    }

    protected fun upgradeUserLevel(
        it: User,
        policy: UserLevelUpgradePolicy,
    ) {
        it.tryUpgradeLevel(policy)
        userRepository.save(it)
    }

    private fun buildInfo(targetUsers: List<User>): UpgradeUserLevelsInfo {
        return UpgradeUserLevelsInfo(targetUsers.map { UpgradeUserLevelsDto(it.id, it.level) })
    }
}

