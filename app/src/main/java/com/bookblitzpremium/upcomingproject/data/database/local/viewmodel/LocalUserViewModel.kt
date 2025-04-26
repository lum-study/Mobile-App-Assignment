package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalUserRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.PasswordVerificationState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.mindrot.jbcrypt.BCrypt
import javax.inject.Inject

@HiltViewModel
class LocalUserViewModel @Inject constructor(
    private val repo: LocalUserRepository,
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val localLoading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val localError: StateFlow<String?> = _error.asStateFlow()

    private val _success = MutableStateFlow<String?>(null)
    val success: StateFlow<String?> = _success.asStateFlow()

    fun insertNewUser(user: User) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                repo.insertUsers(user)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error fetching hotel: ${e.localizedMessage}"
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
                repo.selectAllUser().collect { userList ->
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
            val exists = repo.findUserEmail(email)
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
            val result = repo.validateUser(email, password)
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