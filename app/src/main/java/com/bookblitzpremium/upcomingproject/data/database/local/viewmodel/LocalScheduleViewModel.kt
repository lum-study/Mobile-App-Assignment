package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalScheduleRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalTripPackageRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalScheduleViewModel @Inject constructor(
    private val scheduleRepository: LocalScheduleRepository,
    private val tripPackageRepository: LocalTripPackageRepository
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun addOrUpdateSchedule(schedule: Schedule) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            val result = runCatching {
                tripPackageRepository.getTripByID(schedule.tripPackageID)
                    ?: throw IllegalArgumentException("Invalid trip package ID")

                scheduleRepository.addOrUpdateSchedule(schedule)
            }
            result.onFailure { e ->
                _error.value = when (e) {
                    is IllegalArgumentException -> e.message
                    else -> "Failed to update schedule"
                }
            }
            _loading.value = false
        }
    }

    fun deleteSchedule(schedule: Schedule) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                scheduleRepository.deleteSchedule(schedule)
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting schedule: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun getScheduleByTripPackage(tripPackageID: String): Flow<PagingData<Schedule>> {
        return scheduleRepository.getSchedulesPagingFlowByTripPackage(tripPackageID)
            .cachedIn(viewModelScope)
    }

}