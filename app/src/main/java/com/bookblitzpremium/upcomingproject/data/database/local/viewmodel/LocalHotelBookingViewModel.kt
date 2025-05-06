package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelBookingRepo
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteHotelBookingRepository
import com.bookblitzpremium.upcomingproject.data.model.HotelBookingInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalHotelBookingViewModel @Inject constructor(
    private val localHotelBookingRepo: LocalHotelBookingRepo,
    private val remoteHotelBookingRepository: RemoteHotelBookingRepository,
    private val localHotel: LocalHotelRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _hotelBookings = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotelBookings: StateFlow<List<HotelBooking>> = _hotelBookings.asStateFlow()

    private val _hotelBookingHistory = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotelBookingHistory: StateFlow<List<HotelBooking>> = _hotelBookingHistory.asStateFlow()

    private val _hotelUserID = MutableStateFlow<List<HotelBooking>>(emptyList())
    val hotelUserID: StateFlow<List<HotelBooking>> = _hotelUserID.asStateFlow()

    init {
        viewModelScope.launch {
            localHotelBookingRepo.getAllHotelBookings().collect { bookings ->
                _hotelBookings.value = bookings
            }
        }
    }

    fun addOrUpdateHotelBooking(hotelBooking: HotelBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                localHotelBookingRepo.upsertHotelBooking(hotelBooking)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    private val _hotelsMap = MutableStateFlow<Map<String, Hotel>>(emptyMap())
    val hotelsMap: StateFlow<Map<String, Hotel>> = _hotelsMap.asStateFlow()

    fun fetchHotelBookingsByUserId(userId: String){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val bookings = localHotelBookingRepo.getHotelBookingsByBookingUserId(userId)
                _hotelUserID.value = bookings

                val hotelIds = bookings.map { it.hotelID }.distinct()
                val hotelMap = mutableMapOf<String, Hotel>()
                hotelIds.forEach { id ->
                    val hotel = localHotel.getHotelByID(id) // Make sure this is a suspend function
                    if (hotel != null) {
                        hotelMap[id] = hotel
                    }
                }
                _hotelsMap.value = hotelMap
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }


//    fun insertHotelBooking(hotelBooking: HotelBooking) {
//        viewModelScope.launch {
//            _loading.value = true
//            _error.value = null
//            try {
//                localHotelBookingRepo.insertHotelBooking(hotelBooking)
//                remoteHotelBookingRepository.updateHotelBooking(hotelBooking)
//            } catch (e: SQLiteException) {
//                _error.value = "Database error: ${e.localizedMessage}"
//            } catch (e: Exception) {
//                _error.value = "Error fetching hotel booking: ${e.localizedMessage}"
//            } finally {
//                _loading.value = false
//            }
//        }
//    }
//
    fun updateHotelBooking(booking: HotelBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                localHotelBookingRepo.upsertHotelBooking(booking)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchByHotelBookingID(bookingID:String){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {

            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }




    fun fetchHotelBookingsById(bookingID: String){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val bookings = localHotelBookingRepo.getHotelBookingsByBookingID(bookingID)
                _hotelBookingHistory.value = bookings
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching hotel booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getHotelBookingInformationByUserID(userID: String): Flow<PagingData<HotelBookingInformation>> {
        return localHotelBookingRepo.getHotelBookingByUserID(userID)
    }
}