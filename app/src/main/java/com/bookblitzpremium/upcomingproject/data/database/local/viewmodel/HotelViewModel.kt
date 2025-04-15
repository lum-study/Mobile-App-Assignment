package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotels
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Response
import com.bookblitzpremium.upcomingproject.data.database.local.repository.HotelListRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.HotelListResponse
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HotelViewModel @Inject constructor(
    private val repository: HotelListRepository
) : ViewModel() {

    private val _hotelState = MutableStateFlow<Response<List<Hotels>>>(Response.Idle)
    val hotelState: StateFlow<Response<List<Hotels>>> = _hotelState.asStateFlow()

    val db = Firebase.firestore

    init {
        fetchHotelList()
    }

    private fun fetchHotelList() {
        viewModelScope.launch {
            repository.getHotelList().collect {
                _hotelState.value = it
            }
        }
    }
}
