package kr.co.jinia91.spring.sample.proxy.dynamic

import java.lang.reflect.Proxy
import kr.co.jinia91.spring.sample.proxy.Hello
import kr.co.jinia91.spring.sample.proxy.HelloImpl
import org.junit.jupiter.api.Test

class LoggingDecoratorDynamicProxyTests {
    @Test
    fun `로깅 테스트`() {
        val proxyLoggingHello = Proxy.newProxyInstance(
            Hello::class.java.classLoader,
            arrayOf(Hello::class.java),
            LoggingDecorator(HelloImpl())
        ) as Hello

        proxyLoggingHello.hello("jinia91")
    }
}