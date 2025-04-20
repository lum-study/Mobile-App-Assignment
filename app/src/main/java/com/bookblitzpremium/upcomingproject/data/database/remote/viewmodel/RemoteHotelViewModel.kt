package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteHotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteHotelViewModel @Inject constructor(private val remoteHotelRepository: RemoteHotelRepository) :
    ViewModel() {
    private val _hotel = MutableStateFlow<List<Hotel>>(emptyList())
    val hotel: StateFlow<List<Hotel>> = _hotel.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun getHotelsIfNotLoaded(): List<Hotel> {
        return try {
            _loading.value = true
            _error.value = null
            _hotel.value = remoteHotelRepository.getAllHotel()
            _hotel.value
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to load hotels"
            emptyList()
        } finally {
            _loading.value = false
        }
    }

    private fun getHotels() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _hotel.value = remoteHotelRepository.getAllHotel()
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load hotels"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addHotel(hotel: Hotel): String {
        var id: String = ""
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                id = remoteHotelRepository.addHotel(hotel)
                _hotel.value += hotel
            } catch (e: Exception) {
                _error.value = "Failed to add hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
        return id
    }

    fun updateHotel(hotel: Hotel) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteHotelRepository.updateHotel(hotel)
                _hotel.value = _hotel.value.map {
                    if (it.id == hotel.id) hotel else it
                }
            } catch (e: Exception) {
                _error.value = "Failed to update hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteHotel(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteHotelRepository.deleteHotel(id)
                _hotel.value = _hotel.value.filter { it.id != id }
            } catch (e: Exception) {
                _error.value = "Failed to delete hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}