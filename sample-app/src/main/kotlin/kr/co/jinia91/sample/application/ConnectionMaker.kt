package kr.co.jinia91.sample.application

import java.sql.Connection
import java.sql.DriverManager

interface ConnectionMaker {
    fun makeConnection(): Connection
}

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