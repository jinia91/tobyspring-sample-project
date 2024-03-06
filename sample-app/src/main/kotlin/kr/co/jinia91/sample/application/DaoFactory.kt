package kr.co.jinia91.sample.application

import co.kr.jinia91.springframework.core.annotation.Bean
import co.kr.jinia91.springframework.core.annotation.Configuration

@Configuration
class DaoFactory {
    @Bean
    fun dbConnectionMaker(): ConnectionMaker {
        return DConnectionMaker()
    }
}