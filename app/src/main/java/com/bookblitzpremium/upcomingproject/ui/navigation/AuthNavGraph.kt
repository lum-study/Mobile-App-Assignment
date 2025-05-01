package com.bookblitzpremium.upcomingproject.ui.navigation

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bookblitzpremium.upcomingproject.GenderSelectionScreen
import com.bookblitzpremium.upcomingproject.GenderSizeLayout
import com.bookblitzpremium.upcomingproject.LoginSizeLayout
import com.bookblitzpremium.upcomingproject.RegristerSizeLayout
import com.bookblitzpremium.upcomingproject.TabletAuth.LoginScreen2
import com.bookblitzpremium.upcomingproject.TabletAuth.Step1Screen
import com.bookblitzpremium.upcomingproject.TabletAuth.TabletLoginScreen
import com.bookblitzpremium.upcomingproject.TabletAuth.encodeToUri
import com.bookblitzpremium.upcomingproject.WelcomeLoginSizeLayout
import com.bookblitzpremium.upcomingproject.WelcomeRegristerSizeLayout
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicForgetPasswordPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicOTPPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.GenderMobileVersion
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.RegristerPage



@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    viewModel: AuthViewModel
) {

    Log.e("MyTag", "This is an error log message")

    navigation(
        startDestination = AppScreen.Login.route,
        route = AppScreen.AuthGraph.route
    ) {
        composable(
            route = AppScreen.Login.route,
        ) {

//            LoginSizeLayout(
//                navController = navController,
//                viewModel = viewModel,
//            )
            LoginPage(
                showToggleToTablet = false,
                navController = navController,
                email = "",
                viewModel = viewModel
            )
        }


        composable(
            route = "${AppScreen.Login.route}/{email}/{password}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email").toString()
            val password = backStackEntry.arguments?.getString("password").toString()

            LoginSizeLayout(
                navController = navController,
                viewModel = viewModel,
                email = email,
                password = password
            )
        }

        composable(
            route = "${AppScreen.WelcomeLoginScreen.route}/{email}/{password}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email").toString()
            val password = backStackEntry.arguments?.getString("password").toString()
            WelcomeLoginSizeLayout(navController,email, password) // pass it to your screen
        }

        composable(
            route = "${AppScreen.WelcomeRegristerScreen.route}/{email}/{password}/{selectedGender}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
                navArgument("selectedGender") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email").toString()
            val password = backStackEntry.arguments?.getString("password").toString()
            val selectedGender = backStackEntry.arguments?.getString("selectedGender").toString()

            WelcomeRegristerSizeLayout(navController,email, password, selectedGender) // pass it to your screen
        }



        composable(AppScreen.Register.route) {
//            RegristerPage(navController, viewModel = viewModel)
            RegristerSizeLayout(
                navController = navController,
                viewModel = viewModel
            )
        }

        composable(route ="${AppScreen.Register.route}/{email}/{password}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
            )) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email").toString()
            val password = backStackEntry.arguments?.getString("password").toString()

            RegristerSizeLayout(
                navController = navController,
                viewModel = viewModel,
                email = email,
                password = password
            )
        }


        composable(
            route = "${AppScreen.GenderScreen.route}/{email}/{password}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email").toString()
            val password = backStackEntry.arguments?.getString("password").toString()

            GenderSizeLayout(
                navController = navController,
                email = email,
                password =password
            )
//
//            GenderMobileVersion(navController, userId = userID, onClick = {
//                navController.navigate(AppScreen.Home.route)
//            })
        }

        composable(
            route = "${AppScreen.GenderScreen.route}/{email}/{password}/{selectedGender}",
            arguments = listOf(
                navArgument("email") { type = NavType.StringType },
                navArgument("password") { type = NavType.StringType },
                navArgument("selectedGender") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val email = backStackEntry.arguments?.getString("email").toString()
            val password = backStackEntry.arguments?.getString("password").toString()
            val selectedGender = backStackEntry.arguments?.getString("selectedGender").toString()

            GenderSizeLayout(
                navController = navController,
                email = email,
                password =password,
                selectedGender = selectedGender
            )
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
    }
}
