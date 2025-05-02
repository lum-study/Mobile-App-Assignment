package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalRatingRepository
import com.bookblitzpremium.upcomingproject.data.model.RatingRecord
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalRatingViewModel @Inject constructor(
    private val ratingRepository: LocalRatingRepository,
    private val hotelRepository: LocalHotelRepository,
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addOrUpdateRating(rating: Rating) {

        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
//                hotelRepository.getHotelByID(rating.hotelID)
//                    ?: throw IllegalArgumentException("Invalid hotel ID")

                ratingRepository.addOrUpdateRating(rating)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating rating: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteRating(rating: Rating) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                ratingRepository.deleteRating(rating)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting rating: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    private val _ratings = MutableStateFlow<List<Rating>>(emptyList())
    val ratings: StateFlow<List<Rating>> = _ratings.asStateFlow()

    fun getRatingByHotelId(hotelId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val ratingList = ratingRepository.getRatingByHotelId(hotelId)
                _ratings.value = ratingList
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching ratings: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    suspend fun getRatingByHotel(userID: String): List<Rating> {
        return ratingRepository.getRatingByHotelId(userID)
    }

    suspend fun getRatingByUserID(userID: String): List<RatingRecord> {
        return ratingRepository.getRatingByUserID(userID)
    }

    fun getAllRatingsFlow(): Flow<List<Rating>> {
        return ratingRepository.getAllRatingsFlow()
    }
}