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

    suspend fun findUserEmail(emails: String): String? = userDao.findUserEmail(emails)

    fun selectAllUser(): Flow<List<User>> = userDao.selectAllUser()
}