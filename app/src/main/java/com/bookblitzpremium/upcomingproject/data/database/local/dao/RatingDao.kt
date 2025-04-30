package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.*
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {

    @Upsert
    suspend fun upsertRating(rating: Rating)

    @Delete
    suspend fun deleteRating(rating: Rating)

    @Query("SELECT * FROM rating WHERE hotelID = :hotelId")
    fun getRatingsByHotelID(hotelId: String): PagingSource<Int, Rating>

    @Query("SELECT * FROM rating WHERE id = :id")
    suspend fun getRatingById(id: String): Rating?

    @Query("SELECT * FROM rating ORDER BY id DESC")
    fun getAllRatingsFlow(): Flow<List<Rating>>
}