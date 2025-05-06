package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

    val userData by viewModel.userDetails.collectAsState()

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
                email = userData.email,
                password = userData.password,
                viewModel = viewModel
            )
        }

        else -> {
            RegristerTabletScreen(
                navController,
                tabletScreen = false,
                email = userData.email,
                password = userData.password,
                viewModel = viewModel
            )
        }
    }
}