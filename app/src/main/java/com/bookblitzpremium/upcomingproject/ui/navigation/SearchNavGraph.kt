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
            FilteredResultScreen(navController = navController)
        }

        composable(
            route = "${AppScreen.Filter.route}/{keyword}/{minPrice}/{maxPrice}",
            arguments = listOf(
                navArgument("keyword") { type = NavType.StringType },
                navArgument("minPrice") { type = NavType.StringType },
                navArgument("maxPrice") { type = NavType.StringType },
            )
        ) { backStackEntry ->
            val keyword = backStackEntry.arguments?.getString("keyword") ?: ""
            val minPrice = backStackEntry.arguments?.getString("minPrice") ?: ""
            val maxPrice = backStackEntry.arguments?.getString("maxPrice") ?: ""
            FilterScreen(
                navController = navController,
                keyword = keyword,
                minPrice = minPrice,
                maxPrice = maxPrice
            )
        }
    }
}