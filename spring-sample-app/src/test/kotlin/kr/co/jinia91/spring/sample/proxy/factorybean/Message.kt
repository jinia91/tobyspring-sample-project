package kr.co.jinia91.spring.sample.proxy.factorybean

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service

@Repository
class Data {
    override fun toString(): String {
        return "Data"
    }
}

@Repository
class Message @Autowired private constructor(
    private val message: Data
) {
    companion object {
        fun createMessage(data :Data): Message {
            return Message(data)
        }
    }

    fun getMessage(): String {
        return message.toString()
    }
}

//@Component
class MessageFactoryBean  @Autowired constructor(
    private val data: Data
) : FactoryBean<Message> {
    override fun getObject(): Message {
        return Message.createMessage(data)
    }

    override fun getObjectType(): Class<*> {
        return Message::class.java
    }

    override fun isSingleton(): Boolean {
        return false
    }
}

@SpringBootTest
class Test {
    @Autowired
    lateinit var message: Message

    @Test
    fun test() {
        println(message.getMessage())
    }
}