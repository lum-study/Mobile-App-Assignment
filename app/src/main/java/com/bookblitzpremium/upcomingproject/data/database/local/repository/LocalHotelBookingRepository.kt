package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelBookingDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalHotelBookingRepo @Inject constructor(private val hotelBookingDao: HotelBookingDao) {

    fun getAllHotelBookings(): Flow<List<HotelBooking>> {
        return hotelBookingDao.getAllHotelBookings()
    }

    suspend fun getHotelBookingById(bookingId: Long): HotelBooking? {
        return hotelBookingDao.getHotelBookingById(bookingId)
    }

    suspend fun insertHotelBooking(hotelBooking: HotelBooking): Long {
        return hotelBookingDao.insertHotelBooking(hotelBooking)
    }

}