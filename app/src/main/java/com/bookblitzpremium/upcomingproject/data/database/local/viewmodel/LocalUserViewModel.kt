package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalUserViewModel @Inject constructor(
    private val userRepository: LocalUserRepository
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun getUserByID(id: String): User? {
        return userRepository.getUserByID(id)
    }

    fun addOrUpdateUser(user: User) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = kotlin.runCatching {
                userRepository.addOrUpdateUser(user)
            }
            result.onFailure { e ->
                _error.value = when (e) {
                    is IllegalArgumentException -> e.message
                    else -> "Failed to update user"
                }
            }
            _loading.value = false
        }
    }

    fun deleteUser(user: User) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                userRepository.deleteUser(user)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting user: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}