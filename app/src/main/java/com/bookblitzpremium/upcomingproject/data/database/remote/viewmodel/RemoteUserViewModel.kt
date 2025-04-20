package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteUserViewModel @Inject constructor(private val remoteUserRepository: RemoteUserRepository) :
    ViewModel() {
    private val _users = MutableStateFlow<User?>(null)
    val users: StateFlow<User?> = _users.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun getUsersIfNotLoaded(userID: String): User? {
        return try {
            _loading.value = true
            _error.value = null
            _users.value = remoteUserRepository.getUserByID(userID)
            _users.value
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to load hotels"
            null
        } finally {
            _loading.value = false
        }
    }

    fun getUserByID(userID: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _users.value = remoteUserRepository.getUserByID(userID)
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load users"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addUser(user: User): String {
        var id: String = ""
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                id = remoteUserRepository.addUser(user)
            } catch (e: Exception) {
                _error.value = "Failed to add user: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
        return id
    }

    fun updateUser(user: User) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteUserRepository.updateUser(user)
            } catch (e: Exception) {
                _error.value = "Failed to update user: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteUser(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteUserRepository.deleteUser(id)
            } catch (e: Exception) {
                _error.value = "Failed to delete user: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}