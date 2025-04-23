package com.bookblitzpremium.upcomingproject.common.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material.icons.outlined.Task
import androidx.compose.ui.graphics.vector.ImageVector

enum class BottomNavigation(val icon: ImageVector, val navigation: AppScreen, val title: String) {
    Home(icon = Icons.Outlined.Home, navigation = AppScreen.HomeGraph, title = "Home"),
    Search(icon = Icons.Outlined.Search, navigation = AppScreen.SearchGraph, title = "Search"),
    Schedule(icon = Icons.Outlined.Task, navigation = AppScreen.OrderGraph, title = "My Order"),
    Profile(icon = Icons.Outlined.Person, navigation = AppScreen.ProfileGraph, title = "Profile"),
}