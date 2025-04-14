package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {
    @Upsert()
    suspend fun upsertRating(rating: Rating)

    @Delete
    suspend fun deleteRating(rating: Rating)

    @Query("SELECT * FROM rating")
    fun getAllRatings(): Flow<List<Rating>>

    @Query("SELECT * FROM rating WHERE id = :id")
    suspend fun getRatingById(id: String): Rating?
}