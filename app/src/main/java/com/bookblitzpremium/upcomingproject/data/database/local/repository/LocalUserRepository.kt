package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.UserDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import javax.inject.Inject

class LocalUserRepository @Inject constructor(private val userDao: UserDao) {
    suspend fun getUserByID(userID: String) = userDao.getUserByID(userID)
    suspend fun deleteUser(user: User) = userDao.deleteUser(user)
    suspend fun addOrUpdateUser(user: User) = userDao.upsertUser(user)
}