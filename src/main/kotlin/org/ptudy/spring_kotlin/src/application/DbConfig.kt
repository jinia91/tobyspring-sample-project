package org.ptudy.spring_kotlin.src.application

import org.ptudy.spring_kotlin.di_container.annotation.Bean
import org.ptudy.spring_kotlin.di_container.annotation.Configuration

@Configuration
class DbConfig {
    @Bean
    fun dbConnectionMaker(): ConnectionMaker {
        return DConnectionMaker()
    }
}