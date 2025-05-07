package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bookblitzpremium.upcomingproject.HandleRotateState
import com.bookblitzpremium.upcomingproject.HotelBookingScreenLayout
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.booking.HotelBookingListScreen
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ModifyHotelBooking
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ReviewFinalPackageSelected
import com.bookblitzpremium.upcomingproject.ui.screen.booking.TripPackageBookingScreen
import com.bookblitzpremium.upcomingproject.ui.screen.home.HomeScreen
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.FlightScreen
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.ScheduleScreen
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.TripPackageScreen
import java.net.URLDecoder

fun NavGraphBuilder.homeNavGraph(navController: NavHostController, saveData: HandleRotateState) {
    navigation(
        startDestination = AppScreen.Home.route,
        route = AppScreen.HomeGraph.route
    ) {

        composable(
            "${AppScreen.Hotel.route}/{hotelID}/{tripPackageID}/{isOrder}",
        ){ backStackEntry ->
            val hotelID = backStackEntry.arguments?.getString("hotelID") ?: ""
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""
            val isOrder = backStackEntry.arguments?.getString("isOrder") ?: "false"
            HotelBookingScreenLayout(
                navController = navController,
                hotelID = hotelID,
                isOrder = isOrder,
                bookingID = "",
                tripPackageID = tripPackageID,
                saveData = saveData
            )
        }


        composable(
            "${AppScreen.Hotel.route}/{bookingID}/{hotelID}/{tripPackageID}/{isOrder}/{numberOfRoom}/{numberOFClient}/{startDate}/{endDate}",
        ){ backStackEntry ->
            val bookingID = backStackEntry.arguments?.getString("bookingID") ?: ""
            val hotelID = backStackEntry.arguments?.getString("hotelID") ?: ""
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""
            val isOrder = backStackEntry.arguments?.getString("isOrder") ?: "false"
            val numberOfRoom = backStackEntry.arguments?.getString("numberOfRoom") ?: ""
            val numberOFClient = backStackEntry.arguments?.getString("numberOFClient") ?: ""
            val startDate = backStackEntry.arguments?.getString("startDate") ?: ""
            val endDate = backStackEntry.arguments?.getString("endDate") ?: ""
            HotelBookingScreenLayout(
                navController = navController,
                hotelID = hotelID,
                isOrder = isOrder,
                bookingID = bookingID,
                numberOfRoom =numberOfRoom,
                numberOFClient = numberOFClient,
                startDate =startDate,
                endDate = endDate,
                tripPackageID = tripPackageID,
                saveData = saveData
            )
        }

        composable(AppScreen.Home.route) {
            HomeScreen(navController)
        }

        composable(
            route = "${AppScreen.TripPackage.route}/{tripPackageID}/{bookingID}",
            arguments = listOf(
                navArgument("tripPackageID") { type = NavType.StringType },
                navArgument("bookingID") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""
            val bookingID = backStackEntry.arguments?.getString("bookingID") ?: ""
            TripPackageScreen(navController = navController, tripPackageID = tripPackageID, bookingID = bookingID)
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
            route = "${AppScreen.TripPackageBooking.route}/{tripPackageID}/{tripPackageName}/{tripPackagePrice}/{availableSlots}",
            arguments = listOf(
                navArgument("tripPackageID") { type = NavType.StringType },
                navArgument("tripPackageName") { type = NavType.StringType },
                navArgument("tripPackagePrice") { type = NavType.StringType },
                navArgument("availableSlots") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""
            val tripPackageName = backStackEntry.arguments?.getString("tripPackageName") ?: ""
            val tripPackagePrice = backStackEntry.arguments?.getString("tripPackagePrice") ?: ""
            val availableSlots = backStackEntry.arguments?.getString("availableSlots") ?: ""

            TripPackageBookingScreen(
                navController = navController,
                tripPackageID = tripPackageID,
                tripPackageName = tripPackageName,
                tripPackagePrice = tripPackagePrice,
                availableSlots = availableSlots.toInt()
            )
        }

        composable(
            AppScreen.EditScreen.route
        ) {
            HotelBookingListScreen(navController,userId = "7ZpmX7cb6JY4xpOBY8fOzA4AA6W2")
        }

        composable(
            "${AppScreen.BookingHistory.route}/{encodedBooking}",
        ) { backStackEntry ->
            val encodedBooking = backStackEntry.arguments?.getString("encodedBooking") ?: ""
            val booking = try {
                URLDecoder.decode(encodedBooking, "UTF-8")
            } catch (e: Exception) {
                encodedBooking
            }
            ModifyHotelBooking(
                navController = navController,
                modifier = Modifier,
                booking = booking
            )
        }

    }
}