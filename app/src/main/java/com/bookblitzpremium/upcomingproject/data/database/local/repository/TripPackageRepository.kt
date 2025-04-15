package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class TripPackageRepository @Inject constructor(private val tripPackageDao: TripPackageDao) {
    val allTrips: Flow<List<TripPackage>> = tripPackageDao.getAllTrips()


    suspend fun insert(trip: TripPackage) = tripPackageDao.upsertTrip(trip)
    suspend fun delete(trip: TripPackage) = tripPackageDao.deleteTrip(trip)
    suspend fun getTripById(id: String) = tripPackageDao.getTripById(id)
}


