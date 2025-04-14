package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalHotelViewModel @Inject constructor(private val hotelRepository: LocalHotelRepository) :
    ViewModel() {
    val hotelList = hotelRepository.allHotels
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addOrUpdateHotel(hotel: Hotel) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                hotelRepository.addOrUpdateHotel(hotel)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteHotel(hotel: Hotel) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                hotelRepository.deleteHotel(hotel)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}