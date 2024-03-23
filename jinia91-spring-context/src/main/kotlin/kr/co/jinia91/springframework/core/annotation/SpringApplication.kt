package kr.co.jinia91.springframework.core.annotation

@Target(AnnotationTarget.CLASS)
annotation class SpringApplication(val packages: Array<String> = [])