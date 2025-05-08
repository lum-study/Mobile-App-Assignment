package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.TabletAuth.LoginWelcomeScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

@Composable
fun WelcomeLoginSizeLayout(
    navController: NavController,
    email:String,
    password: String,
    viewModel: AuthViewModel
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    when (deviceType){
        DeviceType.TabletLandscape -> {
            LoginWelcomeScreen(
                navController = navController,
                tabletScreen  = true,
                email = email,
                password = password,
                viewModel = viewModel
            )
        }

        else -> {
            LoginWelcomeScreen(
                navController = navController,
                tabletScreen  = false,
                email = email,
                password = password,
                viewModel = viewModel
            )
        }
    }
}

