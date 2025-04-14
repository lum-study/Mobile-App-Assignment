package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel

@Dao
interface HotelDao {
    @Upsert()
    suspend fun upsertHotel(hotel: Hotel)

    @Delete
    suspend fun deleteHotel(hotel: Hotel)

    @Query("SELECT * FROM hotel")
    fun getAllHotels(): PagingSource<Int, Hotel>

    @Query("SELECT * FROM hotel WHERE id = :id")
    suspend fun getHotelByID(id: String): Hotel?

    @Query(
        """
        SELECT * FROM hotel 
        WHERE (name LIKE '%' || :input || '%' OR address LIKE '%' || :input || '%')
        AND (:rating = 0.0 OR rating >= :rating)
        AND (price BETWEEN :startPrice AND :endPrice)
        AND (:feature1 = "" OR feature LIKE '%' || :feature1 || '%') 
        AND (:feature1 = "" OR feature LIKE '%' || :feature2 || '%' ) 
        ORDER BY 
        price,
        CASE WHEN :startPrice = 0.0 AND :rating != 0.0 THEN rating ELSE NULL END,
        name ASC
        """
    )
    fun getHotelByFilter(
        input: String,
        rating: Double = 0.0,
        startPrice: Double = 0.0,
        endPrice: Double = 0.0,
        feature1: String = "",
        feature2: String = ""
    ): PagingSource<Int, Hotel>
}