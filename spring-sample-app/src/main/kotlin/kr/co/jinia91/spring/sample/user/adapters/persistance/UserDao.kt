package kr.co.jinia91.spring.sample.user.adapters.persistance

import kr.co.jinia91.spring.sample.UserDaoSqlDefinition
import kr.co.jinia91.spring.sample.user.User
import kr.co.jinia91.spring.sample.user.UserRepository
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Component
import org.springframework.stereotype.Repository

@Repository
class UserDao(
    private val jdbcTemplate: JdbcTemplate,
    private val userMapper: RowMapper<User>,
    private val userDaoSqlDefinition: UserDaoSqlDefinition,
) {
    fun addAndGet(user: User): User {
        add(user)
        return get(user.id) ?: throw IllegalStateException("User not persist")
    }

    fun add(user: User) {
        jdbcTemplate.update(userDaoSqlDefinition.insert, user.id, user.name, user.password, user.level.toString(), user.logInCount)
    }

    fun get(id: String): User? {
        return jdbcTemplate.query(userDaoSqlDefinition.select, userMapper, id)
            .firstOrNull()
    }

    fun deleteAll() {
        jdbcTemplate.execute(userDaoSqlDefinition.deleteAll)
    }

    fun getCount(): Int {
        return jdbcTemplate.queryForObject(userDaoSqlDefinition.count, Int::class.java)!!
    }
}
