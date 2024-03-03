package org.ptudy.spring_kotlin

import java.sql.Connection
import java.sql.DriverManager

class UserDao {
    fun add(user: User) {
        getConnection()
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
        getConnection()
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

    private fun getConnection(): Connection =
        DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "root", "")
}

fun main() {
    val userDao = UserDao()
    val user = User("1234", "name", "password")
    userDao.add(user)
    val user2 = userDao.get("1234")
    println(user2)
}