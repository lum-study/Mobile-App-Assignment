package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteFlightRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteFlightViewModel @Inject constructor(private val remoteFlightRepository: RemoteFlightRepository) :
    ViewModel() {
    private val _flights = MutableStateFlow<List<Flight>>(emptyList())
    val flights: StateFlow<List<Flight>> = _flights.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        getFlights()
    }

    private fun getFlights() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _flights.value = remoteFlightRepository.getAllFlight()
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load flights"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addFlight(flight: Flight) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteFlightRepository.addFlight(flight)
                getFlights()
            } catch (e: Exception) {
                _error.value = "Failed to add flight: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateFlight(flight: Flight) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteFlightRepository.updateFlight(flight)
                getFlights()
            } catch (e: Exception) {
                _error.value = "Failed to update flight: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteFlight(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteFlightRepository.deleteFlight(id)
                getFlights()
            } catch (e: Exception) {
                _error.value = "Failed to delete flight: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}