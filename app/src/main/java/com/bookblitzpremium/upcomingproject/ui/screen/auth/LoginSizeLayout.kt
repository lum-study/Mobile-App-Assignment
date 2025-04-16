package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
import com.bookblitzpremium.upcomingproject.ui.utility.isMediumHeight
import com.bookblitzpremium.upcomingproject.ui.utility.isTablet

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("ContextCastToActivity")
@Composable
fun DynamicLoginPage(onNextButtonClicked: () -> Unit, navController: NavController, userModel: AuthViewModel ) {
    val activity = LocalContext.current as? Activity ?: return PlaceholderUI()

    val windowSizeClass = getWindowSizeClass(activity)

    val isTabletLandscape = isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

    val isPhonePortrait = !isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

    val showTabletUI = when {
        isTabletLandscape -> true  // Show tablet layout
        isPhonePortrait -> false   // Show phone layout
        else -> false
    }

//    LoginPage(showTabletUI, onNextButtonClicked, navController,userLoginViewModel)

    }

@Composable
fun PlaceholderUI() {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Activity is null - Preview mode or invalid context")
    }
}
