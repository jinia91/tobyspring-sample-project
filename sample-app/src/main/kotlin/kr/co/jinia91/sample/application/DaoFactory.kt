package kr.co.jinia91.sample.application

import kr.co.jinia91.springframework.core.annotation.Bean
import kr.co.jinia91.springframework.core.annotation.Configuration

@Configuration
class DaoFactory {
    @Bean
    fun dbConnectionMaker(): ConnectionMaker {
        return DConnectionMaker()
    }
}