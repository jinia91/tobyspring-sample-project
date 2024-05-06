package kr.co.jinia91.spring.sample.proxy.dynamic

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method

class LoggingDecorator(
    private val target: Any
) : InvocationHandler {
    override fun invoke(proxy: Any?, method: Method?, args: Array<out Any>?): Any? {
        println("method: ${method?.name}, args: ${args?.joinToString()}")
        return method?.invoke(target, *args.orEmpty())
    }
}
