package co.kr.jinia91.springframework.core

interface BeanFactory {
    fun getBean(name: String) : Any?
}