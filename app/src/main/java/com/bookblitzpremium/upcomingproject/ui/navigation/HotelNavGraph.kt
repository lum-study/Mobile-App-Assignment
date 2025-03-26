package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.DynamicHotelDetails

enum class HotelScreen(val route: String) {
   Hotel_Details("hotel_details")
}

fun NavGraphBuilder.hotelNavGraph(navController: NavController) {
    composable(HotelScreen.Hotel_Details.route) {
        DynamicHotelDetails( onNextButtonClicked = { }, navController)
    }


}
