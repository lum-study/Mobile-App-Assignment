package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.profile.EditProfileScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.ProfileScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.RatingRecordsScreen
import com.bookblitzpremium.upcomingproject.ui.screen.rating.RatingScreen

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    navigation(startDestination = AppScreen.Profile.route, route = AppScreen.ProfileGraph.route) {
        composable(route = AppScreen.EditProfile.route) {
            EditProfileScreen()
        }

        composable(
            route = "${AppScreen.Ratings.route}/{hotelId}",
            arguments = listOf(navArgument("hotelId") { type = NavType.StringType })
        ) { backStackEntry ->
            val hotelId = backStackEntry.arguments?.getString("hotelId") ?: "dummyHotelId"
            RatingScreen(
                navController = navController,
                hotelId = hotelId,
                onBackPressed = { navController.popBackStack() },
                onRatingSubmitted = { navController.navigate(AppScreen.RatingRecords.route) }
            )
        }

        composable(route = AppScreen.RatingRecords.route) {
            RatingRecordsScreen()
        }

        composable(route = AppScreen.Profile.route) {
            val authViewModel: AuthViewModel = hiltViewModel()
            val hotelId = "dummyHotelId"

            ProfileScreen(
                navController = navController,
                userName = "John Doe",
                onBackClick = { navController.popBackStack() },
                onMenuItemClick = { menuItem ->
                    when (menuItem) {
                        "Edit Profile" -> navController.navigate(AppScreen.EditProfile.route)
                        "My Orders" -> navController.navigate(AppScreen.MyOrders.route)
                        "Ratings" -> navController.navigate(AppScreen.RatingRecords.route)
                        "Log out" -> {
                            authViewModel.signOut()
                            navController.navigate(AppScreen.AuthGraph.route) {
                                popUpTo(AppScreen.Home.route) { inclusive = true }
                            }
                        }

                        else -> {}
                    }
                }
            )
        }
    }
}