package kr.co.jinia91.spring.sample.user.adapters.persistance.sql

interface SqlRegistry {
    fun findSql(table: String, key: String): String
    fun registerSql(table: String, key: String, sql: String)
}

class SqlNotFoundException(message: String) : RuntimeException(message)

