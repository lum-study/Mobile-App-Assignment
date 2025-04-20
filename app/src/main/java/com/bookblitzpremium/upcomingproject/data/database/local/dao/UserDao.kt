package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: User)

    @Query("SELECT email FROM user WHERE email = :emails")
    suspend fun findUserEmail(emails: String): String?

    @Query("SELECT * FROM user")
    fun selectAllUser(): Flow<List<User>>
}