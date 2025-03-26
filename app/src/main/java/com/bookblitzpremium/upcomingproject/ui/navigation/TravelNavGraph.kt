package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.PaymentDetails

enum class BookingType(val route: String) {
    TripPackage(route = "Trip Packages"),
    Hotel(route = "Hotels"),
}

fun NavGraphBuilder.TravelNavGraph(navController: NavController) {
    composable(Payment.PaymentMethod.route) {
        PaymentDetails()
    }

//    composable(Payment.Receipt.route) {
//        HotelDetailsPayment(navController)
//    }

//    composable(Payment.Payment.route) {
//        PaymentPayment(navController)
//    }
}
