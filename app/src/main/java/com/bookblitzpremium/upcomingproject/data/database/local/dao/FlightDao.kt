package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import com.bookblitzpremium.upcomingproject.data.model.FlightInformation
import kotlinx.coroutines.flow.Flow

@Dao
interface FlightDao {
    @Upsert
    suspend fun upsertFlight(flight: Flight)

    @Delete
    suspend fun deleteFlight(flight: Flight)

    @Query("SELECT * FROM flight")
    fun getAllFlights(): Flow<List<Flight>>

    @Query("SELECT * FROM flight WHERE id = :id")
    suspend fun getFlightByID(id: String): Flight?

    @Query("SELECT id FROM flight WHERE arrivalState = :arrivalState AND departState = :departState")
    suspend fun getFlightIDByPlace(arrivalState: String, departState: String): List<String>

    @Query("""
        SELECT f.*, tp.startDate AS endDate, s.time AS endTime
        FROM flight f 
        INNER JOIN trip_package tp ON f.id = tp.flightID
        INNER JOIN schedule s ON s.tripPackageID = tp.id
        WHERE f.id = :id
        AND s.day = 1
        AND (s.day, s.time) = (
            SELECT day, MIN(time)
            FROM schedule
            WHERE tripPackageID = tp.id
            AND day = 1
            ORDER BY
            CAST(SUBSTR(time, 1, INSTR(time, ':') - 1) AS INTEGER) * 60 +
            CAST(SUBSTR(time, INSTR(time, ':') + 1) AS INTEGER)
            LIMIT 1
        )
        """)
    suspend fun getFlightInformationByID(id: String): FlightInformation?
}