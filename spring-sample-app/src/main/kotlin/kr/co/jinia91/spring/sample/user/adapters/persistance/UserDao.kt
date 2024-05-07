package kr.co.jinia91.spring.sample.user.adapters.persistance

import kr.co.jinia91.spring.sample.user.domain.User
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.stereotype.Repository

interface UserDao{
    fun addAndGet(user: User): User
    fun insertOrUpdate(user: User)
    fun get(id: String): User?
    fun getAll(): List<User>
    fun deleteAll()
    fun getCount(): Int
}

@Repository
class UserDaoJdbc(
    private val jdbcTemplate: JdbcTemplate,
    private val userMapper: RowMapper<User>,
    private val sqlProvider: SqlProvider
) : UserDao {
    override fun addAndGet(user: User): User {
        insertOrUpdate(user)
        return get(user.id) ?: throw IllegalStateException("User not persist")
    }

    override fun insertOrUpdate(user: User) {
        jdbcTemplate.update(sqlProvider.getSql("user", "insertOrUpdate"),
            user.id, user.name, user.password, user.level.toString(), user.logInCount, user.postCount, user.email,
            user.name, user.password, user.level.toString(), user.logInCount, user.postCount, user.email
        )
    }

    override fun get(id: String): User? {
        return jdbcTemplate.query(sqlProvider.getSql("user","select"), userMapper, id)
            .firstOrNull()
    }

    override fun getAll(): List<User> {
        return jdbcTemplate.query(sqlProvider.getSql("user","selectAll"), userMapper)
    }

    override fun deleteAll() {
        jdbcTemplate.execute(sqlProvider.getSql("user","deleteAll"))
    }

    override fun getCount(): Int {
        return jdbcTemplate.queryForObject(sqlProvider.getSql("user", "count"), Int::class.java)!!
    }
}
