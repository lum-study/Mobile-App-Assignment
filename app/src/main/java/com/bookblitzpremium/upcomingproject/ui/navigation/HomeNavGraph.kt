package com.bookblitzpremium.upcomingproject.ui.navigation

import android.util.Log
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingAmount
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingDatePage
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ReviewFinalPackageSelected
import com.bookblitzpremium.upcomingproject.HomeScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.OverlappingContentTest
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.FlightScreen
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.ScheduleScreen
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.TripPackageScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController, userModel: AuthViewModel) {
    navigation(
        startDestination = AppScreen.Home.route,
        route = AppScreen.HomeGraph.route
    ) {
        composable(AppScreen.Home.route) {
            Log.d("Navigation", "Navigating to HomeGraph")
            HomeScreen(onLogout = { userModel.signOut()},userModel)
        }
        composable(AppScreen.TripPackage.route) {
            TripPackageScreen(navController)
        }
        composable(AppScreen.Schedule.route) {
            ScheduleScreen(navController)
        }
        composable(AppScreen.Flight.route) {
            FlightScreen(navController)
        }
        composable(AppScreen.Hotel.route) {
            OverlappingContentTest(2, navController)
        }
        composable(AppScreen.BookingDate.route) {
            BookingDatePage(modifier = Modifier, navController)
        }

        composable(AppScreen.BookingPeople.route) {
            BookingAmount(modifier = Modifier,navController)
        }

        composable(AppScreen.BookingReview.route) {
            ReviewFinalPackageSelected(modifier = Modifier, navController = navController)
        }

    }
}