package kr.co.jinia91.spring.sample.user.adapters.persistance

import java.sql.ResultSet
import javax.sql.DataSource
import kr.co.jinia91.spring.sample.user.domain.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.DriverManagerDataSource
import org.springframework.transaction.PlatformTransactionManager

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

    @Bean
    fun transactionManager(dataSource: DataSource) : PlatformTransactionManager = DataSourceTransactionManager(dataSource)

    @Bean
    fun userMapper() : RowMapper<User> = RowMapper<User> { rs: ResultSet, _: Int ->
        User(
            id = rs.getString("id"),
            name = rs.getString("name"),
            password = rs.getString("password"),
            level = User.Level.valueOf(rs.getString("level")),
            logInCount = rs.getInt("login_cnt"),
            postCount = rs.getInt("post_cnt"),
            email = rs.getString("email")
        )
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource) = JdbcTemplate(dataSource)
}
