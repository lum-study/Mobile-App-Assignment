package com.bookblitzpremium.upcomingproject.ui.navigation

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.UserLogin
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
import com.bookblitzpremium.upcomingproject.ui.screen.home.HomeScreen

@Composable
fun MyAppNavigation(modifier: Modifier = Modifier,authViewModel: UserLogin) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreen.Login.route, builder = {
        composable(AppScreen.Login.route){
            LoginPage(false, onNextButtonClicked = {}, navController,authViewModel)
        }
//        composable("signup"){
//            SignupPage(modifier,navController,authViewModel)
//        }
        composable(AppScreen.Home.route){
            HomeScreen(navController)
        }
    })
}