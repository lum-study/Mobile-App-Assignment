package com.bookblitzpremium.upcomingproject


import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.TabletAuth.Step2Screen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

@Composable
fun WelcomeRegristerSizeLayout(
    navController: NavController,
    email:String,
    password: String,
    genderSelected: String
){
    val context = LocalContext.current
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    when (deviceType){
        DeviceType.TabletLandscape -> {

            Step2Screen(
                navController = navController,
                tabletScreen = false,
                email  = email,
                password = password,
                genderSelected = genderSelected,
            )
        }

        else -> {
            Step2Screen(
                navController = navController,
                tabletScreen = true,
                email  = email,
                password = password,
                genderSelected = genderSelected,
            )
        }
    }
}