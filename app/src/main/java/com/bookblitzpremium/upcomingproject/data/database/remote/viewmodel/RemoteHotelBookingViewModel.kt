package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteHotelBookingRepository
import com.google.firebase.database.Exclude
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RemoteHotelBookingViewModel @Inject constructor(
    private val remoteHotelBookingRepository: RemoteHotelBookingRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _hotel_booking = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotel_booking: StateFlow<List<HotelBooking>> = _hotel_booking.asStateFlow()

    suspend fun addHotelBooking(hotelBooking: HotelBooking): String {
        _loading.value = true
        _error.value = null
        return try {
            val id = remoteHotelBookingRepository.addHotelBooking(hotelBooking)
            val updatedHotelBooking = hotelBooking.copy(id = id)
            _hotel_booking.value += updatedHotelBooking
            id
        } catch (e: Exception) {
            _error.value = "Failed to add hotel booking: ${e.localizedMessage}"
            ""
        } finally {
            _loading.value = false
        }
    }

    private val _hotelBookings = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotelBookings: StateFlow<List<HotelBooking>> = _hotelBookings

    fun getAllHotelBooking() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val hotelBookings = remoteHotelBookingRepository.getAllHotelBooking()
                _hotelBookings.value = hotelBookings // Update your state here
            } catch (e: Exception) {
                _error.value = "Failed to get hotel bookings: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updatePayment(hotelBooking: HotelBooking) {
        viewModelScope.launch {
            try {
                remoteHotelBookingRepository.updatePayment(hotelBooking)
            } catch (e: Exception) {
                _error.value = "Failed to get hotel bookings: ${e.localizedMessage}"
            }
        }
    }


//    fun fetchHotelBookingsByUserId(userId: String) {
//        viewModelScope.launch {
//            _loading.value = true
//            _error.value = null
//            try {
//                val bookings = remoteHotelBookingRepository.getHotelBookingsByUserId(userId)
//                _hotel_booking.value = bookings
//            } catch (e: Exception) {
//                _error.value = "Failed to fetch hotel bookings: ${e.localizedMessage}"
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
}