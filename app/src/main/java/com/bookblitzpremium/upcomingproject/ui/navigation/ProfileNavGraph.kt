package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.EditProfileScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.ProfileScreen

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    navigation(startDestination = AppScreen.Profile.route, route = AppScreen.ProfileGraph.route) {
        composable(
            route = AppScreen.Profile.route
        ){
            ProfileScreen(navController)
        }
        composable(
            route = AppScreen.EditProfile.route
        ){
            EditProfileScreen()
        }
        composable(route = AppScreen.Profile.route) {
            ProfileScreen(
                navController = navController,
//                userName = "John Doe", // Replace with actual user data
//                onBackClick = {
//                    navController.popBackStack()
//                },
//                onMenuItemClick = { menuItem ->
//                    when (menuItem) {
//                        "Edit Profile" -> navController.navigate(AppScreen.EditProfile.route)
//                        "Payment Methods" -> navController.navigate(AppScreen.PaymentMethods.route)
//                        "My Orders" -> navController.navigate(AppScreen.MyOrders.route)
//                        "Ratings" -> navController.navigate(AppScreen.Ratings.route)
//                        "Log out" -> {
//                            // handle logout logic if needed
//                            navController.navigate(AppScreen.Login.route) {
//                                popUpTo(AppScreen.Profile.route) { inclusive = true }
//                            }
//                        }
//                        else -> {}
//                    }
//                }
            )
        }
    }
}