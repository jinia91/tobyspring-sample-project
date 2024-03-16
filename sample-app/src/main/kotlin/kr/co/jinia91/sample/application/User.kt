package kr.co.jinia91.sample.application

import org.hibernate.validator.constraints.Length

data class User(
    @field:Length(min = 4, max = 10)
    val id: String,
    @field:Length(min = 1, max = 5)
    var name: String,
    @field:Length(min = 6, max = 10)
    var password: String
)