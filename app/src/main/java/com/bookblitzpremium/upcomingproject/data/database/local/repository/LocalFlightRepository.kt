package com.bookblitzpremium.upcomingproject.data.database.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.dao.FlightDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalFlightRepository @Inject constructor(private val flightDao: FlightDao) {
    fun getAllFlights(): Flow<PagingData<Flight>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { flightDao.getAllFlights() }
        ).flow
    }

    suspend fun addOrUpdateFlight(flight: Flight) = flightDao.upsertFlight(flight)
    suspend fun deleteFlight(flight: Flight) = flightDao.deleteFlight(flight)
    suspend fun getFlightByID(id: String) = flightDao.getFlightByID(id)
    suspend fun getFlightInformationByID(id: String) = flightDao.getFlightInformationByID(id)
    suspend fun getFlightIDByPlace(arrivalState: String, departState: String) =
        flightDao.getFlightIDByPlace(arrivalState = arrivalState, departState = departState)
}