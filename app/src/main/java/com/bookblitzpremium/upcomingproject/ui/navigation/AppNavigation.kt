package com.bookblitzpremium.upcomingproject.ui.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun AppNavigation(navController: NavHostController, startDestination: String, modifier: Modifier = Modifier) {

    val userModel: AuthViewModel = hiltViewModel()

// Observe navigation commands from AuthViewModel
    val navigationCommand by userModel.navigationCommand.collectAsState()

    LaunchedEffect(navigationCommand) {
        navigationCommand?.let { destination ->
            Log.d("MainApp", "Navigating to $destination")
            navController.navigate(destination) {
                popUpTo(navController.graph.startDestinationId) {
                    inclusive = true
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

        authNavGraph(navController,userModel)
        homeNavGraph(navController,userModel)
        searchNavGraph(navController)
        profileNavGraph(navController)
    }
}

