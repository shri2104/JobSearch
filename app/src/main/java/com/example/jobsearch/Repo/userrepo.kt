package com.example.jobsearch.Repo

import com.example.jobsearch.Dao.UserDao
import com.example.jobsearch.Entity.User
import javax.inject.Inject

class UserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun registerUser(user: User): Boolean {
        return try {
            val existingUser = userDao.checkIfEmailExists(user.email)
            if (existingUser == null) {
                userDao.insertUser(user)
                true
            } else {
                false
            }
        } catch (e: Exception) {
            false
        }
    }
    suspend fun checkEmailExists(email: String): Boolean {
        return userDao.checkIfEmailExists(email) != null
    }
    suspend fun login(email: String, password: String): Boolean {
        return try {
            val user = userDao.login(email, password)
            user != null
        } catch (e: Exception) {
            false
        }
    }
}

