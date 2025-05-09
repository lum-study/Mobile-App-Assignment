package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelBookingRepo
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteHotelBookingRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemotePaymentRepository
import com.google.firebase.firestore.FirebaseFirestoreException
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import javax.inject.Inject


@HiltViewModel
class RemoteHotelBookingViewModel @Inject constructor(
    private val remotePaymentRepo: RemotePaymentRepository,
    private val remoteHotelBookingRepository: RemoteHotelBookingRepository,
    private val localHotelBookingRepository: LocalHotelBookingRepo,
) : ViewModel() {

    private val _startDate = MutableStateFlow<String?>(null)
    val startDate: StateFlow<String?> = _startDate.asStateFlow()

    private val _endDate = MutableStateFlow<String?>(null)
    val endDate: StateFlow<String?> = _endDate.asStateFlow()

    fun updateStartDate(startDateCopy: String) {
        _startDate.value = startDateCopy
    }

    fun updateEndDate(endDateCopy: String) {
        _endDate.value = endDateCopy
    }


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

  
    fun addNewIntegratedRecord(hotelBooking: HotelBooking, payment : Payment) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            _success.value = false
            try {
                withTimeout(5000L){
                    remotePaymentRepo.updatePayment(payment)
                    val firestoreId = remoteHotelBookingRepository.addHotelBooking(hotelBooking)
                    val updatedBooking = hotelBooking.copy(id = firestoreId)
                    localHotelBookingRepository.insertHotelBooking(updatedBooking)
                    _success.value = true
                }
            } catch (e: TimeoutCancellationException) {
                _error.value = "Request timed out."
            } catch (e: FirebaseFirestoreException) {
                _error.value = "Firestore error: ${e.localizedMessage}"
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Unexpected error: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
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

    private val _success = MutableStateFlow(false)
    val success: StateFlow<Boolean> = _success.asStateFlow()

    fun updateHotelBooking(hotelBooking: HotelBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            _success.value = false // Reset success at the start
            try {
                remoteHotelBookingRepository.updateHotelBooking(hotelBooking)
                localHotelBookingRepository.upsertHotelBooking(hotelBooking)
                _success.value = true
                delay(2500L)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun clearSuccess() {
        _success.value = false
    }

    suspend fun getHotelBookingIfNotLoaded(userID: String): List<HotelBooking> {
        return try {
            _loading.value = true
            _error.value = null
            _hotel_booking.value = remoteHotelBookingRepository.getAllHotelBookingByUserID(userID)
            _hotel_booking.value
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to load hotel booking"
            emptyList()
        } finally {
            _loading.value = false
        }
    }
}