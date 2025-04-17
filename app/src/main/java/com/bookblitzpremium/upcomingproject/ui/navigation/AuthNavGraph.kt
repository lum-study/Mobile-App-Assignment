package com.bookblitzpremium.upcomingproject.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicForgetPasswordPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicOTPPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.RegristerPage


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.authNavGraph(navController: NavHostController, userModel: AuthViewModel) {

    navigation(
        startDestination = AppScreen.Login.route,
        route = AppScreen.AuthGraph.route
    ) {

        composable(AppScreen.Login.route) {
            LoginPage(
                showToggleToTablet = false,
                navController = navController,
                viewModel = userModel
            )
        }

        composable(AppScreen.Register.route) {
            RegristerPage( userModel,navController)
        }

        composable(AppScreen.OTP.route) {
            DynamicOTPPage(
                navController = navController,
                userModel = userModel
            )
        }

        composable(AppScreen.ForgotPassword.route) {
            DynamicForgetPasswordPage(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.Login.route)
                },
                navController = navController,
                userModel = userModel
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
