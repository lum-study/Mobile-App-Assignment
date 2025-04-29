package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating

@Dao
interface RatingDao {
    @Upsert()
    suspend fun upsertRating(rating: Rating)

    @Delete
    suspend fun deleteRating(rating: Rating)

    @Query("SELECT * FROM rating WHERE hotelId = :hotelId")
    fun getRatingsByHotelID(hotelId: String): PagingSource<Int, Rating>

    @Query("SELECT * FROM rating WHERE hotelId = :hotelId")
    suspend fun getRatingByHotelId(hotelId: String): List<Rating>

}