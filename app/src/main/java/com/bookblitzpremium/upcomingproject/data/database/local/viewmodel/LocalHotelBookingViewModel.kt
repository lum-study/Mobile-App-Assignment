package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelBookingRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalHotelBookingViewModel @Inject constructor(
    private val repo: LocalHotelBookingRepo
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _hotelBookings = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotelBookings: StateFlow<List<HotelBooking>> = _hotelBookings.asStateFlow()

    private val _hotelBooking = MutableStateFlow<HotelBooking?>(null)
    val hotelBooking: StateFlow<HotelBooking?> = _hotelBooking.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAllHotelBookings().collect { bookings ->
                _hotelBookings.value = bookings
            }
        }
    }

    fun fetchHotelBookingById(bookingId: Long) {
        viewModelScope.launch {
            val booking = repo.getHotelBookingById(bookingId)
            _hotelBooking.value = booking
        }
    }

    fun insertHotelBooking(hotelBooking: HotelBooking) {
        viewModelScope.launch {
            repo.insertHotelBooking(hotelBooking)
        }
    }
    
}