package co.kr.jinia91.springframework.core

class MyApplicationContext(
    classes : Set<Class<*>> = ClassPathScanner.getAllClassesInSrc("org.ptudy.spring_kotlin.src.application")
) : AbstractApplicationContext(classes) {
    override fun getBean(name: String) : Any? {
        return beans.find {
            it.javaClass.isAssignableFrom(Class.forName(name))
        }
    }
}