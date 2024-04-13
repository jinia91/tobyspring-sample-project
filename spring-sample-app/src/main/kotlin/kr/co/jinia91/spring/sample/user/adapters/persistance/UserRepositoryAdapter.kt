package kr.co.jinia91.spring.sample.user.adapters.persistance

import kr.co.jinia91.spring.sample.user.User
import kr.co.jinia91.spring.sample.user.UserRepository
import org.springframework.stereotype.Repository

@Repository
class UserRepositoryAdapter(
    private val userDao: UserDao,
) : UserRepository {
    override fun save(user: User): User {
        return userDao.addAndGet(user)
    }

    override fun deleteAll() {
        userDao.deleteAll()
    }
}