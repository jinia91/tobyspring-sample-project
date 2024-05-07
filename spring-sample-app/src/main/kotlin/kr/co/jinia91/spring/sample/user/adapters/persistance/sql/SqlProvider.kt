package kr.co.jinia91.spring.sample.user.adapters.persistance.sql

import org.springframework.stereotype.Component

interface SqlProvider {
    fun getSql(table: String, key: String): String
}



class SqlRetrievalFailureException(message: String) : RuntimeException(message)