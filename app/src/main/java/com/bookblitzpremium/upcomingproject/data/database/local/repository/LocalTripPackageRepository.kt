package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalTripPackageRepository @Inject constructor(private val tripPackageDao: TripPackageDao) {
    val allTrips: Flow<List<TripPackage>> = tripPackageDao.getAllTrips()

    suspend fun addOrUpdateTripPackage(trip: TripPackage) = tripPackageDao.upsertTrip(trip)
    suspend fun deleteTripPackage(trip: TripPackage) = tripPackageDao.deleteTrip(trip)
    suspend fun getTripById(id: String) = tripPackageDao.getTripById(id)
}