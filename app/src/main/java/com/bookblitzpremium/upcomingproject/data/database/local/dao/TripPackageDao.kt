package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage

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
        SELECT * FROM trip_package 
        WHERE (name LIKE '%' || :input || '%' OR location LIKE '%' || :input || '%' OR description LIKE '%' || :input || '%')
        AND (price BETWEEN :startPrice AND :endPrice)
        AND (:startDate = "" OR :endDate = "" OR startDate BETWEEN :startDate AND :endDate)
        AND (:flightID IS NULL OR flightID IN (:flightID))
        ORDER BY 
        price,
        CASE WHEN :startDate != "" THEN startDate ELSE NULL END,
        name ASC
        """
    )
    fun getFilteredTrip(
        input: String,
        startPrice: Double = 0.0,
        endPrice: Double = 0.0,
        flightID: List<String>? = null,
        startDate: String = "",
        endDate: String = ""
    ): PagingSource<Int, TripPackage>
}