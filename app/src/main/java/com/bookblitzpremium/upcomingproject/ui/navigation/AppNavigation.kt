package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authNavGraph(navController, viewModel)
        homeNavGraph(navController)
        hotelNavGraph(navController)
        searchNavGraph(navController)
        orderNavGraph(navController)
        profileNavGraph(navController)
    }
}