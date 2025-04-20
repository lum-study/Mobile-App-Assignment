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

    suspend fun getFlightsIfNotLoaded(): List<Flight> {
        return try {
            _loading.value = true
            _error.value = null
            _flights.value = remoteFlightRepository.getAllFlight()
            flights.value
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to load flight"
            emptyList()
        } finally {
            _loading.value = false
        }
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

    fun addFlight(flight: Flight): String {
        var id: String = ""
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                id = remoteFlightRepository.addFlight(flight)
                _flights.value += flight
            } catch (e: Exception) {
                _error.value = "Failed to add flight: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
        return id
    }

    fun updateFlight(flight: Flight) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteFlightRepository.updateFlight(flight)
                _flights.value = _flights.value.map {
                    if (it.id == flight.id) flight else it
                }
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
                _flights.value = _flights.value.filter { it.id != id }
            } catch (e: Exception) {
                _error.value = "Failed to delete flight: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}