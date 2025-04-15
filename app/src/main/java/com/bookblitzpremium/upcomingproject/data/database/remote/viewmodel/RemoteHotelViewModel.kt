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

    init {
        fetchHotels()
    }

    private fun fetchHotels() {
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

    fun addHotel(hotel: Hotel) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteHotelRepository.addHotel(hotel)
                fetchHotels()
            } catch (e: Exception) {
                _error.value = "Failed to add hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updateHotel(hotel: Hotel) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteHotelRepository.updateHotel(hotel)
                fetchHotels()
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
                fetchHotels()
            } catch (e: Exception) {
                _error.value = "Failed to delete hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun filteredHotel(
        input: String,
        rating: Double = 0.0,
        startPrice: Double = 0.0,
        endPrice: Double = 0.0,
        feature1: String = "",
        feature2: String = ""
    ): List<Hotel> {
        return _hotel.value.filter { hotel ->
            val matchesText = input.isEmpty() ||
                    hotel.name.contains(input, ignoreCase = true) ||
                    hotel.address.contains(input, ignoreCase = true)

            val matchesRating = rating == 0.0 || hotel.rating >= rating

            val matchesPrice = hotel.price in startPrice..endPrice

            val matchesFeature1 = feature1.isEmpty() ||
                    hotel.feature.contains(feature1)
            val matchesFeature2 = feature2.isEmpty() ||
                    hotel.feature.contains(feature2)

            matchesText && matchesRating && matchesPrice && matchesFeature1 && matchesFeature2
        }.sortedWith(
            compareBy(
                { it.price },
                { if (rating != 0.0) it.rating else null },
                { it.name }
            )
        )
    }
}