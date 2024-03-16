package kr.co.jinia91.sample.application

import kr.co.jinia91.springframework.core.annotation.Autowired
import kr.co.jinia91.springframework.core.annotation.Component


@Component
class UserDao{

    @field:Autowired
    lateinit var connectionMaker: ConnectionMaker

    fun add(user: User) {
        connectionMaker.makeConnection()
            .use { conn ->
                conn.prepareStatement("insert into user values(?, ?, ?)").use { stmt ->
                    stmt.setString(1, user.id)
                    stmt.setString(2, user.name)
                    stmt.setString(3, user.password)
                    stmt.executeUpdate()
                }
            }
    }

    fun get(id: String): User? {
        connectionMaker.makeConnection()
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
                        return null
                    }
                }
            }
    }

    fun deleteAll() {
        connectionMaker.makeConnection()
            .use { conn ->
                conn.prepareStatement("delete from user").use { stmt ->
                    stmt.executeUpdate()
                }
            }
    }
}
