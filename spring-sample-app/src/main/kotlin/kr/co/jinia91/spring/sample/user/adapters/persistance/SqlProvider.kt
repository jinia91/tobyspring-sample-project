package kr.co.jinia91.spring.sample.user.adapters.persistance

import org.springframework.stereotype.Component

interface SqlProvider {
    fun getSql(table: String, key: String): String
}

@Component
class SimpleSqlProvider(
    private val userDaoSqlDefinition: UserDaoSqlDefinition
) : SqlProvider {
    override fun getSql(table: String, key: String): String {
        if (table == "user") {
            return userDaoSqlDefinition.queries["$key"]
                ?: throw SqlRetrievalFailureException("Sql not found for $table.$key")
        } else {
            throw SqlRetrievalFailureException("Sql not found for $table.$key")
        }
    }

}

class SqlRetrievalFailureException(message: String) : RuntimeException(message)