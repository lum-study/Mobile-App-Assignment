package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelDao {
    @Upsert()
    suspend fun upsertHotel(hotel: Hotel)

    @Delete
    suspend fun deleteHotel(hotel: Hotel)

    @Query("SELECT * FROM hotel")
    fun getAllHotels(): Flow<List<Hotel>>

    @Query("SELECT * FROM hotel WHERE id = :id")
    suspend fun getHotelById(id: String): Hotel?
}