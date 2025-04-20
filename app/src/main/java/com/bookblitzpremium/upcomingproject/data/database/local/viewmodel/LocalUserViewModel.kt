package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalUserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalUserViewModel @Inject constructor(
    private val repo: LocalUserRepository
) : ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _success = MutableStateFlow<String?>(null)
    val success: StateFlow<String?> = _success.asStateFlow()

    fun checkAndInsertUsers(users: List<User>) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            _success.value = null

            val errors = mutableListOf<String>()
            val insertedCount = mutableListOf<User>()
            try {
                for (user in users) {
                    val email = user.email ?: continue

                    val existingEmail = repo.findUserEmail(email)
                    if (existingEmail != null) {
                        errors.add("Email already registered: $email")
                        continue
                    }

                    repo.insertUsers(user)
                    insertedCount.add(user)
                }
                if (errors.isNotEmpty()) {
                    _error.value = errors.joinToString("\n")
                    delay(3000)
                    _error.value = null
                }
                if (insertedCount.isNotEmpty()) {
                    _success.value = "Successfully inserted ${insertedCount.size} users"
                    delay(3000)
                    _success.value = null
                }
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
                delay(3000)
                _error.value = null
            } catch (e: Exception) {
                _error.value = "Unexpected error: ${e.localizedMessage}"
                delay(3000)
                _error.value = null
            } finally {
                _loading.value = false
            }
        }
    }

    fun selectAllUser(): Flow<List<User>> = repo.selectAllUser()
}