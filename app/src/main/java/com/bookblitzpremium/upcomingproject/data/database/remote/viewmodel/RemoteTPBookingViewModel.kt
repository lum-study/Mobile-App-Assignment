package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TPBooking
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteTPBookingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteTPBookingViewModel @Inject constructor(private val remoteTPBookingRepository: RemoteTPBookingRepository) :
    ViewModel() {
    private val _tpBookings = MutableStateFlow<List<TPBooking>>(emptyList())
    val tpBookings: StateFlow<List<TPBooking>> = _tpBookings.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun getTripPackageBookingIfNotLoaded(userID: String): List<TPBooking> {
        return try {
            _loading.value = true
            _error.value = null
            _tpBookings.value = remoteTPBookingRepository.getAllTripPackageBookingByUserID(userID)
            _tpBookings.value
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to load trip package booking"
            emptyList()
        } finally {
            _loading.value = false
        }
    }

    fun getTPBookings(userID: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _tpBookings.value =
                    remoteTPBookingRepository.getAllTripPackageBookingByUserID(userID)
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load tpBookings"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addTPBooking(tpBooking: TPBooking): String {
        var id: String = ""
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                id = remoteTPBookingRepository.addTripPackageBooking(tpBooking)
                _tpBookings.value += tpBooking
            } catch (e: Exception) {
                _error.value = "Failed to add tpBooking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
        return id
    }

    fun updateTPBooking(tpBooking: TPBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteTPBookingRepository.updateTripPackageBooking(tpBooking)
                _tpBookings.value = _tpBookings.value.map {
                    if (it.id == tpBooking.id) tpBooking else it
                }
            } catch (e: Exception) {
                _error.value = "Failed to update tpBooking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteTPBooking(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteTPBookingRepository.deleteTripPackageBooking(id)
                _tpBookings.value = _tpBookings.value.filter { it.id != id }
            } catch (e: Exception) {
                _error.value = "Failed to delete tpBooking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}