package com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteScheduleRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RemoteScheduleViewModel @Inject constructor(private val remoteScheduleRepository: RemoteScheduleRepository) :
    ViewModel() {
    private val _schedule = MutableStateFlow<List<Schedule>>(emptyList())
    val schedule: StateFlow<List<Schedule>> = _schedule.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    suspend fun getScheduleIfNotLoaded(): List<Schedule> {
        return try {
            _loading.value = true
            _error.value = null
            _schedule.value = remoteScheduleRepository.getAllSchedule()
            _schedule.value
        } catch (e: Exception) {
            _error.value = e.localizedMessage ?: "Failed to load schedule"
            emptyList()
        } finally {
            _loading.value = false
        }
    }

    private fun getSchedules() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _schedule.value = remoteScheduleRepository.getAllSchedule()
            } catch (e: Exception) {
                _error.value = e.localizedMessage ?: "Failed to load schedules"
            } finally {
                _loading.value = false
            }
        }
    }

    fun addSchedule(schedule: Schedule): String {
        var id: String = ""
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                id = remoteScheduleRepository.addSchedule(schedule)
                _schedule.value += schedule
            } catch (e: Exception) {
                _error.value = "Failed to add schedule: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
        return id
    }

    fun updateSchedule(schedule: Schedule) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteScheduleRepository.updateSchedule(schedule)
                _schedule.value = _schedule.value.map {
                    if (it.id == schedule.id) schedule else it
                }
            } catch (e: Exception) {
                _error.value = "Failed to update schedule: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteSchedule(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                remoteScheduleRepository.deleteSchedule(id)
                _schedule.value = _schedule.value.filter { it.id != id }
            } catch (e: Exception) {
                _error.value = "Failed to delete schedule: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

}