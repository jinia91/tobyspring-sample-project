package kr.co.jinia91.spring.sample.user.adapters.persistance

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties(prefix = "sql.user")
class UserDaoSqlDefinition(
    var select: String,
    var insertOrUpdate: String,
    var deleteAll: String,
    var selectAll: String,
    var count: String
)