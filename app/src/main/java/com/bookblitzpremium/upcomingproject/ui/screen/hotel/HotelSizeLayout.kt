package com.bookblitzpremium.upcomingproject.ui.screen.hotel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
import com.bookblitzpremium.upcomingproject.ui.utility.isMediumHeight
import com.bookblitzpremium.upcomingproject.ui.utility.isTablet

@SuppressLint("ContextCastToActivity")
@Composable
fun DynamicHotelDetails(onNextButtonClicked: () -> Unit, navController: NavController) {
    val activity = LocalContext.current as? Activity ?: return

    val windowSizeClass = getWindowSizeClass(activity)

    val isTabletLandscape = isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)
    val isPhonePortrait = !isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

    when {
        isTabletLandscape -> {
            HotelHeaderTable(onNextButtonClicked = onNextButtonClicked)
        }
        isPhonePortrait -> {
            MobileLayout(2, 300.dp, 500.dp)
        }
        else -> {
            MobileLayout(3, 500.dp, 800.dp)
        }
    }
}

