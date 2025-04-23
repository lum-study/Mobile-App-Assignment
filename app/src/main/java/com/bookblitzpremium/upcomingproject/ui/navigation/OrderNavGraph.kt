package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen

fun NavGraphBuilder.orderNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.MyOrders.route,
        route = AppScreen.OrderGraph.route
    ) {
        composable(AppScreen.MyOrders.route) {

        }
    }
}