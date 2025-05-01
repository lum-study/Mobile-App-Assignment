package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.TabletAuth.encodeToUri
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.ui.screen.auth.GenderMobileVersion
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

@Composable
fun GenderSizeLayout(
    navController: NavController,
    email:String = "",
    password: String = "",
    selectedGender: String = "",
    userID: String = ""
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    when (deviceType){
        DeviceType.MobilePortrait -> {
            GenderMobileVersion(
                navController = navController,
                email = email,
                password = password
            )
        }

        DeviceType.TabletLandscape -> {
            GenderSelectionScreen(
                navController = navController,
                tabletScreen = true,
                email = email,
                password = password,
            )
        }

        else -> {
            GenderSelectionScreen(
                navController = navController,
                tabletScreen = false,
                email = email,
                password = password,
            )
        }
    }
}