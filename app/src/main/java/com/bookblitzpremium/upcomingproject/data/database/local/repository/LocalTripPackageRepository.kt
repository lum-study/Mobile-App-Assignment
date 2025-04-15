package com.bookblitzpremium.upcomingproject.data.database.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTripPackageRepository @Inject constructor(private val tripPackageDao: TripPackageDao) {
    fun getTripPackagesPagingFlow(): Flow<PagingData<TripPackage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { tripPackageDao.getAllTrips() }
        ).flow
    }

    fun getFilteredTripPackagesPagingFlow(
        input: String,
        startPrice: Double,
        endPrice: Double,
        flightID: List<String>?,
        startDate: String,
        endDate: String
    ): Flow<PagingData<TripPackage>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                tripPackageDao.getFilteredTrip(
                    input = input,
                    startPrice = startPrice,
                    endPrice = endPrice,
                    flightID = flightID,
                    startDate = startDate,
                    endDate = endDate
                )
            }
        ).flow
    }

    suspend fun addOrUpdateTripPackage(trip: TripPackage) = tripPackageDao.upsertTrip(trip)
    suspend fun deleteTripPackage(trip: TripPackage) = tripPackageDao.deleteTrip(trip)
    suspend fun getTripByID(id: String) = tripPackageDao.getTripByID(id)
}