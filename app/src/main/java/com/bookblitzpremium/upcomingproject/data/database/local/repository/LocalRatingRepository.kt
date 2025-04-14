package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.RatingDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalRatingRepository @Inject constructor(private val ratingDao: RatingDao) {
    val allRatings: Flow<List<Rating>> = ratingDao.getAllRatings()

    suspend fun addOrUpdateRating(rating: Rating) = ratingDao.upsertRating(rating)
    suspend fun deleteRating(rating: Rating) = ratingDao.deleteRating(rating)
    suspend fun getRatingById(id: String) = ratingDao.getRatingById(id)
}