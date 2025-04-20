package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalFlightRepository
import com.bookblitzpremium.upcomingproject.data.model.FlightInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalFlightViewModel @Inject constructor(private val flightRepository: LocalFlightRepository) :
    ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getAllFlightPagingFlow(): Flow<PagingData<Flight>> {
        return flightRepository.getAllFlights()
            .cachedIn(viewModelScope)
    }

    fun addOrUpdateFlight(flight: Flight) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                flightRepository.addOrUpdateFlight(flight)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating flight: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteFlight(flight: Flight) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                flightRepository.deleteFlight(flight)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting flight: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    suspend fun getFlightByID(id: String): Flight? {
        return flightRepository.getFlightByID(id)
    }

    suspend fun getFlightInformationByID(id: String): FlightInformation? {
        return flightRepository.getFlightInformationByID(id)
    }

    suspend fun getFlightIDByPlace(
        arrivalState: String, departState: String
    ): List<String> {
        return flightRepository.getFlightIDByPlace(
            arrivalState = arrivalState, departState = departState
        )
    }
}