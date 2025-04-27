package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Upsert
    suspend fun upsertUser(user: User)

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * from user WHERE id = :userID")
    suspend fun getUserByID(userID: String): User?


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(vararg users: User)

    @Query("SELECT COUNT(*) > 0 FROM user WHERE email = :emails LIMIT 1")
    suspend fun findUserEmail(emails: String): Boolean?

    @Query("SELECT * FROM user")
    fun selectAllUser(): Flow<List<User>>

    @Query("SELECT password FROM user WHERE id = :password")
    fun retievePassword(password: String) : String?

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?
}