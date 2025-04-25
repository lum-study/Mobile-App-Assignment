package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TPBooking
import com.bookblitzpremium.upcomingproject.data.model.TPBookingInformation

@Dao
interface TPBookingDao {
    @Upsert
    suspend fun upsertTripPackageBooking(tripPackageBooking: TPBooking)

    @Delete
    suspend fun deleteTripPackageBooking(tripPackageBooking: TPBooking)

    @Query(
        """
        SELECT b.*, tp.name as tripPackageName, tp.startDate as tripPackageStartDate,tp.imageUrl as tripPackageImageUrl, p.totalAmount as paymentAmount, p.createDate as purchaseDate
        FROM tp_booking b
        INNER JOIN trip_package tp ON tp.id = b.tripPackageID
        INNER JOIN payment p ON p.id = b.paymentID
        WHERE b.userID = :userID
    """
    )
    fun getTripPackageBookingsByUserID(userID: String): PagingSource<Int, TPBookingInformation>
}