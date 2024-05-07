package kr.co.jinia91.spring.sample.user.adapters.persistance.sql

import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Component

@Component
class DefaultSqlReader(
    private val userDaoSqlDefinition: UserDaoSqlDefinition,
    private val sqlRegistry: SqlRegistry
) : SqlReader {

    @PostConstruct
    fun loadSql() {
        read(sqlRegistry)
    }

    override fun read(sqlRegistry: SqlRegistry) {
        val userSql = this.userDaoSqlDefinition.queries
        userSql.forEach { (key, value) ->
            sqlRegistry.registerSql("user", key, value)
        }
    }
}