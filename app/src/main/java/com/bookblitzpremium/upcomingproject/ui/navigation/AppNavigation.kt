package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.MobieLayout

@Composable
fun AppNavigation(navController: NavHostController, startDestination: String, modifier: Modifier = Modifier) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {

        authNavGraph(navController)
        homeNavGraph(navController)
        searchNavGraph(navController)
        profileNavGraph(navController)
    }
}