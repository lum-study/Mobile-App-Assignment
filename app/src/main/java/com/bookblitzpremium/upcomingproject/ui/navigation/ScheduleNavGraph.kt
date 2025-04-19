package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen

fun NavGraphBuilder.scheduleNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.TestRoute.route,
        route = AppScreen.ScheduleGraph.route
    ) {
        composable(AppScreen.TestRoute.route){

        }
    }
}