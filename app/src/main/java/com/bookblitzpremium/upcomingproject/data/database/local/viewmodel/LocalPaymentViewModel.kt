package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalPaymentRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalUserRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemotePaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalPaymentViewModel @Inject constructor(
    private val paymentRepository: LocalPaymentRepository,
    private val remotePaymentRepo: RemotePaymentRepository,
    private val userRepository: LocalUserRepository
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun getPaymentByID(id: String): Payment? {
        return paymentRepository.getPaymentByID(id)
    }

    private val _paymentBooking = MutableStateFlow<List<Payment>>(emptyList())
    val paymentBooking: StateFlow<List<Payment>> = _paymentBooking.asStateFlow()

    fun getAllPayment(){
        viewModelScope.launch {
            paymentRepository.getAllPayment().collect { payment ->
                _paymentBooking.value = payment
            }
        }
    }

    fun addRoom(payment: Payment) {
        viewModelScope.launch {
            paymentRepository.insertRoom(payment)
        }
    }

    suspend fun getAllRooms(): List<Payment> {
        return paymentRepository.getAllRooms()
    }

    fun addOrUpdatePayment(payment: Payment) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = kotlin.runCatching {
//                userRepository.getUserByID(payment.userID)
//                    ?: throw IllegalArgumentException("Invalid user ID")
//                paymentRepository.addOrUpdatePayment(payment)
                remotePaymentRepo.updatePayment(payment)
            }
            result.onFailure { e ->
                _error.value = when (e) {
                    is IllegalArgumentException -> e.message
                    else -> "Failed to update payment"
                }
            }
            _loading.value = false
        }
    }

    fun addPayment(payment: Payment) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                paymentRepository.addOrUpdatePayment(payment)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching hotel: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deletePayment(payment: Payment) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                paymentRepository.deletePayment(payment)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting payment: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}