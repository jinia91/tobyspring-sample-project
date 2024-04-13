package kr.co.jinia91.spring.sample.user

interface UserRepository {
    fun save(user: User): User
    fun deleteAll()
    fun findById(id: String): User?
}