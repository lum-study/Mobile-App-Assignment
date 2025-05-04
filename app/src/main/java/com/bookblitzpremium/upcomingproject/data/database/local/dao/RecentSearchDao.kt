package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.RecentSearch

@Dao
interface RecentSearchDao {
    @Upsert()
    suspend fun upsertRecentSearch(recentSearch: RecentSearch)

    @Delete
    suspend fun deleteRecentSearch(recentSearch: RecentSearch)

    @Query("SELECT * FROM recent_search ORDER BY id DESC LIMIT 1")
    suspend fun getAllRecentSearch(): RecentSearch

    @Query("DELETE FROM recent_search")
    suspend fun deleteAll()
}