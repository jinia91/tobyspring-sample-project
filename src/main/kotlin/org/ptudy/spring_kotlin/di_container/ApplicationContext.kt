package org.ptudy.spring_kotlin.di_container

class ApplicationContext(
    classes : Set<Class<*>> = ClassPathScanner.getAllClassesInSrc("org.ptudy.spring_kotlin.src.application")
) : DiContainer(classes) {
    fun getBean(name: String) : Any? {
        return beans.find {
            it.javaClass.isAssignableFrom(Class.forName(name))
        }
    }
}