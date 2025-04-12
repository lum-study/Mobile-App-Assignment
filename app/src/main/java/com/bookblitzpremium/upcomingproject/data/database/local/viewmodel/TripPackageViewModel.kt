package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.bookblitzpremium.upcomingproject.data.database.local.repository.TripPackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TripPackageViewModel @Inject constructor(private val tripPackageRepository: TripPackageRepository) : ViewModel() {
    val tripList = tripPackageRepository.allTrips
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    fun addTrip(trip: TripPackage) {
        viewModelScope.launch {
            tripPackageRepository.insert(trip)
        }
    }


    fun deleteTrip(trip: TripPackage) {
        viewModelScope.launch {
            tripPackageRepository.delete(trip)
        }
    }
}
