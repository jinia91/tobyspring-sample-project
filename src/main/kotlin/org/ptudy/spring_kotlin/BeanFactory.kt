package org.ptudy.spring_kotlin

interface BeanFactory {
    fun getBean(name: String) : Any?
}