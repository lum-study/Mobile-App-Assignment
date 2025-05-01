package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.TabletAuth.Step1Screen
import com.bookblitzpremium.upcomingproject.TabletAuth.TabletLoginScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
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
            Step1Screen(
                navController,
                tabletScreen = true,
                email = email,
                password = password
            )
        }

        else -> {
            Step1Screen(
                navController,
                tabletScreen = false,
                email = email,
                password = password
            )
        }
    }
}