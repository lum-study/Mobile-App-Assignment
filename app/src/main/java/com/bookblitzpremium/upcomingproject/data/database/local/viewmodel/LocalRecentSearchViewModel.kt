package com.bookblitzpremium.upcomingproject.data.database.local.viewmodel

import android.database.sqlite.SQLiteException
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.entity.RecentSearch
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalRecentSearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocalRecentSearchViewModel @Inject constructor(private val recentSearchRepository: LocalRecentSearchRepository) :
    ViewModel() {
    private val _recentSearch = MutableStateFlow<RecentSearch?>(null)
    val recentSearch: StateFlow<RecentSearch?> = _recentSearch.asStateFlow()

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        getRecentSearch()
    }

    fun addOrUpdateRecentSearch(recentSearch: RecentSearch) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                recentSearchRepository.addOrUpdateRecentSearch(recentSearch)
                getRecentSearch()
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error updating recentSearch: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteRecentSearch(recentSearch: RecentSearch) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                recentSearchRepository.deleteRecentSearch(recentSearch)
                getRecentSearch()
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting recentSearch: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    private fun getRecentSearch(){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                _recentSearch.value = recentSearchRepository.getRecentSearch()
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting recentSearch: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }

    fun deleteAll(){
        viewModelScope.launch {
            _loading.value = true
            _error.value = null

            try {
                recentSearchRepository.deleteAll()
            } catch (e: SQLiteException) {
                _error.value = "Database error: ${e.localizedMessage}"
            } catch (e: Exception) {
                _error.value = "Error deleting recentSearch: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}