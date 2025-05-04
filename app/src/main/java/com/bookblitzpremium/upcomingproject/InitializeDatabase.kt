package com.bookblitzpremium.upcomingproject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalHotelBookingRepo
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalPaymentRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalTPBookingRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.LocalUserRepository
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalFlightViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRatingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalScheduleViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTripPackageViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteHotelBookingRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemotePaymentRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteTPBookingRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.repository.RemoteUserRepository
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteFlightViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteRatingViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteScheduleViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteTripPackageViewModel
import com.bookblitzpremium.upcomingproject.data.datastore.DataStoreManager
import com.bookblitzpremium.upcomingproject.data.datastore.DataStoreViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@Composable
fun InitializeDatabase(
    dataStoreManager: DataStoreManager = hiltViewModel<DataStoreViewModel>().manager,
) {
    val isFirstLaunch by dataStoreManager.isFirstLaunch.collectAsState(initial = null)

    val remoteHotelViewModel: RemoteHotelViewModel = hiltViewModel()
    val remoteFlightViewModel: RemoteFlightViewModel = hiltViewModel()
    val remoteRatingViewModel: RemoteRatingViewModel = hiltViewModel()
    val remoteTripPackageViewModel: RemoteTripPackageViewModel = hiltViewModel()
    val remoteScheduleViewModel: RemoteScheduleViewModel = hiltViewModel()

    val localHotelViewModel: LocalHotelViewModel = hiltViewModel()
    val localFlightViewModel: LocalFlightViewModel = hiltViewModel()
    val localRatingViewModel: LocalRatingViewModel = hiltViewModel()
    val localTripPackageViewModel: LocalTripPackageViewModel = hiltViewModel()
    val localScheduleViewModel: LocalScheduleViewModel = hiltViewModel()

    LaunchedEffect(isFirstLaunch) {
        if (isFirstLaunch == true) {
            // Hotel
            val hotels = remoteHotelViewModel.getHotelsIfNotLoaded()
            if (hotels.isNotEmpty()) {
                hotels.forEach { localHotelViewModel.addOrUpdateHotel(it) }
                delay(3000)
            }

            // Flight
            val flights = remoteFlightViewModel.getFlightsIfNotLoaded()
            if (flights.isNotEmpty()) {
                flights.forEach { localFlightViewModel.addOrUpdateFlight(it) }
                delay(3000)
            }

            // Trip Package
            val trips = remoteTripPackageViewModel.getTripPackageIfNotLoaded()
            if (trips.isNotEmpty()) {
                trips.forEach { localTripPackageViewModel.addOrUpdateTrip(it) }
                delay(3000)
            }

            // Schedule
            val schedules = remoteScheduleViewModel.getScheduleIfNotLoaded()
            if (schedules.isNotEmpty()) {
                schedules.forEach { localScheduleViewModel.addOrUpdateSchedule(it) }
                delay(3000)
            }

            // Rating
            val ratings = remoteRatingViewModel.getRatingsIfNotLoaded()
            if (ratings.isNotEmpty()) {
                ratings.forEach { localRatingViewModel.addOrUpdateRating(it) }
            }

            dataStoreManager.setFirstLaunch(false)
        }
    }
}

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val dataStoreManager: DataStoreManager,
    private val remoteUserRepository: RemoteUserRepository,
    private val remoteTPBookingRepository: RemoteTPBookingRepository,
    private val remotePaymentRepository: RemotePaymentRepository,
    private val remoteHotelBookingRepository: RemoteHotelBookingRepository,
    private val localUserRepository: LocalUserRepository,
    private val localTPBookingRepository: LocalTPBookingRepository,
    private val localPaymentRepository: LocalPaymentRepository,
    private val localHotelBookingRepository: LocalHotelBookingRepo,
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun initializeTransaction(userID: String) {
        viewModelScope.launch {
            try {
                _loading.value = true
                _error.value = null
                val isTransactionUpdated = dataStoreManager.isTransactionUpdated.first()
                if (!isTransactionUpdated && userID.isNotEmpty()) {
                    val user = remoteUserRepository.getUserByID(userID)
                    user?.let { localUserRepository.addOrUpdateUser(it) }

                    remotePaymentRepository.getAllPaymentByUserID(userID).forEach {
                        localPaymentRepository.addOrUpdatePayment(it)
                    }
                    remoteTPBookingRepository.getAllTripPackageBookingByUserID(userID).forEach {
                        localTPBookingRepository.addOrUpdateTPBooking(it)
                    }
                    remoteHotelBookingRepository.getAllHotelBookingByUserID(userID).forEach {
                        localHotelBookingRepository.upsertHotelBooking(it)
                    }
                    dataStoreManager.setTransactionUpdated(true)
                }
            } catch (e: Exception) {
                _error.value = "Transaction Failed: ${e.localizedMessage}"
            } finally {
                _loading.value = false
            }
        }
    }
}
