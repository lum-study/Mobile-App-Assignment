package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalUserRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


//remote
//local

@HiltViewModel
class RemoteUserViewModel @Inject constructor(
    private val remoteUserRepository: RemoteUserRepository,
    private val localUserRepo: LocalUserRepository,
) : ViewModel() {

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

    fun updateUser(user: User) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteUserRepository.updateUser(user)
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to update user"
            } finally {
                _loading.value = false
            }
        }
    }

    suspend fun checkEmails(email: String): String {
        _loading.value = true
        _error.value = null
        return try {
            remoteUserRepository.checkEmails(email)
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to check email"
            throw e
        } finally {
            _loading.value = false
        }
    }


    suspend fun addUser(id: String, user: User) {
        _loading.value = true
        _error.value = null
        try {
            val newId = remoteUserRepository.validateEmail(id, user)
            localUserRepo.addOrUpdateUser(user.copy(id = newId))
        } catch (e: Exception) {
            _error.value = "Failed to add user: ${e.message}"
        } finally {
            _loading.value = false
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

    private val _selectedGender = MutableStateFlow<String?>(null)
    val selectedGender: StateFlow<String?> = _selectedGender.asStateFlow()

    fun selectGender(gender: String) {
        _selectedGender.value = gender
        _error.value = null // Clear any previous error
    }

    suspend fun updateUserGender(userId: String, gender: String) {
        _loading.value = true
        _error.value = null
        try {
            val updatedUser = remoteUserRepository.updateUserGender(userId, gender)
                ?: throw IllegalStateException("User not found")
            localUserRepo.addOrUpdateUser(updatedUser)
        } catch (e: Exception) {
            _error.value = "Failed to update gender: ${e.localizedMessage}"
            throw e
        } finally {
            _loading.value = false
        }
    }

    fun setError(message: String?) {
        _error.value = message
    }

}