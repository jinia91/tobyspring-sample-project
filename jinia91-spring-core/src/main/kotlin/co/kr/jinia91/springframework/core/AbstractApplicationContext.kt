package co.kr.jinia91.springframework.core

import co.kr.jinia91.springframework.core.annotation.Autowired
import co.kr.jinia91.springframework.core.annotation.Bean
import co.kr.jinia91.springframework.core.annotation.Component
import co.kr.jinia91.springframework.core.annotation.Configuration
import java.lang.reflect.Constructor
import java.lang.reflect.Field


abstract class AbstractApplicationContext(
    classes: Set<Class<*>>,
) : BeanFactory {
    companion object {
        private val BEAN_ANNOTATIONS: Set<Class<out Annotation>> = setOf(Component::class.java)
        private val CONFIGURATION_ANNOTATIONS: Set<Class<out Annotation>> = setOf(Configuration::class.java)
        private val BEAN_BUILDER: Set<Class<out Annotation>> = setOf(Bean::class.java)
    }

    protected val beans: Set<Any>

    init {
        val primaryBeans = instantiateBeansFromConfiguration(classes)
        val secondaryBeans = instantiateBeans(classes)
        val beans = primaryBeans + secondaryBeans
        dependencyInject(beans)
        this.beans = beans
    }

    private fun instantiateBeansFromConfiguration(classes: Set<Class<*>>): Set<Any> {
        val configurationClasses = classes.filter { it.isAnnotationPresent(Configuration::class.java) }
        val beans = configurationClasses.flatMap { it.declaredMethods.toList() }
            .filter { it.isAnnotationPresent(Bean::class.java) }
            .map { it.invoke(instantiateClass(it.declaringClass)) }
        return beans.toSet()
    }

    private fun instantiateBeans(classes: Set<Class<*>>): Set<Any> {
        return classes.filter { isTargetOfBean(it) }
            .map { instantiateClass(it) }
            .toSet()
    }

    private fun isTargetOfBean(clazz: Class<*>): Boolean = BEAN_ANNOTATIONS.any(clazz::isAnnotationPresent)

    private fun instantiateClass(clazz: Class<*>): Any {
        val constructor: Constructor<*> = clazz.getDeclaredConstructor()
        constructor.isAccessible = true
        val instance: Any = constructor.newInstance()
        constructor.isAccessible = false
        return instance
    }

    private fun dependencyInject(beans: Set<Any>) {
        for (bean in beans) {
            val fieldsToNeedInjection = findFieldsToNeedAutowiring(bean)
            for (field in fieldsToNeedInjection) {
                val fieldValueBean = findAutoWiringBean(beans, field.type)
                setField(bean = bean, field = field, fieldValue = fieldValueBean)
            }
        }
    }

    private fun findFieldsToNeedAutowiring(bean: Any): Set<Field> {
        return bean.javaClass.declaredFields
            .filter { it.isAnnotationPresent(Autowired::class.java) }
            .toSet()
    }

    private fun findAutoWiringBean(beans: Set<Any>, type: Class<*>): Any {
        val candidateBean = beans.filter { type.isAssignableFrom(it.javaClass) }
        if (candidateBean.size > 1) throw java.lang.IllegalArgumentException("$type 후보가 너무 많습니다")
        if (candidateBean.isEmpty()) throw java.lang.IllegalArgumentException("$type 후보가 없습니다")
        return candidateBean.first()
    }

    private fun setField(bean: Any, field: Field, fieldValue: Any) {
        try {
            field.isAccessible = true
            field[bean] = fieldValue
            field.isAccessible = false
        } catch (e: IllegalAccessException) {
            throw IllegalArgumentException("필드를 설정할 수 없습니다.")
        }
    }

}