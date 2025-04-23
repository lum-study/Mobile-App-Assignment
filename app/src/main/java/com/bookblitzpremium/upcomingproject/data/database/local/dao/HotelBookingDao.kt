package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import kotlinx.coroutines.flow.Flow

@Dao
interface HotelBookingDao {
    @Query("SELECT * FROM hotel_booking")
    fun getAllHotelBookings(): Flow<List<HotelBooking>>

    @Query("SELECT * FROM hotel_booking WHERE userid = :userId")
    suspend fun getHotelBookingsByBookingUserId(userId: String): List<HotelBooking>

    @Query("SELECT * FROM hotel_booking WHERE id = :bookingId")
    suspend fun getHotelBookingsByBookingId(bookingId: String): List<HotelBooking>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHotelBooking(hotelBooking: HotelBooking): Long

    @Update
    suspend fun updateHotelBooking(booking: HotelBooking)

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertHotelBookings(hotelBookings: List<HotelBooking>): List<Long>


}