package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.FlightDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalFlightRepository @Inject constructor(private val flightDao: FlightDao) {
    val allFlights: Flow<List<Flight>> = flightDao.getAllFlights()

    suspend fun addOrUpdateFlight(flight: Flight) = flightDao.upsertFlight(flight)
    suspend fun deleteFlight(flight: Flight) = flightDao.deleteFlight(flight)
    suspend fun getFlightById(id: String) = flightDao.getFlightById(id)
}