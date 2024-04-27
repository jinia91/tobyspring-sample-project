package kr.co.jinia91.spring.sample.proxy

import io.kotest.matchers.shouldBe
import java.util.*
import org.junit.jupiter.api.Test

class ReflectionTests {
    @Test
    fun invokeMethod() {
        val name = "jinia91"
        val clazz = name.javaClass
        val method = clazz.getMethod("toUpperCase")
        val result = method.invoke(name)
        result shouldBe "JINIA91"
    }

    @Test
    fun invokeMethodWithParameter() {
        val hello = HelloImpl()
        val clazz = hello.javaClass
        val method = clazz.getMethod("hello", String::class.java)
        val result = method.invoke(hello, "jinia91")
        result shouldBe "Hello, jinia91"
    }

    @Test
    fun invokeMethodWithProxy() {
        val hello = HelloProxy(HelloImpl())
        val clazz = hello.javaClass
        val method = clazz.getMethod("hello", String::class.java)
        val result = method.invoke(hello, "jinia91")
        result shouldBe "HELLO, JINIA91"
    }
}

interface Hello {
    fun hello(name: String): String
}

class HelloImpl : Hello {
    override fun hello(name: String): String {
        return "Hello, $name"
    }
}

class HelloProxy(
    private val hello: Hello
) : Hello {
    override fun hello(name: String): String {
        return hello.hello(name).uppercase(Locale.getDefault())
    }
}