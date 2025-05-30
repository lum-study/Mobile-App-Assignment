package com.bookblitzpremium.upcomingproject.ui.navigation

import android.util.Log
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.bookblitzpremium.upcomingproject.HandleRotateState
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.utility.PermissionUtils

@Composable
fun AppNavigation(
    navController: NavHostController,
    startDestination: String,
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel,
    saveData: HandleRotateState
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authNavGraph(navController, viewModel)
        Log.e("MyTag", "This is an error log message")
        homeNavGraph(navController,saveData)
        hotelNavGraph(navController,saveData)
        searchNavGraph(navController)
        orderNavGraph(navController)
        profileNavGraph(navController,viewModel)
    }
}