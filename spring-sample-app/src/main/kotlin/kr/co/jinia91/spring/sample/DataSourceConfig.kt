package kr.co.jinia91.spring.sample

import java.sql.ResultSet
import javax.sql.DataSource
import kr.co.jinia91.spring.sample.user.User
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.datasource.DriverManagerDataSource

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
    fun userMapper() : RowMapper<User> = RowMapper<User> { rs: ResultSet, _: Int ->
        User(
            id = rs.getString("id"),
            name = rs.getString("name"),
            password = rs.getString("password")
        )
    }

    @Bean
    fun jdbcTemplate(dataSource: DataSource) = JdbcTemplate(dataSource)
}
