package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalHotelViewModel @Inject constructor(private val hotelRepository: LocalHotelRepository) :
    ViewModel() {
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

    fun filterHotel(
        input: String,
        rating: Double,
        startPrice: Double,
        endPrice: Double,
        feature1: String,
        feature2: String
    ): Flow<PagingData<Hotel>> {
        return hotelRepository.getFilteredHotelsPagingFlow(
            input = input,
            rating = rating,
            startPrice = startPrice,
            endPrice = endPrice,
            feature1 = feature1,
            feature2 = feature2
        ).cachedIn(viewModelScope)
    }

    fun getAllHotelsPagingFlow(): Flow<PagingData<Hotel>> {
        return hotelRepository.getHotelsPagingFlow()
            .cachedIn(viewModelScope)
    }

    fun getByKeyword(keyword: String): Flow<PagingData<Hotel>>{
        return hotelRepository.getByKeyword(keyword)
            .cachedIn(viewModelScope)
    }

    private val _selectedHotel = MutableStateFlow<Hotel?>(null)
    val selectedHotel: StateFlow<Hotel?> = _selectedHotel.asStateFlow()

    fun getHotelByID(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val hotel = hotelRepository.getHotelByID(id)
                _selectedHotel.value = hotel
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}