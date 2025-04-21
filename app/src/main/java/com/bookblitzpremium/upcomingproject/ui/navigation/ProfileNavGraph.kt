package com.bookblitzpremium.upcomingproject.navigation

import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.ProfileScreen

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    composable(route = AppScreen.Profile.route) {
        ProfileScreen(
            navController = navController,
            userName = "John Doe", // Replace with actual user data
            onBackClick = {
                navController.popBackStack()
            },
            onMenuItemClick = { menuItem ->
                when (menuItem) {
                    "Edit Profile" -> navController.navigate(AppScreen.EditProfile.route)
                    "Payment Methods" -> navController.navigate(AppScreen.PaymentMethods.route)
                    "My Orders" -> navController.navigate(AppScreen.MyOrders.route)
                    "Ratings" -> navController.navigate(AppScreen.Ratings.route)
                    "Log out" -> {
                        // handle logout logic if needed
                        navController.navigate(AppScreen.Login.route) {
                            popUpTo(AppScreen.Profile.route) { inclusive = true }
                        }
                    }
                    else -> {}
                }
            }
        )
    }
}