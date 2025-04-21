package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelBookingDao {
    @Query("SELECT * FROM hotel_booking")
    fun getAllHotelBookings(): Flow<List<HotelBooking>>

    @Query("SELECT * FROM hotel_booking WHERE id = :bookingId")
    suspend fun getHotelBookingById(bookingId: Long): HotelBooking?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotelBooking(hotelBooking: HotelBooking): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotelBookings(hotelBookings: List<HotelBooking>): List<Long>
}