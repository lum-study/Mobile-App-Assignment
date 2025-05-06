package com.bookblitzpremium.upcomingproject


import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.TabletAuth.WelcomeRegristerScreen
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

    var savedEmail by rememberSaveable { mutableStateOf(email) }
    var savedPassword by rememberSaveable { mutableStateOf(password) }

    when (deviceType){
        DeviceType.TabletLandscape -> {

            WelcomeRegristerScreen(
                navController = navController,
                tabletScreen = false,
                email  = email,
                password = password,
                genderSelected = genderSelected,
            )
        }
        else -> {
            WelcomeRegristerScreen(
                navController = navController,
                tabletScreen = true,
                email  = email,
                password = password,
                genderSelected = genderSelected,
            )
        }
    }
}