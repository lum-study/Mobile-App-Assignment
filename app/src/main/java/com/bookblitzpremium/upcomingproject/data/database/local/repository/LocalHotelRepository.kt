package com.bookblitzpremium.upcomingproject.data.database.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalHotelRepository @Inject constructor(private val hotelDao: HotelDao) {
    fun getHotelsPagingFlow(): Flow<PagingData<Hotel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { hotelDao.getAllHotels() }
        ).flow
    }

    fun getFilteredHotelsPagingFlow(
        input: String,
        rating: Double,
        startPrice: Double,
        endPrice: Double,
        feature1: String,
        feature2: String
    ): Flow<PagingData<Hotel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                hotelDao.getHotelByFilter(
                    input = input,
                    rating = rating,
                    startPrice = startPrice,
                    endPrice = endPrice,
                    feature1 = feature1,
                    feature2 = feature2
                )
            }
        ).flow
    }

    fun getByKeyword(keyword: String): Flow<PagingData<Hotel>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { hotelDao.filterByKeyword(input = keyword) }
        ).flow
    }

    suspend fun addOrUpdateHotel(hotel: Hotel) = hotelDao.upsertHotel(hotel)
    suspend fun deleteHotel(hotel: Hotel) = hotelDao.deleteHotel(hotel)
    suspend fun getHotelByID(id: String) = hotelDao.getHotelByID(id)
    suspend fun getAllHotel() = hotelDao.getAllHotels()

}