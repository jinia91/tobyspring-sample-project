package kr.co.jinia91.springframework.core

class SimpleApplicationContext(
    classes: Set<Class<*>>
) : AbstractApplicationContext(classes) {
    override fun getBean(name: String): Any? {
        return beans.find {
            it.javaClass.isAssignableFrom(Class.forName(name))
        }
    }

    override fun getAllBeans(): Map<String, Any> {
        return beans.associateBy {
            it.javaClass.name
        }
    }
}