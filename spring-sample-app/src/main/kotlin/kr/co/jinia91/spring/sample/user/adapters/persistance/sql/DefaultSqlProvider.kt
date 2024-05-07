package kr.co.jinia91.spring.sample.user.adapters.persistance.sql

import org.springframework.stereotype.Component

@Component
class DefaultSqlProvider(
    private val sqlRegistry: SqlRegistry
) : SqlProvider {
    override fun getSql(table: String, key: String): String {
        return sqlRegistry.findSql(table, key)
    }
}