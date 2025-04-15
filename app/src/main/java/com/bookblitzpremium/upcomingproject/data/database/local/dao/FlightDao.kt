package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Upsert()
    suspend fun upsertFlight(flight: Flight)

    @Delete
    suspend fun deleteFlight(flight: Flight)

    @Query("SELECT * FROM flight")
    fun getAllFlights(): Flow<List<Flight>>

    @Query("SELECT * FROM flight WHERE id = :id")
    fun getFlightByID(id: String): Flight?

    @Query("SELECT id FROM flight WHERE arrivalState = :arrivalState AND departState = :departState")
    fun getFlightIDByPlace(arrivalState: String, departState: String): List<String>
}