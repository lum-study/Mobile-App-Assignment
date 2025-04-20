package com.bookblitzpremium.upcomingproject.ui.navigation

import android.util.Log
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
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
    }
}