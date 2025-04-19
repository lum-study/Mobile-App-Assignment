package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.bookblitzpremium.upcomingproject.data.model.TripPackageInformation

@Dao
interface TripPackageDao {
    @Upsert()
    suspend fun upsertTrip(trip: TripPackage)

    @Delete
    suspend fun deleteTrip(trip: TripPackage)

    @Query("SELECT * FROM trip_package")
    fun getAllTrips(): PagingSource<Int, TripPackage>

    @Query("SELECT * FROM trip_package WHERE id = :id")
    suspend fun getTripByID(id: String): TripPackage?

    @Query(
        """
        SELECT * FROM trip_package tp
        INNER JOIN flight f ON tp.flightId = f.id
        WHERE (tp.name LIKE '%' || :input || '%' OR tp.location LIKE '%' || :input || '%' OR tp.description LIKE '%' || :input || '%')
        AND (tp.price BETWEEN :startPrice AND :endPrice)
        AND (:startDate = "" OR :endDate = "" OR tp.startDate BETWEEN :startDate AND :endDate)
        AND (:departure = f.departState AND :arrival = f.arrivalState)
        ORDER BY 
        tp.price,
        CASE WHEN :startDate != "" THEN tp.startDate ELSE NULL END,
        name ASC
        """
    )
    fun getFilteredTrip(
        input: String,
        startPrice: Double = 0.0,
        endPrice: Double = 0.0,
        departure: String,
        arrival: String,
        startDate: String = "",
        endDate: String = ""
    ): PagingSource<Int, TripPackage>

    @Query("""
        SELECT * FROM trip_package
        WHERE (name LIKE '%' || :input || '%' OR location LIKE '%' || :input || '%' OR description LIKE '%' || :input || '%')
    """)
    fun filterByKeyword(input: String): PagingSource<Int, TripPackage>

    @Transaction
    @Query(
        """
            SELECT tp.*, h.name as hotelName, f.departState as flightDepart, f.arrivalState as flightArrival, s.day as scheduleDay
        FROM trip_package tp
        INNER JOIN hotel h ON tp.hotelId = h.id
        INNER JOIN flight f ON tp.flightId = f.id
        INNER JOIN schedule s ON tp.id = s.tripPackageId
        WHERE tp.id = :tripPackageID
        AND s.day = (
            SELECT MAX(day)
            FROM schedule
            WHERE tripPackageID = tp.id
        )

        """
    )
    suspend fun getTripPackageInformation(tripPackageID: String): TripPackageInformation
}