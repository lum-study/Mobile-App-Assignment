package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicChangePassword
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicForgetPasswordPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicLoginPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicOTPPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicRegisterPage

fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.Login.route,
        route = AppScreen.AuthGraph.route
    ) {
        composable(AppScreen.Login.route) {
            DynamicLoginPage(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.HomeGraph.route) {
                        popUpTo(
                            AppScreen.AuthGraph.route
                        ) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                navController = navController
            )
        }

        composable(AppScreen.Register.route) {
            DynamicRegisterPage(  // Check the correct spelling here
                onNextButtonClicked = { navController.navigate(AppScreen.Home.route) },
                navController = navController
            )
        }

        composable(AppScreen.OTP.route) {
            DynamicOTPPage(
                onNextButtonClicked = { navController.navigate(AppScreen.Home.route) },
                navController = navController
            )
        }

        composable(AppScreen.ForgotPassword.route) {
            DynamicForgetPasswordPage(
                onNextButtonClicked = { navController.navigate(AppScreen.Home.route) },
                navController = navController
            )
        }

        composable(AppScreen.ChangePassword.route) {
            DynamicChangePassword(
                onNextButtonClicked = { navController.navigate(AppScreen.Home.route) },
                navController = navController
            )
        }

//        composable(AppScreen.Home.route) {
//            DynamicHotelDetails(
//                onNextButtonClicked = { navController.navigate(AppScreen.Register.route) },
//                navController = navController
//            )
//        }
    }
}