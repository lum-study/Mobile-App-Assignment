package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier
) {
    val userModel: AuthViewModel = hiltViewModel()
    val navigationCommand by userModel.navigationCommand.collectAsState()

    LaunchedEffect(navigationCommand) {
        navigationCommand?.let { destination ->
            navController.navigate(destination) {
                popUpTo(navController.graph.id) {
                }
                launchSingleTop = true
            }
            userModel.clearNavigationCommand()
        }
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authNavGraph(navController, userModel)
        homeNavGraph(navController)
        searchNavGraph(navController)
        orderNavGraph(navController)
        profileNavGraph(navController)
    }
}