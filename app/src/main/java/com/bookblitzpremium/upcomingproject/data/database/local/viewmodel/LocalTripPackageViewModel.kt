package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalFlightRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalTripPackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalTripPackageViewModel @Inject constructor(
    private val tripPackageRepository: LocalTripPackageRepository,
    private val flightRepository: LocalFlightRepository,
    private val hotelRepository: LocalHotelRepository
) : ViewModel() {
    val tripList = tripPackageRepository.allTrips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addOrUpdateTrip(trip: TripPackage) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = runCatching {
                hotelRepository.getHotelById(trip.hotelID)
                    ?: throw IllegalArgumentException("Invalid hotel ID")
                flightRepository.getFlightById(trip.flightID)
                    ?: throw IllegalArgumentException("Invalid flight ID")

                tripPackageRepository.addOrUpdateTripPackage(trip)
            }
            result.onFailure { e ->
                _error.value = when (e) {
                    is IllegalArgumentException -> e.message
                    else -> "Failed to update trip package"
                }
            }
            _loading.value = false
        }
    }

    fun deleteTrip(trip: TripPackage) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                tripPackageRepository.deleteTripPackage(trip)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting trip: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}
