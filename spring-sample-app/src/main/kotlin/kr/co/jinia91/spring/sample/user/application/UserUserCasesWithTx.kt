package kr.co.jinia91.spring.sample.user.application

import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

@Service
@Primary
class UserServiceTxImpl(
    private val userUserCases: UserServiceImpl,
    private val transactionManager: PlatformTransactionManager
) : UserUserCases {
    override fun signUp(command: SignUpUserCommand): SignUpUserInfo {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
        try {
            val result = userUserCases.signUp(command)
            transactionManager.commit(status)
            return result
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }

    override fun upgradeUserLevels(): UpgradeUserLevelsInfo {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
        try {
            val result = userUserCases.upgradeUserLevels()
            transactionManager.commit(status)
            return result
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }
}

