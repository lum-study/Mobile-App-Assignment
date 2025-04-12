package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.PaymentDetails

enum class Payment(val route: String) {
    Card(route = "Debit Card"),
    CreditCard(route = "Credit Card"),
    EWallet(route = "eWallet"),
    PaymentMethod(route = "PaymentMethod"),
    Receipt(route = "Receipt"),
}

fun NavGraphBuilder.paymentNavGraph(navController: NavController) {
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
