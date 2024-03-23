package kr.co.jinia91.springframework.core.utils

import kr.co.jinia91.springframework.core.annotation.SpringApplication
import org.reflections.Reflections
import org.reflections.scanners.Scanners
import org.reflections.util.ClasspathHelper
import org.reflections.util.ConfigurationBuilder

private val log = mu.KotlinLogging.logger {}

object ClassPathScanner {
    fun getAllClassesInSrc(packageName: String): Set<Class<*>> {
        log.debug { "scan package = $packageName" }
        val scanners = Scanners.SubTypes.filterResultsBy { true }
        val reflections = Reflections(packageName, scanners)
        return reflections.getSubTypesOf(Any::class.java)
    }
    fun getAllClassesInSrc(packages: Array<String>) = packages.map {
        ClassPathScanner.getAllClassesInSrc(it)
    }.flatten().toSet()

    fun findSpringApplication(): Class<*>? {
        val reflections = Reflections(
            ConfigurationBuilder()
                .setUrls(ClasspathHelper.forJavaClassPath())
        )
        val annotated = reflections.getTypesAnnotatedWith(SpringApplication::class.java)
        check(annotated.size <= 1) { "SpringApplication annotation should be only one" }
        return annotated.firstOrNull()
    }
}