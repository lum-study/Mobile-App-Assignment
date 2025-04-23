package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.profile.EditProfileScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.ProfileScreen

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    navigation(startDestination = AppScreen.Profile.route, route = AppScreen.ProfileGraph.route) {
        composable(
            route = AppScreen.EditProfile.route
        ) {
            EditProfileScreen()
        }
        composable(route = AppScreen.Profile.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
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
                            authViewModel.signOut()
                            navController.navigate(AppScreen.Login.route) {
                                popUpTo(AppScreen.Login.route) { inclusive = true }
                            }
                        }

                        else -> {}
                    }
                }
            )
        }
    }
}