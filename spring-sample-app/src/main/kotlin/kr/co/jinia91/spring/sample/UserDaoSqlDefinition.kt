package kr.co.jinia91.spring.sample

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sql.user")
class UserDaoSqlDefinition(
    var select: String,
    var insert: String,
    var deleteAll: String,
    var count: String
)