package kr.co.jinia91.spring.sample.user.domain

interface UserRepository {
    fun save(user: User): User
    fun deleteAll()
    fun findById(id: String): User?

    fun findAll(): List<User>

    fun saveAll(users: List<User>)
}