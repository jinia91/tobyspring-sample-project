package kr.co.jinia91.spring.sample

import java.sql.ResultSet
import javax.sql.DataSource
import kr.co.jinia91.spring.sample.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
class DataSourceConfig {
    @Bean
    fun dataSource(): DriverManagerDataSource {
        return DriverManagerDataSource(
            "jdbc:mysql://localhost:3306/spring?allowPublicKeyRetrieval=true&useSSL=false",
            "root",
            ""
        )
    }
}


@Component
class UserDao(
    private val dataSource: DataSource
) {
    private val jdbcTemplate = JdbcTemplate(dataSource)

    private val userMapper = { rs: ResultSet, _: Int ->
        User(
            id = rs.getString("id"),
            name = rs.getString("name"),
            password = rs.getString("password")
        )
    }

    fun addAndGet(user: User): User {
        jdbcTemplate.update("insert into user values(?, ?, ?)", user.id, user.name, user.password)
        return jdbcTemplate.query("select * from user where id = ?", userMapper, user.id)
            .firstOrNull() ?: throw IllegalArgumentException("User not found")
    }

    fun add(user: User) {
        jdbcTemplate.update("insert into user values(?, ?, ?)", user.id, user.name, user.password)
    }

    fun get(id: String): User {
        return jdbcTemplate.query("select * from user where id = ?", userMapper, id)
            .firstOrNull() ?: throw IllegalArgumentException("User not found")
    }

    fun deleteAll() {
        jdbcTemplate.execute("delete from user")
    }

    fun getCount(): Int {
        return jdbcTemplate.queryForObject("select count(*) from user", Int::class.java)!!
    }
}
