package kr.co.jinia91.spring.sample.user.adapters.persistance.sql

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sql.user")
class UserDaoSqlDefinition(val queries: Map<String, String>)