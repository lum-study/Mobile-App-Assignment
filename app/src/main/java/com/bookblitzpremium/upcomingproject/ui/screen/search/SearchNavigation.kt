package com.bookblitzpremium.upcomingproject.ui.screen.search

import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.home.HomeScreen

@Composable
fun SearchNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Search.route,
        modifier = modifier,
    ) {
        composable(
            route = AppScreen.Search.route,
            enterTransition = { slideInVertically(initialOffsetY = { 1000 }) + fadeIn() },
        ) {
//            TestScreen()
//            HomeScreen()
            SearchScreen()
//            FilteredResultScreen()

//            ScheduleScreen()
//            FlightScreen()
//            ReceiptScreen()
//            ProfileScreen()
//            TripPackageScreen()
        }
        composable(
            route = AppScreen.Filter.route,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        ) {
//            FilterScreen(navController)
        }
        composable(route = AppScreen.Result.route) {
            FilteredResultScreen(navController = navController)
        }

        composable(route = AppScreen.Home.route) {
            HomeScreen(navController)
        }
    }
}