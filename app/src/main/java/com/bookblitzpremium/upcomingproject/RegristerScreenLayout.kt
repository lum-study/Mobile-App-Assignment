package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.TabletAuth.RegristerTabletScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.auth.RegristerPage
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

@Composable
fun RegristerSizeLayout(
    navController: NavController,
    viewModel : AuthViewModel,
    email:String = "",
    password: String = ""
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)


    when (deviceType){
        DeviceType.MobilePortrait -> {
            RegristerPage(
                navController = navController,
                viewModel = viewModel,
            )
        }

        DeviceType.TabletLandscape -> {
            RegristerTabletScreen(
                navController,
                tabletScreen = true,
                email = email,
                password = password
            )
        }

        else -> {
            RegristerTabletScreen(
                navController,
                tabletScreen = false,
                email = email,
                password = password
            )
        }
    }
}