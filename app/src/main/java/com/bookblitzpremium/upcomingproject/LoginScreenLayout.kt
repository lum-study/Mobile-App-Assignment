package com.bookblitzpremium.upcomingproject

import android.util.Log
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.TabletAuth.TabletLoginScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

@Composable
fun LoginSizeLayout(
    navController: NavController,
    viewModel : AuthViewModel = hiltViewModel(),
    email:String = "",
    password: String = ""
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

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
            TabletLoginScreen(navController,true, email, password)
        }

        else -> {
            TabletLoginScreen(navController,false, email, password)
        }
    }
}
