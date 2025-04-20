package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TPBooking
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalPaymentRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalTPBookingRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalUserRepository
import com.bookblitzpremium.upcomingproject.data.model.TPBookingInformation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalTPBookingViewModel @Inject constructor(
    private val tpBookingRepository: LocalTPBookingRepository,
    private val paymentRepository: LocalPaymentRepository,
    private val userRepository: LocalUserRepository
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun getTPBookingByUserID(userID: String): Flow<PagingData<TPBookingInformation>> {
        return tpBookingRepository.getTPBookingByUserID(userID)
            .cachedIn(viewModelScope)
    }

    fun addOrUpdateTPBooking(tpBooking: TPBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = runCatching {
                paymentRepository.getPaymentByID(tpBooking.paymentID)
                    ?: throw IllegalArgumentException("Invalid payment ID")
                userRepository.getUserByID(tpBooking.userID)
                    ?: throw IllegalArgumentException("Invalid user ID")

                tpBookingRepository.addOrUpdateTPBooking(tpBooking)
            }
            result.onFailure { e ->
                _error.value = when (e) {
                    is IllegalArgumentException -> e.message
                    else -> "Failed to update trip package booking"
                }
            }
            _loading.value = false
        }
    }

    fun deleteTPBooking(tpBooking: TPBooking) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                tpBookingRepository.deleteTPBooking(tpBooking)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting trip package booking: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}