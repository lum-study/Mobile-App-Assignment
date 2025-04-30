package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.compose.runtime.mutableStateOf
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

    private val _success = MutableStateFlow<String?>(null)
    val success: StateFlow<String?> = _success.asStateFlow()

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

    private val _updateGender = MutableStateFlow<String?>(null)
    val updateGender: StateFlow<String?> = _updateGender.asStateFlow()

    fun updateRoomCount(id: String, gender: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                userRepository.updateUserGender(id, gender)
                _updateGender.value = updateGender.toString()
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting user: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
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


    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    fun selectAllUser() {
        viewModelScope.launch {
//            _loading.value = true
            _error.value = null
            try {
                // Collect the Flow<List<User>> from the repository
                userRepository.selectAllUser().collect { userList ->
                    _users.value = userList
                }
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching users: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    private val _emailExists = MutableStateFlow<Boolean?>(null)
    val emailExists: StateFlow<Boolean?> = _emailExists.asStateFlow()

    suspend fun checkUserEmail(email: String): Boolean {
        _loading.value = true
        _error.value = null
        _emailExists.value = null
        return try {
            val exists = userRepository.findUserEmail(email)
            _emailExists.value = exists
        } catch (e: SQLiteException) {
            _error.value = "Database error: ${e.localizedMessage}"
            _emailExists.value = false
            false
        } catch (e: Exception) {
            _error.value = "Error fetching users: ${e.localizedMessage}"
            _emailExists.value = false
            false
        } finally {
            _loading.value = false
        } == true
    }

    fun loginLocalUser(email: String, password: String, onResult: (String?, String?) -> Unit) {
        viewModelScope.launch {
            val result = userRepository.validateUser(email, password)
            result
                .onSuccess { uid ->
                    onResult(uid, null) // login success
                }
                .onFailure { exception ->
                    onResult(null, exception.message) // show error
                }
        }
    }
}