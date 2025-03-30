package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
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
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingFlow
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.ButtonGrid
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.MobieLayout
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.MobieLayout1
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.OverlappingContentTest
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.RoundedBottomSheet
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.SelectingFigure


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.Login.route,
        route = AppScreen.AuthGraph.route
    ) {
        composable(AppScreen.Login.route) {
//            DynamicLoginPage(
//                onNextButtonClicked = {
//                    navController.navigate(AppScreen.HomeGraph.route) {
//                        popUpTo(
//                            AppScreen.AuthGraph.route
//                        ) { inclusive = true }
//                        launchSingleTop = true
//                    }
//                },
//                navController = navController
//            )

//            MobieLayout1(2, 500.dp, 500.dp)
//            SelectingFigure(2, modifier = Modifier)

//            ButtonGrid()
//            RoundedBottomSheet()

            OverlappingContentTest()
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

