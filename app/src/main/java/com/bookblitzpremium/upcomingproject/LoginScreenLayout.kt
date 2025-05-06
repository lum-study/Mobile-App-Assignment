package com.bookblitzpremium.upcomingproject

import android.util.Log
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.TabletAuth.TabletLoginScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

@Composable
fun LoginSizeLayout(
    navController: NavController,
    viewModel : AuthViewModel,
    email:String = "",
    password: String = ""
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    val userDetails by viewModel.userDetails.collectAsState()

    Log.d("runtime", deviceType.toString())
    when (deviceType){
        DeviceType.MobilePortrait -> {
            LoginPage(
                showToggleToTablet = false,
                navController = navController,
                email = email,
                viewModel = viewModel
            )
        }

        DeviceType.TabletPortrait -> {
            TabletLoginScreen(navController,true, userDetails.email, userDetails.password,viewModel)
        }

        else -> {
            TabletLoginScreen(navController,false, userDetails.email, userDetails.password,viewModel)
        }
    }
}
