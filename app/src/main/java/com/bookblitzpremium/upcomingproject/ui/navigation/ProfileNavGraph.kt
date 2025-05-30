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

fun NavGraphBuilder.profileNavGraph(navController: NavHostController, userViewModel: AuthViewModel) {
    navigation(startDestination = AppScreen.Profile.route, route = AppScreen.ProfileGraph.route) {
        composable(route = AppScreen.EditProfile.route) {
            EditProfileScreen()
        }

        composable(
            route = "${AppScreen.Ratings.route}/{hotelId}/{bookingID}",
            arguments = listOf(
                navArgument("hotelId") { type = NavType.StringType },
                navArgument("bookingID") { type = NavType.StringType })
        ) { backStackEntry ->
            val hotelId = backStackEntry.arguments?.getString("hotelId") ?: ""
            val bookingID = backStackEntry.arguments?.getString("bookingID") ?: ""

            RatingScreen(
                navController = navController,
                hotelId = hotelId,
                bookingID = bookingID,
            )
        }

        composable(route = AppScreen.RatingRecords.route) {
            RatingRecordsScreen()
        }

        composable(route = AppScreen.Profile.route) {
            ProfileScreen(
                navController = navController,
                onBackClick = { navController.popBackStack() },
                onMenuItemClick = { menuItem ->
                    when (menuItem) {
                        "Edit Profile" -> navController.navigate(AppScreen.EditProfile.route)
                        "My Orders" -> navController.navigate(AppScreen.MyOrders.route)
                        "Ratings" -> navController.navigate(AppScreen.RatingRecords.route)
                        "Log out" -> {
                            userViewModel.signOut()
                            navController.navigate(AppScreen.AuthGraph.route) {
                                popUpTo(AppScreen.Home.route) { inclusive = true }
                            }
                        }

                        else -> {}
                    }
                },
                authViewModel = userViewModel
            )
        }
    }
}