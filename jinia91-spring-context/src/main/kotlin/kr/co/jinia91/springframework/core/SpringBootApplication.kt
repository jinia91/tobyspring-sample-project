package kr.co.jinia91.springframework.core

import kr.co.jinia91.springframework.core.annotation.SpringApplication
import kr.co.jinia91.springframework.core.utils.ClassPathScanner

private val log = mu.KotlinLogging.logger {}

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
        return SimpleApplicationContext(ClassPathScanner.getAllClassesInSrc(springApplication.packageName)).also {
            log.info {
                """

###############################################################################                    
#    ___  _         _         _        _____               _                  #
#   |_  |(_)       (_)       ( )      /  ___|             (_)                 #
#     | | _  _ __   _   __ _ |/  ___  \ `--.  _ __   _ __  _  _ __    __ _    #
#     | || || '_ \ | | / _` |   / __|  `--. \| '_ \ | '__|| || '_ \  / _` |   #
# /\__/ /| || | | || || (_| |   \__ \ /\__/ /| |_) || |   | || | | || (_| |   #
# \____/ |_||_| |_||_| \__,_|   |___/ \____/ | .__/ |_|   |_||_| |_| \__, |   #
#                                            | |                      __/ |   #
#                                            |_|                     |___/    #
###############################################################################                                           
                                                               
                """.trimIndent()
            }
        }
    }
}