package com.bookblitzpremium.upcomingproject.ui.navigation

import android.app.Activity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.EditProfileScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.ProfileScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.RatingRecordsScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.RatingRecord
import com.bookblitzpremium.upcomingproject.ui.screen.rating.RatingScreen
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass

fun NavGraphBuilder.profileNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.Profile.route,
        route = AppScreen.ProfileGraph.route
    ) {
        composable(route = AppScreen.EditProfile.route) {
            EditProfileScreen(
                onBackClick = { navController.popBackStack() }
            )
        }

//        composable(
//            route = "${AppScreen.Ratings.route}/{hotelId}", // dynamic route for Ratings
//            arguments = listOf(navArgument("hotelId") { type = NavType.StringType })
//        ) { backStackEntry ->
//            val hotelId = backStackEntry.arguments?.getString("hotelId") ?: "dummyHotelId"
//            RatingScreen(
//                hotelId = hotelId,
//                onBackPressed = { navController.popBackStack() },
//                onRatingSubmitted = { ratingRecord: RatingRecord ->
//                    // Test navigation directly without `passData`
//                    navController.navigate(AppScreen.RatingRecords.route) {
//                        launchSingleTop = true // Prevent multiple instances of RatingRecordsScreen
//                    }
//                }
//            )
        }

        composable(route = AppScreen.RatingRecords.route) {
            // Sample records for RatingRecordsScreen with dummy data
            val sampleRecords = listOf(
                RatingRecord(
                    id = "1",
                    title = "Recent Feedback",
                    rating = 4.5f,
                    review = "Great experience overall",
                    date = "2023-06-15",
                    imageUrl = "https://yourimageurl.com/image.jpg",
                    progress = 0.75f
                ),
                RatingRecord(
                    id = "2",
                    title = "Great Service",
                    rating = 5.0f,
                    review = "Excellent customer service, would visit again!",
                    date = "2023-06-14",
                    imageUrl = "https://yourimageurl.com/image2.jpg",
                    progress = 1.0f
                )
            )

            RatingRecordsScreen(
                records = sampleRecords,
                onDeleteRecord = { id -> /* Handle deletion */ },
                onUpdateRecord = { updatedRecord -> /* Handle update */ },
                modifier = Modifier.fillMaxSize()
            )
        }

        composable(route = AppScreen.Profile.route) {
            val hotelId = "dummyHotelId"

            ProfileScreen(
                navController = navController,
                userName = "John Doe", // Replace with actual user data
                onBackClick = { navController.popBackStack() },
                onMenuItemClick = { menuItem ->
                    when (menuItem) {
                        "Edit Profile" -> navController.navigate(AppScreen.EditProfile.route)
                        "Payment Methods" -> navController.navigate(AppScreen.PaymentMethods.route)
                        "My Orders" -> navController.navigate(AppScreen.MyOrders.route)
                        "Ratings" -> navController.navigate("${AppScreen.Ratings.route}/$hotelId") // Pass dummy hotelId here
                        "Rating History" -> navController.navigate(AppScreen.RatingRecords.route)
                        "Log out" -> {
                            navController.navigate(AppScreen.Login.route) {
                                popUpTo(AppScreen.ProfileGraph.route) { inclusive = true }
                            }
                        }
                        else -> {}
                    }
                }
            )
        }
    }