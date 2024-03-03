package org.ptudy.spring_kotlin.src.application

import java.sql.Connection
import java.sql.DriverManager
import org.ptudy.spring_kotlin.di_container.annotation.Component

interface ConnectionMaker {
    fun makeConnection(): Connection
}

@Component
class DConnectionMaker : ConnectionMaker {
    override fun makeConnection(): Connection {
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/spring", "root", "")
    }
}

class NConnectionMaker : ConnectionMaker {
    override fun makeConnection(): Connection {
        return DriverManager.getConnection("jdbc:mysql://localhost:3307/spring", "root", "")
    }
}