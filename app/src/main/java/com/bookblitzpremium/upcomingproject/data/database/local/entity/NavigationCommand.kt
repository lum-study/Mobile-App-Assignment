package com.bookblitzpremium.upcomingproject.data.database.local.entity

sealed class NavigationCommand {
    object None : NavigationCommand()
    object ToHome : NavigationCommand()
    object ToLoginWithout : NavigationCommand()
    data class ToLogin(val email: String) : NavigationCommand()
}