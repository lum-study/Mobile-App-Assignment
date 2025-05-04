package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.TabletAuth.LoginWelcomeScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType


//may have to add to update trhe signOut state

@Composable
fun WelcomeLoginSizeLayout(
    navController: NavController,
    email:String,
    password: String
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
                password = password
            )
        }

        else -> {
            LoginWelcomeScreen(
                navController = navController,
                tabletScreen  = false,
                email = email,
                password = password
            )
        }
    }
}