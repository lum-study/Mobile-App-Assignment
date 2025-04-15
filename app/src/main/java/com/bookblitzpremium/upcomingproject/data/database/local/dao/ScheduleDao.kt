package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule

@Dao
interface ScheduleDao {
    @Upsert()
    suspend fun upsertSchedule(schedule: Schedule)

    @Delete
    suspend fun deleteSchedule(schedule: Schedule)

    @Query("SELECT * FROM schedule WHERE tripPackageID = :tripPackageID")
    fun getSchedulesByTripPackageID(tripPackageID: String): PagingSource<Int, Schedule>
}