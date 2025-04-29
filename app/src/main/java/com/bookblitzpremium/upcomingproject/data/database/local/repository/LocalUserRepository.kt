package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.UserDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalUserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun getUserByID(userID: String) = userDao.getUserByID(userID)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun addOrUpdateUser(user: User) = userDao.upsertUser(user)

    suspend fun findUserEmail(emails: String): Boolean? = userDao.findUserEmail(emails)

    fun selectAllUser(): Flow<List<User>> = userDao.selectAllUser()

    fun retrivePassword(password: String) = userDao.retievePassword(password)

    suspend fun validateUser(email: String, password: String): Result<String> {
        val user = userDao.getUserByEmail(email)

        return when {
            user == null -> Result.failure(Exception("Email is not registered"))
            user.password != password -> Result.failure(Exception("Incorrect password"))
            else -> Result.success(user.id)
        }
    }

    suspend fun updateUserGender(id:String, gender:String) = userDao.updateUserGender(id,gender)
}
