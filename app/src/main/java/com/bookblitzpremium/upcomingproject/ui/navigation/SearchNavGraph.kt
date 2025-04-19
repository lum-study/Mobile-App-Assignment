package com.bookblitzpremium.upcomingproject.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.search.FilterScreen
import com.bookblitzpremium.upcomingproject.ui.screen.search.FilteredResultScreen
import com.bookblitzpremium.upcomingproject.ui.screen.search.SearchScreen

fun NavGraphBuilder.searchNavGraph(navController: NavHostController) {
    navigation(startDestination = AppScreen.Search.route, route = AppScreen.SearchGraph.route) {
        composable(AppScreen.Search.route) {
            SearchScreen(navController)
        }

        composable(AppScreen.Result.route) {
            FilteredResultScreen()
        }

        composable(
            route = "${AppScreen.Filter.route}/{keyword}",
            arguments = listOf(navArgument("keyword") { type = NavType.StringType })
        ) { backStackEntry ->
            val keyword = backStackEntry.arguments?.getString("keyword") ?: ""
            FilterScreen(navController = navController, keyword = keyword)
        }
    }
}