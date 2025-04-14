package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule
import kotlinx.coroutines.flow.Flow

@Dao
interface ScheduleDao {
    @Upsert()
    suspend fun upsertSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule")
    fun getAllSchedules(): Flow<List<Schedule>>

    @Query("SELECT * FROM schedule WHERE id = :id")
    suspend fun getScheduleById(id: String): Schedule?
}