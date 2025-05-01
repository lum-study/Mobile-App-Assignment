package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bookblitzpremium.upcomingproject.HotelBookingScreenLayout
import com.bookblitzpremium.upcomingproject.MapScreen
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingAmount
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingDatePage
import com.bookblitzpremium.upcomingproject.ui.screen.booking.HotelBookingListScreen
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ModifyHotelBooking
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ReviewFinalPackageSelected
import java.net.URLDecoder

enum class HotelScreen(val route: String) {
   Hotel_Details("hotel_details")
}

fun NavGraphBuilder.hotelNavGraph(navController: NavController) {

//        composable(AppScreen.Hotel_Details.route) {
//            DynamicHotelDetails( onNextButtonClicked = { }, navController)
//        }

        composable(
            "${AppScreen.Hotel.route}/{hotelID}/{tripPackageID}",
        ){ backStackEntry ->
            val hotelID = backStackEntry.arguments?.getString("hotelID") ?: ""
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""

//            HotelDetailScreen(
//                navController = navController,
//                hotelBookingId = hotelID,
//                tripPackageID = tripPackageID
//            )

            HotelBookingScreenLayout(
                navController = navController,
                hotelID  = hotelID,
                tripPackageID = tripPackageID,
            )
        }

        composable(
            AppScreen.EditScreen.route
        ) {
            HotelBookingListScreen(navController,userId = "123")
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

        composable(
            "${AppScreen.BookingDate.route}/{hotelID}/{hotelPrice}",
        ) { backStackEntry ->
            val hotelID = backStackEntry.arguments?.getString("hotelID") ?: ""
            val hotelPrice = backStackEntry.arguments?.getString("hotelPrice") ?: ""

            BookingDatePage(
                navController = navController,
                hotelID = hotelID,
                hotelPrice = hotelPrice,
            )
        }

        composable(
            "${AppScreen.Maps.route}/{locationName}"
        ){ backStackEntry ->
            val locationName = backStackEntry.arguments?.getString("locationName") ?: ""
            MapScreen(
                addressInput = locationName,
                navController = navController,
            )
        }

        composable(
            "${AppScreen.BookingPeople.route}/{hotelID}/{hotelPrice}/{startDate}/{endDate}",
        ) { backStackEntry ->
            val hotelID = backStackEntry.arguments?.getString("hotelID") ?: ""
            val hotelPrice = backStackEntry.arguments?.getString("hotelPrice") ?: ""
            val startDate = backStackEntry.arguments?.getString("startDate") ?: ""
            val endDate = backStackEntry.arguments?.getString("endDate") ?: ""

            BookingAmount(
                modifier = Modifier,
                navController = navController,
                hotelID = hotelID,
                hotelPrice = hotelPrice,
                startDate = startDate,
                endDate = endDate
            )

            //add the maps image
        }

        composable(
            AppScreen.PaymentHotels.route
        ){


        }

        composable(
            "${AppScreen.BookingReview.route}/{hotelID}/{startDate}/{endDate}/{totalPerson}/{roomBooked}/{totalPrice}/{paymentMethodToString}/{cardNumber}/{paymentID}"

        ) { backStackEntry ->
            val hotelID = URLDecoder.decode(backStackEntry.arguments?.getString("hotelID") ?: "", "UTF-8")
            val totalPrice = URLDecoder.decode(backStackEntry.arguments?.getString("totalPrice") ?: "", "UTF-8")
            val startDate = URLDecoder.decode(backStackEntry.arguments?.getString("startDate") ?: "", "UTF-8")
            val endDate = URLDecoder.decode(backStackEntry.arguments?.getString("endDate") ?: "", "UTF-8")
            val totalPerson = URLDecoder.decode(backStackEntry.arguments?.getString("totalPerson") ?: "", "UTF-8")
            val roomBooked = URLDecoder.decode(backStackEntry.arguments?.getString("roomBooked") ?: "", "UTF-8")
            val paymentMethod = URLDecoder.decode(backStackEntry.arguments?.getString("paymentMethodToString") ?: "", "UTF-8")
            val paymentId = URLDecoder.decode(backStackEntry.arguments?.getString("paymentID") ?: "", "UTF-8")
            val cardNumber = URLDecoder.decode(backStackEntry.arguments?.getString("cardNumber") ?: "", "UTF-8")
            println("AppScreen.BookingReview.route}/{$hotelID}/{$startDate}/{$endDate}/{$totalPerson}/{$roomBooked}/{$totalPrice}/{$paymentMethod}/{$cardNumber}/{$paymentId}")

            ReviewFinalPackageSelected(
                modifier = Modifier,
                navController = navController,
                hotelID = hotelID,
                totalPrice = totalPrice,
                startDate = startDate,
                endDate = endDate,
                totalPerson = totalPerson,
                roomBooked = roomBooked,
                paymentID = paymentId,
                paymentMethod = paymentMethod ?: "",
                cardNumber = cardNumber ?: "",
            )
        }

}
