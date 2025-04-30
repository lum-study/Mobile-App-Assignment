package com.bookblitzpremium.upcomingproject

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalFlightViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalPaymentViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRatingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalScheduleViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTPBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTripPackageViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteFlightViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteRatingViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteScheduleViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteTPBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteTripPackageViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.datastore.DataStoreManager
import com.bookblitzpremium.upcomingproject.data.datastore.DataStoreViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.delay

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

@Composable
fun InitializeTransaction(
    dataStoreManager: DataStoreManager = hiltViewModel<DataStoreViewModel>().manager,
) {
    val isTransactionUpdated by dataStoreManager.isTransactionUpdated.collectAsState(initial = null)
    val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""

    val remoteUserViewModel: RemoteUserViewModel = hiltViewModel()
    val remoteTPBookingViewModel: RemoteTPBookingViewModel = hiltViewModel()
    val remotePaymentViewModel: RemotePaymentViewModel = hiltViewModel()
    val remoteHotelBookingViewModel: RemoteHotelBookingViewModel = hiltViewModel()

    val localUserViewModel: LocalUserViewModel = hiltViewModel()
    val localTPBookingViewModel: LocalTPBookingViewModel = hiltViewModel()
    val localPaymentViewModel: LocalPaymentViewModel = hiltViewModel()
    val localHotelBookingViewModel: LocalHotelBookingViewModel = hiltViewModel()

    LaunchedEffect(userID, isTransactionUpdated) {
        if (isTransactionUpdated == false && userID.isNotEmpty()) {
            val user = remoteUserViewModel.getUsersIfNotLoaded(userID)

            if (user != null) {
                localUserViewModel.addOrUpdateUser(user)
            }

            val payment = remotePaymentViewModel.getPaymentsIfNotLoaded(userID)
            if (user != null) {
                payment.forEach { localPaymentViewModel.addOrUpdatePayment(it) }
            }

            val tripPackageBooking =
                remoteTPBookingViewModel.getTripPackageBookingIfNotLoaded(userID)
            if (user != null) {
                tripPackageBooking.forEach { localTPBookingViewModel.addOrUpdateTPBooking(it) }
            }

            val hotelBooking = remoteHotelBookingViewModel.getHotelBookingIfNotLoaded(userID)
            if (user != null) {
                hotelBooking.forEach { localHotelBookingViewModel.addOrUpdateHotelBooking(it) }
            }

            dataStoreManager.setTransactionUpdated(true)
        }
    }
}