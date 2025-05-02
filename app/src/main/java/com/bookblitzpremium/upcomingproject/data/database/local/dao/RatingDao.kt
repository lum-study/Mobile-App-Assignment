package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.model.RatingRecord
import kotlinx.coroutines.flow.Flow

@Dao
interface RatingDao {
    @Upsert
    suspend fun upsertRating(rating: Rating)

    @Delete
    suspend fun deleteRating(rating: Rating)

    @Query("SELECT * FROM rating WHERE hotelID = :hotelId")
    fun getRatingsByHotelID(hotelId: String): PagingSource<Int, Rating>

    @Query("SELECT * FROM rating WHERE hotelID = :hotelId")
    suspend fun getRatingByHotelId(hotelId: String): List<Rating>

    @Query("SELECT * FROM rating WHERE id = :id")
    suspend fun getRatingById(id: String): Rating?

    @Query("SELECT * FROM rating ORDER BY id DESC")
    fun getAllRatingsFlow(): Flow<List<Rating>>

    @Query("""
        SELECT r.*, h.name as hotelName, h.imageUrl as hotelImageUrl 
        FROM rating r 
        INNER JOIN hotel h ON r.hotelID = h.id
        WHERE userID = :userID
    """)
    suspend fun getRatingByUserID(userID: String): List<RatingRecord>
}