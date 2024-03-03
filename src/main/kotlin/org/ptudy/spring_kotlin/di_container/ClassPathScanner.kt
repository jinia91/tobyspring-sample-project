package org.ptudy.spring_kotlin.di_container

import org.reflections.Reflections
import org.reflections.scanners.Scanners


object ClassPathScanner {
    fun getAllClassesInSrc(packageName : String): Set<Class<*>> {
        val scanners = Scanners.SubTypes.filterResultsBy{true}
        val reflections = Reflections(packageName, scanners)
        return reflections.getSubTypesOf(Any::class.java)
    }
}