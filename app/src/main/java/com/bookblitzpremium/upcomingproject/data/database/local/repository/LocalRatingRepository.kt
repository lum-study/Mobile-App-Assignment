package com.bookblitzpremium.upcomingproject.data.database.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.dao.RatingDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.model.RatingRecord
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRatingRepository @Inject constructor(private val ratingDao: RatingDao) {
    fun getRatingsPagingFlowByHotel(hotelId: String): Flow<PagingData<Rating>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            pagingSourceFactory = { ratingDao.getRatingsByHotelID(hotelId) }
        ).flow
    }

    suspend fun addOrUpdateRating(rating: Rating) = ratingDao.upsertRating(rating)
    suspend fun deleteRating(rating: Rating) = ratingDao.deleteRating(rating)

    suspend fun getRatingByHotelId(hotelId: String): List<Rating> {
        return ratingDao.getRatingByHotelId(hotelId)
    }

    suspend fun getRatingByUserID(userID: String): List<RatingRecord> {
        return ratingDao.getRatingByUserID(userID)
    }

    fun getAllRatingsFlow(): Flow<List<Rating>> = ratingDao.getAllRatingsFlow()

    suspend fun getRatingById(id: String): Rating? = ratingDao.getRatingById(id)
}