package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelBookingDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalHotelBookingRepo @Inject constructor(
    private val hotelBookingDao: HotelBookingDao
) {

    fun getAllHotelBookings(): Flow<List<HotelBooking>> {
        return hotelBookingDao.getAllHotelBookings()
    }

    suspend fun getHotelBookingsByBookingUserId(userId: String): List<HotelBooking> {
        return hotelBookingDao.getHotelBookingsByBookingUserId(userId)
    }

    suspend fun insertHotelBooking(hotelBooking: HotelBooking): Long {
        return hotelBookingDao.insertHotelBooking(hotelBooking)
    }

    suspend fun updateHotelBooking(booking: HotelBooking) {
        hotelBookingDao.updateHotelBooking(booking)
    }

    suspend fun getHotelBookingsByHotelId(id: String):List<HotelBooking> {
        return hotelBookingDao.getHotelBookingsByBookingId(id)
    }

    suspend fun upsertHotelBooking(hotelBooking: HotelBooking){
        return hotelBookingDao.upsertHotelBooking(hotelBooking)
    }
}