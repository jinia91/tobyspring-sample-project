package kr.co.jinia91.spring.sample.user.application.infra

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import org.springframework.transaction.PlatformTransactionManager
import org.springframework.transaction.support.DefaultTransactionDefinition

class TransactionProxy(
    private val target: Any,
    private val transactionManager: PlatformTransactionManager,
    private val pattern : String
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any? {
        return if (method.name.startsWith(pattern)) {
            invokeInTransaction(method, args)
        } else {
            method.invoke(target, *args.orEmpty())
        }
    }

    private fun invokeInTransaction(method: Method, args: Array<out Any>?): Any? {
        val status = transactionManager.getTransaction(DefaultTransactionDefinition())
        try {
            val result = method.invoke(target, *args.orEmpty())
            transactionManager.commit(status)
            return result
        } catch (e: Exception) {
            transactionManager.rollback(status)
            throw e
        }
    }
}