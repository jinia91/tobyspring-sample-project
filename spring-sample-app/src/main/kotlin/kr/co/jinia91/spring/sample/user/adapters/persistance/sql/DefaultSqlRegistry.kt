package kr.co.jinia91.spring.sample.user.adapters.persistance.sql

import org.springframework.stereotype.Component

@Component
class DefaultSqlRegistry(
    private val sqlMap: MutableMap<String, String> = mutableMapOf()
) : SqlRegistry {
    override fun findSql(table: String, key: String): String {
        return this.sqlMap["$table.$key"]
            ?: throw SqlNotFoundException("Sql not found for $table.$key")
    }

    override fun registerSql(table: String, key: String, sql: String) {
        this.sqlMap["$table.$key"] = sql
    }
}