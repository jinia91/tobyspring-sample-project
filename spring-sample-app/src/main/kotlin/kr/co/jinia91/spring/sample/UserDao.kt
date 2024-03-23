package kr.co.jinia91.spring.sample

import javax.sql.DataSource
import kr.co.jinia91.spring.sample.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
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
    fun addAndGet(user: User): User {
        dataSource.connection
            .use { conn ->
                conn.prepareStatement("insert into user values(?, ?, ?)").use { stmt ->
                    stmt.setString(1, user.id)
                    stmt.setString(2, user.name)
                    stmt.setString(3, user.password)
                    stmt.executeUpdate()
                }

                conn.prepareStatement("select * from user where id = ?").use { stmt ->
                    stmt.setString(1, user.id)
                    val rs = stmt.executeQuery()
                    rs.next()
                    return User(
                        id = rs.getString("id"),
                        name = rs.getString("name"),
                        password = rs.getString("password")
                    )
                }
            }
    }

    fun add(user: User) {
        dataSource.connection
            .use { conn ->
                conn.prepareStatement("insert into user values(?, ?, ?)").use { stmt ->
                    stmt.setString(1, user.id)
                    stmt.setString(2, user.name)
                    stmt.setString(3, user.password)
                    stmt.executeUpdate()
                }
            }
    }

    fun get(id: String): User {
        dataSource.connection
            .use { conn ->
                conn.prepareStatement("select * from user where id = ?").use { stmt ->
                    stmt.setString(1, id)
                    val rs = stmt.executeQuery()
                    if (rs.next()) {
                        return User(
                            id = rs.getString("id"),
                            name = rs.getString("name"),
                            password = rs.getString("password")
                        )
                    } else {
                        throw IllegalArgumentException("존재하지 않는 유저입니다.")
                    }
                }
            }
    }

    fun deleteAll() {
        dataSource.connection
            .use { conn ->
                conn.prepareStatement("delete from user").use { stmt ->
                    stmt.executeUpdate()
                }
            }
    }

    fun getCount(): Int {
        dataSource.connection
            .use { conn ->
                conn.prepareStatement("select count(*) from user").use { stmt ->
                    val rs = stmt.executeQuery()
                    rs.next()
                    return rs.getInt(1)
                }
            }
    }
}
