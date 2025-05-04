package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.RecentSearchDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.RecentSearch
import javax.inject.Inject

class LocalRecentSearchRepository @Inject constructor(private val recentSearchDao: RecentSearchDao) {
    suspend fun addOrUpdateRecentSearch(recentSearch: RecentSearch) = recentSearchDao.upsertRecentSearch(recentSearch)
    suspend fun deleteRecentSearch(recentSearch: RecentSearch) = recentSearchDao.deleteRecentSearch(recentSearch)
    suspend fun getRecentSearch() = recentSearchDao.getAllRecentSearch()
    suspend fun deleteAll() = recentSearchDao.deleteAll()
}