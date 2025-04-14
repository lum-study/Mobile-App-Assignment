package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalFlightRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalTripPackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalTripPackageViewModel @Inject constructor(
    private val tripPackageRepository: LocalTripPackageRepository,
    private val flightRepository: LocalFlightRepository,
    private val hotelRepository: LocalHotelRepository
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addOrUpdateTrip(trip: TripPackage) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = runCatching {
                hotelRepository.getHotelByID(trip.hotelID)
                    ?: throw IllegalArgumentException("Invalid hotel ID")
                flightRepository.getFlightByID(trip.flightID)
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

    fun filterTripPackage(
        input: String,
        startPrice: Double = 0.0,
        endPrice: Double = 0.0,
        flightID: List<String>? = null,
        startDate: String = "",
        endDate: String = ""
    ): Flow<PagingData<TripPackage>> {
        return tripPackageRepository.getFilteredTripPackagesPagingFlow(
            input = input,
            startPrice = startPrice,
            endPrice = endPrice,
            flightID = flightID,
            startDate = startDate,
            endDate = endDate
        ).cachedIn(viewModelScope)
    }

    fun getAllTripPackagesPagingFlow(): Flow<PagingData<TripPackage>> {
        return tripPackageRepository.getTripPackagesPagingFlow()
            .cachedIn(viewModelScope)
    }

}