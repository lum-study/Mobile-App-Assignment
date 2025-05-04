package com.bookblitzpremium.upcomingproject.data.businessviewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.common.enums.BookingStatus
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelBookingRepo
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalRatingRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteHotelBookingRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteRatingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BusinessRatingViewModel @Inject constructor(
    private val localRatingRepository: LocalRatingRepository,
    private val remoteRatingRepository: RemoteRatingRepository,
    private val localHotelBookingRepository: LocalHotelBookingRepo,
    private val remoteHotelBookingRepository: RemoteHotelBookingRepository,
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _ratings = MutableStateFlow<List<Rating>>(emptyList())
    val ratings: StateFlow<List<Rating>> = _ratings.asStateFlow()

    fun addRatingToRemoteAndLocal(rating: Rating, bookingID: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val id = remoteRatingRepository.addRating(rating)
                if (id.isNotEmpty()) {
                    localRatingRepository.addOrUpdateRating(rating.copy(id = id))
                    val bookingList = remoteHotelBookingRepository.getHotelBookingByID(bookingID)
                    val booking = bookingList.firstOrNull()
                    println(bookingList)
                    if (booking != null) {
                        val updatedBooking = booking.copy(status = BookingStatus.Completed.title)
                        println(updatedBooking)
                        remoteHotelBookingRepository.updatePayment(updatedBooking)
                        localHotelBookingRepository.upsertHotelBooking(updatedBooking)
                    }
                }
            } catch (e: Exception) {
                _error.value = "Failed to add rating: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}