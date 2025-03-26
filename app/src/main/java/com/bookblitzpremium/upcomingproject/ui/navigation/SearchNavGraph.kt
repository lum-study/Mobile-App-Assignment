package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.search.FilterScreen
import com.bookblitzpremium.upcomingproject.ui.screen.search.FilteredResultScreen
import com.bookblitzpremium.upcomingproject.ui.screen.search.SearchScreen

fun NavGraphBuilder.searchNavGraph(navController: NavHostController) {
    navigation(startDestination = AppScreen.Search.route, route = AppScreen.SearchGraph.route) {
        composable(AppScreen.Search.route) {
            SearchScreen()
        }

        composable(AppScreen.Result.route) {
            FilteredResultScreen()
        }

        composable(AppScreen.Filter.route) {
            FilterScreen(navController)
        }
    }
}