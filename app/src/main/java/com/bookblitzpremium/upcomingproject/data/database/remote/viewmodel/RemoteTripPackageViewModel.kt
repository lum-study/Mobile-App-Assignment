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

    suspend fun getTripPackageIfNotLoaded(): List<TripPackage> {
        return try {
            _loading.value = true
            _error.value = null
            _packages.value = remoteTripPackageRepository.getAllTripPackages()
            _packages.value
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to load trip package"
            emptyList()
        } finally {
            _loading.value = false
        }
    }

    private fun getPackages() {
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

    fun addPackage(pkg: TripPackage): String {
        var id: String = ""
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                id = remoteTripPackageRepository.addTripPackage(pkg)
                _packages.value += pkg
            } catch (e: Exception) {
                _error.value = "Failed to add package: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
        return id
    }

    fun updatePackage(pkg: TripPackage) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteTripPackageRepository.updateTripPackage(pkg)
                _packages.value = _packages.value.map {
                    if (it.id == pkg.id) pkg else it
                }
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
                _packages.value = _packages.value.filter { it.id != id }
            } catch (e: Exception) {
                _error.value = "Failed to delete package: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    suspend fun getPackageByID(id: String): TripPackage? {
        return remoteTripPackageRepository.getTripPackageByID(id)
    }
}