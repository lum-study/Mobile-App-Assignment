package com.bookblitzpremium.upcomingproject.data.database.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelBookingDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.model.HotelBookingInformation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalHotelBookingRepo @Inject constructor(
    private val hotelBookingDao: HotelBookingDao,
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

    suspend fun getHotelBookingsByBookingID(id: String): List<HotelBooking> {
        return hotelBookingDao.getHotelBookingsByBookingId(id)
    }

    suspend fun upsertHotelBooking(hotelBooking: HotelBooking) {
        return hotelBookingDao.upsertHotelBooking(hotelBooking)
    }

    fun getHotelBookingByUserID(userID: String): Flow<PagingData<HotelBookingInformation>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { hotelBookingDao.getHotelBookingInformationByUserID(userID) }
        ).flow
    }
}