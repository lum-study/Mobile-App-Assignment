package com.bookblitzpremium.upcomingproject.common.enums

import androidx.annotation.StringRes
import com.bookblitzpremium.upcomingproject.R
import com.google.android.material.bottomappbar.BottomAppBar

enum class AppScreen(val route: String, val hasTopBar: Boolean, val hasBottomBar: Boolean) {
    // Auth Screens
    AuthGraph("AuthGraph", hasTopBar = false, hasBottomBar = false),
    Login("Login", hasTopBar = false, hasBottomBar = false),
    Register("Register", hasTopBar = false, hasBottomBar = false),
    OTP("OTP", hasTopBar = false, hasBottomBar = false),
    ForgotPassword("ForgotPassword", hasTopBar = false, hasBottomBar = false),
    ChangePassword("ChangePassword", hasTopBar = false, hasBottomBar = false),

    // Home Screens
    HomeGraph("HomeGraph", hasTopBar = false, hasBottomBar = false),
    Home("Home", hasTopBar = false, hasBottomBar = true),

    TripPackage("TripPackage", hasTopBar = true, hasBottomBar = false),
    Schedule("Schedule", hasTopBar = true, hasBottomBar = false),
    Flight("Flight", hasTopBar = true, hasBottomBar = false),
    Hotel("Hotel", hasTopBar = false, hasBottomBar = false),

    // Search Screens
    SearchGraph("SearchGraph", hasTopBar = false, hasBottomBar = false),
    Search("Search", hasTopBar = true, hasBottomBar = true),
    Result("Result", hasTopBar = true, hasBottomBar = true),
    Filter("Filter", hasTopBar = true, hasBottomBar = true),

    // Profile Screens
    ProfileGraph("ProfileGraph", hasTopBar = false, hasBottomBar = false),
    Profile("Profile", hasTopBar = true, hasBottomBar = true)
}
