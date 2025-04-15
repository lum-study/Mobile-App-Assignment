package com.bookblitzpremium.upcomingproject.ui.navigation

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.UserLogin
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicChangePassword
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicForgetPasswordPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicLoginPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicOTPPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicRegisterPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.EntryPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.OnboardingFlow



@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.Login.route,
        route = AppScreen.AuthGraph.route
    ) {

        composable(AppScreen.Login.route) {
            val parentEntry = remember(navController) {
                navController.getBackStackEntry(AppScreen.AuthGraph.route)
            }
            val userLoginViewModel = viewModel<UserLogin>(parentEntry)

            DynamicLoginPage(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.HomeGraph.route) {
                        popUpTo(AppScreen.AuthGraph.route) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                navController = navController,
                userLoginViewModel = userLoginViewModel
            )
        }

        composable(AppScreen.Register.route) {
            val parentEntry = remember(navController) {
                navController.getBackStackEntry(AppScreen.AuthGraph.route)
            }
            val userLoginViewModel = viewModel<UserLogin>(parentEntry)

            DynamicRegisterPage(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.Home.route)
                },
                navController = navController,
                userLoginViewModel = userLoginViewModel
            )
        }

        composable(AppScreen.OTP.route) {
            val parentEntry = remember(navController) {
                navController.getBackStackEntry(AppScreen.AuthGraph.route)
            }
            val userLoginViewModel = viewModel<UserLogin>(parentEntry)

            DynamicOTPPage(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.Home.route)
                },
                navController = navController,
                userLoginViewModel = userLoginViewModel
            )
        }

        composable(AppScreen.ForgotPassword.route) {
            val parentEntry = remember(navController) {
                navController.getBackStackEntry(AppScreen.AuthGraph.route)
            }
            val userLoginViewModel = viewModel<UserLogin>(parentEntry)

            DynamicForgetPasswordPage(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.Home.route)
                },
                navController = navController,
                userLoginViewModel = userLoginViewModel
            )
        }

        composable(AppScreen.ChangePassword.route) {
            val parentEntry = remember(navController) {
                navController.getBackStackEntry(AppScreen.AuthGraph.route)
            }
            val userLoginViewModel = viewModel<UserLogin>(parentEntry)

            DynamicChangePassword(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.Home.route)
                },
                navController = navController,
                userLoginViewModel = userLoginViewModel
            )
        }

        composable(AppScreen.EntryPage.route) {
            EntryPage(
                onGetStartedClick = {
                    navController.navigate(AppScreen.EntryPage2.route)
                }
            )
        }

        composable(AppScreen.EntryPage2.route) {
            OnboardingFlow(
                onFinish = {
                    navController.navigate(AppScreen.Login.route)
                }
            )
        }
    }
}
