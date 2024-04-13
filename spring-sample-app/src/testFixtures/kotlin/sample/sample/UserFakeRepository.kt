package sample.sample

import kr.co.jinia91.spring.sample.user.User
import kr.co.jinia91.spring.sample.user.UserRepository

class UserFakeRepository : UserRepository {
    private val users = mutableMapOf<String, User>()

    override fun save(user: User) : User {
        users[user.id] = user
        return user
    }

    override fun deleteAll() {
        users.clear()
    }

    override fun findById(id: String): User? {
        return users[id]
    }
}