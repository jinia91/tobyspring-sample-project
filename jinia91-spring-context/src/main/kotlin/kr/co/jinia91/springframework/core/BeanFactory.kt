package kr.co.jinia91.springframework.core

interface BeanFactory {
    fun getBean(name: String) : Any?
    fun getAllBeans() : Map<String, Any>
}