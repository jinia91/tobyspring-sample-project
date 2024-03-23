package kr.co.jinia91.spring.sample

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component

@Component
class UserDao(
    private val jdbcTemplate: JdbcTemplate,
    private val userMapper: RowMapper<User>,
    private val userDaoSqlDefinition: UserDaoSqlDefinition
) {
    fun addAndGet(user: User): User {
        jdbcTemplate.update(userDaoSqlDefinition.insert, user.id, user.name, user.password)
        return jdbcTemplate.query(userDaoSqlDefinition.select, userMapper, user.id)
            .firstOrNull() ?: throw IllegalArgumentException("User not found")
    }

    fun add(user: User) {
        jdbcTemplate.update(userDaoSqlDefinition.insert, user.id, user.name, user.password)
    }

    fun get(id: String): User {
        return jdbcTemplate.query(userDaoSqlDefinition.select, userMapper, id)
            .firstOrNull() ?: throw IllegalArgumentException("User not found")
    }

    fun deleteAll() {
        jdbcTemplate.execute(userDaoSqlDefinition.deleteAll)
    }

    fun getCount(): Int {
        return jdbcTemplate.queryForObject(userDaoSqlDefinition.count, Int::class.java)!!
    }
}
