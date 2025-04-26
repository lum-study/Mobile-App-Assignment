package com.bookblitzpremium.upcomingproject.data.database.local.repository

import androidx.paging.PagingSource
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.UserDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class LocalUserRepository @Inject constructor(
    private val userDao: UserDao
) {
    suspend fun insertUsers(user: User) = userDao.insertUsers(user)

    suspend fun findUserEmail(emails: String): Boolean? = userDao.findUserEmail(emails)

    fun selectAllUser(): Flow<List<User>> = userDao.selectAllUser()

    fun retrivePassword(password: String) = userDao.retievePassword(password)

    suspend fun validateUser(email: String, password: String): Result<String> {
        val user = userDao.getUserByEmail(email)

        return when {
            user == null -> Result.failure(Exception("Email is not registered"))
            user.password != password -> Result.failure(Exception("Incorrect password"))
            else -> Result.success(user.uid)
        }
    }

}