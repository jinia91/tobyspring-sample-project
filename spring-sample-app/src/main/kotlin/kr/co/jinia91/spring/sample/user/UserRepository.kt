package kr.co.jinia91.spring.sample.user

interface UserRepository {
    fun save(user: User)
}