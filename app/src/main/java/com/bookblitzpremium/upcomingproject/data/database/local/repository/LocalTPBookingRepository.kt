package com.bookblitzpremium.upcomingproject.data.database.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TPBookingDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TPBooking
import com.bookblitzpremium.upcomingproject.data.model.TPBookingInformation
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTPBookingRepository @Inject constructor(private val tpBookingDao: TPBookingDao) {
    fun getTPBookingByUserID(userID: String): Flow<PagingData<TPBookingInformation>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { tpBookingDao.getTripPackageBookingsByUserID(userID) }
        ).flow
    }

    suspend fun addOrUpdateTPBooking(tripPackageBooking: TPBooking) =
        tpBookingDao.upsertTripPackageBooking(tripPackageBooking)

    suspend fun deleteTPBooking(tripPackageBooking: TPBooking) =
        tpBookingDao.upsertTripPackageBooking(tripPackageBooking)
}