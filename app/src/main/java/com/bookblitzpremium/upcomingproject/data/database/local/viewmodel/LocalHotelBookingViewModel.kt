package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelBookingRepo
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteHotelBookingRepository
import com.bookblitzpremium.upcomingproject.data.model.HotelBookingInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalHotelBookingViewModel @Inject constructor(
    private val repo: LocalHotelBookingRepo,
    private val remoteRepo: RemoteHotelBookingRepository,
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _hotelBookings = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotelBookings: StateFlow<List<HotelBooking>> = _hotelBookings.asStateFlow()

    private val _hotelBookingHistory = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotelBookingHistory: StateFlow<List<HotelBooking>> = _hotelBookingHistory.asStateFlow()

    private val _hotelUserID = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotelUserID: StateFlow<List<HotelBooking>> = _hotelUserID.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAllHotelBookings().collect { bookings ->
                _hotelBookings.value = bookings
            }
        }
    }

    fun addOrUpdateHotelBooking(hotelBooking: HotelBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                repo.upsertHotelBooking(hotelBooking)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }


    fun fetchHotelBookingsByUserId(userId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val bookings = repo.getHotelBookingsByBookingUserId(userId)
                _hotelUserID.value =
                    bookings // Make sure _hotelBookings is a StateFlow<List<HotelBooking>>
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun insertHotelBooking(hotelBooking: HotelBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                repo.insertHotelBooking(hotelBooking)
                remoteRepo.updatePayment(hotelBooking)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }


    fun updateHotelBooking(hotelBooking: HotelBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                repo.updateHotelBooking(hotelBooking) // Use update instead of insert
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }


    fun fetchHotelBookingsById(hotelId: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val bookings = repo.getHotelBookingsByHotelId(hotelId)
                _hotelBookingHistory.value =
                    bookings // Make sure _hotelBookings is a StateFlow<List<HotelBooking>>
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getHotelBookingInformationByUserID(userID: String): Flow<PagingData<HotelBookingInformation>> {
        return repo.getHotelBookingByUserID(userID)
    }
}