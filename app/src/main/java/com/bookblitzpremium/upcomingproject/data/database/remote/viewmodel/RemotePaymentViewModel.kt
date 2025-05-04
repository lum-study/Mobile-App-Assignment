package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalPaymentRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemotePaymentRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemotePaymentViewModel @Inject constructor(
    private val remotePaymentRepository: RemotePaymentRepository,
    private val localPaymentRepository: LocalPaymentRepository
) : ViewModel() {
    private val _payments = MutableStateFlow<List<Payment>>(emptyList())
    val payments: StateFlow<List<Payment>> = _payments.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun getPaymentsIfNotLoaded(userID: String): List<Payment> {
        return try {
            _loading.value = true
            _error.value = null
            _payments.value = remotePaymentRepository.getAllPaymentByUserID(userID)
            _payments.value
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to load payment"
            emptyList()
        } finally {
            _loading.value = false
        }
    }

    fun getPaymentsByUserID(userID: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                _payments.value = remotePaymentRepository.getAllPaymentByUserID(userID)
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load payments"
            } finally {
                _loading.value = false
            }
        }
    }

    suspend fun addReturnIDPayment(payment: Payment): String {
        _loading.value = true
        _error.value = null

        return try {
            val id = remotePaymentRepository.addPayment(payment)
            _payments.value += payment.copy(id = id) // include ID if needed
            id
        } catch (e: Exception) {
            _error.value = "Failed to add payment: ${e.localizedMessage}"
            ""
        } finally {
            _loading.value = false
        }
    }

    fun updatePayment(payment: Payment) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remotePaymentRepository.updatePayment(payment)
                _payments.value = _payments.value.map {
                    if (it.id == payment.id) payment else it
                }
            } catch (e: Exception) {
                _error.value = "Failed to update payment: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deletePayment(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remotePaymentRepository.deletePayment(id)
                _payments.value = _payments.value.filter { it.id != id }
            } catch (e: Exception) {
                _error.value = "Failed to delete payment: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun updatePaymentBoth(localPayment: Payment) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                remotePaymentRepository.updatePayment(localPayment)
                localPaymentRepository.addOrUpdatePayment(localPayment)
            } catch (e: Exception) {
                _error.value = "Failed to add payment: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}