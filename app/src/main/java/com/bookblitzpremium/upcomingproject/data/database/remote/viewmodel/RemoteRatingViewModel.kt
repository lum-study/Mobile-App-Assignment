package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteRatingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteRatingViewModel @Inject constructor(private val remoteRatingRepository: RemoteRatingRepository) :
    ViewModel() {
    private val _rating = MutableStateFlow<List<Rating>>(emptyList())
    val rating: StateFlow<List<Rating>> = _rating.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchRatings()
    }

    private fun fetchRatings() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _rating.value = remoteRatingRepository.getAllRating()
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load ratings"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addRating(rating: Rating) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteRatingRepository.addRating(rating)
                fetchRatings()
            } catch (e: Exception) {
                _error.value = "Failed to add rating: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateRating(rating: Rating) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteRatingRepository.updateRating(rating)
                fetchRatings()
            } catch (e: Exception) {
                _error.value = "Failed to update rating: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteRating(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteRatingRepository.deleteRating(id)
                fetchRatings()
            } catch (e: Exception) {
                _error.value = "Failed to delete rating: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}