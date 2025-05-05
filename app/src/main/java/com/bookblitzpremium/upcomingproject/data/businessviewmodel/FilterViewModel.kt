package com.bookblitzpremium.upcomingproject.data.businessviewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.bookblitzpremium.upcomingproject.common.enums.BookingType
import com.bookblitzpremium.upcomingproject.common.enums.Feature
import com.bookblitzpremium.upcomingproject.common.enums.FlightStation
import com.bookblitzpremium.upcomingproject.common.enums.Rating
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.time.LocalDate
import javax.inject.Inject

data class FilterState(
    val selectedOption: BookingType = BookingType.Hotel,
    val startPrice: Float = 0f,
    val endPrice: Float = 1240f,
    val selectedRating: Rating = Rating.Rate1,
    val selectedFeature: SnapshotStateList<Feature> = mutableStateListOf(),
    val selectedDeparture: FlightStation = FlightStation.KualaLumpur,
    val selectedArrival: FlightStation = FlightStation.Sabah,
    val selectedStartDate: LocalDate = LocalDate.now(),
    val selectedEndDate: LocalDate = LocalDate.now().plusMonths(1),
    val showStartDatePicker: Boolean = false,
    val showEndDatePicker: Boolean = false,
    val isUpdated: Boolean = false,
)

@HiltViewModel
class FilterViewModel @Inject constructor(): ViewModel() {
    private val _filterState = MutableStateFlow(FilterState())
    val filterState: StateFlow<FilterState> = _filterState

    fun updateOption(option: BookingType){
        _filterState.value = _filterState.value.copy(selectedOption = option)
    }

    fun updateStartPrice(startPrice: Float){
        _filterState.value = _filterState.value.copy(startPrice = startPrice)
    }

    fun updateEndPrice(endPrice: Float){
        _filterState.value = _filterState.value.copy(endPrice = endPrice)
    }

    fun updateRating(rating: Rating){
        _filterState.value = _filterState.value.copy(selectedRating = rating)
    }

    fun updateFeature(feature: SnapshotStateList<Feature>){
        _filterState.value = _filterState.value.copy(selectedFeature = feature)
    }

    fun updateDeparture(departure: FlightStation){
        _filterState.value = _filterState.value.copy(selectedDeparture = departure)
    }

    fun updateArrival(arrival: FlightStation){
        _filterState.value = _filterState.value.copy(selectedArrival = arrival)
    }

    fun updateStartDate(date: LocalDate){
        _filterState.value = _filterState.value.copy(selectedStartDate = date)
    }

    fun updateEndDate(date: LocalDate){
        _filterState.value = _filterState.value.copy(selectedEndDate = date)
    }

    fun updateStartDatePicker(show: Boolean){
        _filterState.value = _filterState.value.copy(showStartDatePicker = show)
    }

    fun updateEndDatePicker(show: Boolean){
        _filterState.value = _filterState.value.copy(showEndDatePicker = show)
    }

    fun clearFilters() {
        _filterState.value = FilterState() // reset to default
    }

    fun updateState(updated: Boolean){
        _filterState.value = _filterState.value.copy(isUpdated = updated)
    }
}