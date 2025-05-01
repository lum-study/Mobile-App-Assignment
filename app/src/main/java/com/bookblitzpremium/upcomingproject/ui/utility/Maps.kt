package com.bookblitzpremium.upcomingproject.ui.utility

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Locale

fun checkForPermission(context: Context): Boolean {
    return !(ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_FINE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
        context,
        Manifest.permission.ACCESS_COARSE_LOCATION
    ) != PackageManager.PERMISSION_GRANTED)
}


class MapViewModel : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _locationResult = MutableStateFlow<Triple<LatLng, LatLng, String?>?>(null)
    val locationResult: StateFlow<Triple<LatLng, LatLng, String?>?> = _locationResult.asStateFlow()

    @SuppressLint("MissingPermission")
    fun getLatLngFromAddress(
        context: Context,
        addressInput: String,
        addressFilter: (Address) -> String? = { it.getAddressLine(0) }
    ) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                _locationResult.value = null

                // Fetch current location
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
                val location = fusedLocationClient.lastLocation.await()
                if (location == null) {
                    Log.e("LOCATION-ERROR", "Current location is null")
                    _error.value = "Unable to fetch current location. Please ensure location services are enabled."
                    return@launch
                }
                val currentLatLng = LatLng(location.latitude, location.longitude)

                // Geocode destination address
                val geocoder = Geocoder(context, Locale.getDefault())
                val addresses = withContext(Dispatchers.IO) {
                    geocoder.getFromLocationName(addressInput, 1)
                }
                if (!addresses.isNullOrEmpty()) {
                    val address = addresses[0]
                    val destLatLng = LatLng(address.latitude, address.longitude)
                    val addressText = addressFilter(address)
                    _locationResult.value = Triple(currentLatLng, destLatLng, addressText)
                } else {
                    Log.e("GEOCODER-ERROR", "No location found for address: $addressInput")
                    _error.value = "No location found for address: $addressInput"
                }
            } catch (e: Exception) {
                Log.e("GEOCODER-ERROR", "Failed to process: ${e.message}")
                _error.value = "Error: ${e.message ?: "Unknown error occurred"}"
            } finally {
                _loading.value = false
            }
        }
    }
}
