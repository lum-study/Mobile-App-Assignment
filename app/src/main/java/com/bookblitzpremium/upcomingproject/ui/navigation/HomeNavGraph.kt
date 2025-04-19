package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingAmount
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingDatePage
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ReviewFinalPackageSelected
import com.bookblitzpremium.upcomingproject.ui.screen.home.HomeScreen
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.OverlappingContentTest
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.FlightScreen
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.ScheduleScreen
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.TripPackageScreen

fun NavGraphBuilder.homeNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.Home.route,
        route = AppScreen.HomeGraph.route
    ) {
        composable(AppScreen.Home.route) {
            HomeScreen(navController)
        }
        composable(
            route = "${AppScreen.TripPackage.route}/{tripPackageID}",
            arguments = listOf(navArgument("tripPackageID") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""
            TripPackageScreen(navController = navController, tripPackageID = tripPackageID)
        }
        composable(
            route = "${AppScreen.Schedule.route}/{tripPackageID}/{startDate}",
            arguments = listOf(
                navArgument("tripPackageID") { type = NavType.StringType },
                navArgument("startDate") { type = NavType.StringType })
        ) { backStackEntry ->
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""
            val startDate = backStackEntry.arguments?.getString("startDate") ?: ""
            ScheduleScreen(tripPackageID = tripPackageID, startDate = startDate)
        }
        composable(
            route = "${AppScreen.Flight.route}/{flightID}/{bookingID}",
            arguments = listOf(
                navArgument("flightID") { type = NavType.StringType },
                navArgument("bookingID") { type = NavType.StringType })
        ) { backStackEntry ->
            val flightID = backStackEntry.arguments?.getString("flightID") ?: ""
            val bookingID = backStackEntry.arguments?.getString("bookingID") ?: ""
            FlightScreen(flightID = flightID, bookingID = bookingID)
        }
        composable(
            route = "${AppScreen.Hotel.route}/{hotelID}/{tripPackageID}"
        ) { backStackEntry ->
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""
            val hotelID = backStackEntry.arguments?.getString("hotelID") ?: ""
            OverlappingContentTest(2, navController)
        }
        composable(AppScreen.BookingDate.route) {
            BookingDatePage(modifier = Modifier, navController)
        }

        composable(AppScreen.BookingPeople.route) {
            BookingAmount(modifier = Modifier, navController)
        }

        composable(AppScreen.BookingReview.route) {
            ReviewFinalPackageSelected(modifier = Modifier, navController = navController)
        }

    }
}