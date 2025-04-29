package com.bookblitzpremium.upcomingproject.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicForgetPasswordPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicOTPPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.GenderMobileVersion
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.RegristerPage



@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.authNavGraph(navController: NavHostController, viewModel: AuthViewModel) {

    Log.e("MyTag", "This is an error log message")

    navigation(
        startDestination = AppScreen.Login.route,
        route = AppScreen.AuthGraph.route
    ) {
        composable(
            route = AppScreen.Login.route,
        ) {
            LoginPage(
                showToggleToTablet = false,
                navController = navController,
                email = "",
                viewModel = viewModel
            )
        }

//        composable(
//            route = "${AppScreen.Login.route}/{email}",
//            arguments = listOf(navArgument("email") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val email = backStackEntry.arguments?.getString("email") ?: ""
//            LoginPage(
//                showToggleToTablet = false,
//                navController = navController,
//                email = email,
//                viewModel = viewModel
//            )
//        }

//        composable(AppScreen.VerifyEmailWaiting.route){
//            VerifyEmailWaitingScreen(navController)
//        }

        composable(AppScreen.Register.route) {
            RegristerPage(navController, viewModel = viewModel)
        }

        composable(
            route = "${AppScreen.GenderScreen.route}/{userID}",
            arguments = listOf(navArgument("userID") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val userID = backStackEntry.arguments?.getString("userID") ?: ""
            GenderMobileVersion(navController, userId = userID, onClick = {
                navController.navigate(AppScreen.Home.route)
            })
        }

        composable(
            route = "${AppScreen.OTP.route}/{email}",
            arguments = listOf(navArgument("email") {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email") ?: ""
            DynamicOTPPage(
                navController = navController,
                email = email,
                userModel = viewModel
            )
        }

        composable(AppScreen.ForgotPassword.route) {
            DynamicForgetPasswordPage(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.Login.route)
                },
                navController = navController,
                userModel = viewModel
            )
        }

//        composable(AppScreen.ChangePassword.route) {
//            val parentEntry = remember(navController) {
//                navController.getBackStackEntry(AppScreen.AuthGraph.route)
//            }
//            val userModel = viewModel<AuthViewModel>(parentEntry)
//
//            DynamicChangePassword(
//                onNextButtonClicked = {
//                    navController.navigate(AppScreen.Home.route)
//                },
//                navController = navController,
//                userModel = userModel
//            )
//        }
//
//        composable(AppScreen.EntryPage.route) {
//            EntryPage(
//                onGetStartedClick = {
//                    navController.navigate(AppScreen.EntryPage2.route)
//                }
//            )
//        }
//
//        composable(AppScreen.EntryPage2.route) {
//            OnboardingFlow(
//                onFinish = {
//                    navController.navigate(AppScreen.Login.route)
//                }
//            )
//        }
    }
}
