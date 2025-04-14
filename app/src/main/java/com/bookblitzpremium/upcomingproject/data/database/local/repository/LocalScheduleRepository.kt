package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.ScheduleDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalScheduleRepository @Inject constructor(private val scheduleDao: ScheduleDao) {
    val allSchedules: Flow<List<Schedule>> = scheduleDao.getAllSchedules()

    suspend fun addOrUpdateSchedule(schedule: Schedule) = scheduleDao.upsertSchedule(schedule)
    suspend fun deleteSchedule(schedule: Schedule) = scheduleDao.deleteSchedule(schedule)
    suspend fun getScheduleById(id: String) = scheduleDao.getScheduleById(id)
}