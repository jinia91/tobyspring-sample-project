package kr.co.jinia91.spring.sample.user.adapters.persistance.sql

interface SqlReader {
    fun read(sqlRegistry: SqlRegistry)
}