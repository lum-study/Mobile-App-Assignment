package com.bookblitzpremium.upcomingproject.data.database.local.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bookblitzpremium.upcomingproject.data.database.local.dao.ScheduleDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalScheduleRepository @Inject constructor(private val scheduleDao: ScheduleDao) {
    fun getSchedulesPagingFlowByTripPackage(tripPackageID: String): Flow<PagingData<Schedule>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { scheduleDao.getSchedulesByTripPackageID(tripPackageID) }
        ).flow
    }

    suspend fun addOrUpdateSchedule(schedule: Schedule) = scheduleDao.upsertSchedule(schedule)
    suspend fun deleteSchedule(schedule: Schedule) = scheduleDao.deleteSchedule(schedule)
}