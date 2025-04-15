package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteTripPackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteTripPackageViewModel @Inject constructor(private val remoteTripPackageRepository: RemoteTripPackageRepository) :
    ViewModel() {
    private val _packages = MutableStateFlow<List<TripPackage>>(emptyList())
    val packages: StateFlow<List<TripPackage>> = _packages.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchPackages()
    }

    private fun fetchPackages() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _packages.value = remoteTripPackageRepository.getAllTripPackages()
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load packages"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addPackage(pkg: TripPackage) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteTripPackageRepository.addTripPackage(pkg)
                fetchPackages()
            } catch (e: Exception) {
                _error.value = "Failed to add package: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updatePackage(pkg: TripPackage) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteTripPackageRepository.updateTripPackage(pkg)
                fetchPackages()
            } catch (e: Exception) {
                _error.value = "Failed to update package: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deletePackage(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteTripPackageRepository.deleteTripPackage(id)
                fetchPackages()
            } catch (e: Exception) {
                _error.value = "Failed to delete package: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun filteredPackage(
        input: String,
        startPrice: Double = 0.0,
        endPrice: Double = 0.0,
        flightID: String = "",
        startDate: String = "",
        endDate: String = ""
    ): List<TripPackage> {
        return _packages.value.filter { tripPackage ->
            val matchesText = input.isEmpty() ||
                    tripPackage.name.contains(input, ignoreCase = true) ||
                    tripPackage.description.contains(input, ignoreCase = true) ||
                    tripPackage.location.contains(input, ignoreCase = true)

            val matchesPrice = tripPackage.price in startPrice..endPrice

            val matchDate = tripPackage.startDate in startDate..endDate

            matchesText && matchesPrice && matchDate
        }.sortedWith(
            compareBy(
                { it.price },
                { if (startDate != "") it.startDate else null },
                { it.name }
            )
        )
    }
}