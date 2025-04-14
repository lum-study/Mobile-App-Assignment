package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import kotlinx.coroutines.flow.Flow

@Dao
interface TripPackageDao {
    @Upsert()
    suspend fun upsertTrip(trip: TripPackage)

    @Delete
    suspend fun deleteTrip(trip: TripPackage)

    @Query("SELECT * FROM trip_package")
    fun getAllTrips(): Flow<List<TripPackage>>

    @Query("SELECT * FROM trip_package WHERE id = :id")
    suspend fun getTripById(id: String): TripPackage?
}
