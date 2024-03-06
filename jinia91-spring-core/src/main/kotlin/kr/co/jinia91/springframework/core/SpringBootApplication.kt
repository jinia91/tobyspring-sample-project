package kr.co.jinia91.springframework.core

import kr.co.jinia91.springframework.core.annotation.SpringApplication
import kr.co.jinia91.springframework.core.utils.ClassPathScanner

object SpringBootApplication {
    fun run() : AbstractApplicationContext {
        val springApplication = ClassPathScanner.findSpringApplication()
            ?: throw IllegalStateException("No SpringApplication class found")

        val springApplicationAnnotation = springApplication.annotations.find { it.annotationClass == SpringApplication::class }
            as SpringApplication

        val packages = springApplicationAnnotation.packages

        if(packages.isNotEmpty()){
            return SimpleApplicationContext(ClassPathScanner.getAllClassesInSrc(packages))
        }
        return SimpleApplicationContext(ClassPathScanner.getAllClassesInSrc(springApplication.packageName))
    }
}