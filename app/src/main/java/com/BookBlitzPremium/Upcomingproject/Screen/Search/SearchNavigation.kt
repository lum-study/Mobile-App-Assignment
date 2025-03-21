package com.BookBlitzPremium.Upcomingproject.Screen.Search

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.BookBlitzPremium.Upcomingproject.ApiServices.TestScreen
import com.BookBlitzPremium.Upcomingproject.Enum.AppScreen
import com.BookBlitzPremium.Upcomingproject.Model.FlightSchedule
import com.BookBlitzPremium.Upcomingproject.Screen.Home.HomeScreen
import com.BookBlitzPremium.Upcomingproject.Screen.Payment.ReceiptScreen
import com.BookBlitzPremium.Upcomingproject.Screen.Profile.ProfileScreen
import com.BookBlitzPremium.Upcomingproject.Screen.TripPackageInfo.FlightScreen
import com.BookBlitzPremium.Upcomingproject.Screen.TripPackageInfo.ScheduleScreen
import com.BookBlitzPremium.Upcomingproject.Screen.TripPackageInfo.TripPackageScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SearchNavigation(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.Search.name,
        modifier = modifier,
    ) {
        composable(
            route = AppScreen.Search.name,
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
            route = AppScreen.Filter.name,
            enterTransition = { slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn() },
        ) {
            FilterScreen(navController)
        }
        composable(route = AppScreen.Result.name) {
            FilteredResultScreen(navController = navController)
        }
    }
}