package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.model.HotelBookingInformation
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

    @Upsert()
    suspend fun upsertHotelBooking(booking: HotelBooking)

    @Query("""
        SELECT hb.*, h.name as hotelName, h.imageUrl as hotelImageUrl, p.createDate as purchaseDate, p.totalAmount as totalAmount
        FROM hotel_booking hb
        INNER JOIN hotel h ON h.id = hb.hotelID
        INNER JOIN payment p ON p.id = hb.paymentID
        WHERE hb.userid = :userID
    """)
    fun getHotelBookingInformationByUserID(userID: String): PagingSource<Int, HotelBookingInformation>
}