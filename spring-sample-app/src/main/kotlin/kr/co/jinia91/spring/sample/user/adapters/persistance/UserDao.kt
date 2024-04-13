package kr.co.jinia91.spring.sample.user.adapters.persistance

import kr.co.jinia91.spring.sample.user.domain.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

@Repository
class UserDao(
    private val jdbcTemplate: JdbcTemplate,
    private val userMapper: RowMapper<User>,
    private val userDaoSqlDefinition: UserDaoSqlDefinition,
) {
    fun addAndGet(user: User): User {
        insertOrUpdate(user)
        return get(user.id) ?: throw IllegalStateException("User not persist")
    }

    fun insertOrUpdate(user: User) {
        jdbcTemplate.update(userDaoSqlDefinition.insertOrUpdate,
            user.id, user.name, user.password, user.level.toString(), user.logInCount, user.postCount,
            user.name, user.password, user.level.toString(), user.logInCount, user.postCount
        )
    }

    fun get(id: String): User? {
        return jdbcTemplate.query(userDaoSqlDefinition.select, userMapper, id)
            .firstOrNull()
    }

    fun getAll(): List<User> {
        return jdbcTemplate.query(userDaoSqlDefinition.selectAll, userMapper)
    }

    fun deleteAll() {
        jdbcTemplate.execute(userDaoSqlDefinition.deleteAll)
    }

    fun getCount(): Int {
        return jdbcTemplate.queryForObject(userDaoSqlDefinition.count, Int::class.java)!!
    }
}
