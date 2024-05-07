package kr.co.jinia91.spring.sample.user.adapters.persistance.sql

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class DefaultSqlProvider(
    private val userDaoSqlDefinition: UserDaoSqlDefinition,
    private val sqlMap: MutableMap<String, String> = mutableMapOf()
) : SqlProvider, SqlReader, SqlRegistry {

    @PostConstruct
    fun loadSql() {
        read(this)
    }

    override fun getSql(table: String, key: String): String {
        return findSql(table, key)
    }

    override fun read(sqlRegistry: SqlRegistry) {
        val userSql = this.userDaoSqlDefinition.queries
        userSql.forEach { (key, value) ->
            sqlRegistry.registerSql("user", key, value)
        }
    }

    override fun findSql(table: String, key: String): String {
        return this.sqlMap["$table.$key"]
            ?: throw SqlNotFoundException("Sql not found for $table.$key")
    }

    override fun registerSql(table: String, key: String, sql: String) {
        this.sqlMap["$table.$key"] = sql
    }

}