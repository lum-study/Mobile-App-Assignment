package com.bookblitzpremium.upcomingproject.ui.screen.travel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.ui.screen.auth.PlaceholderUI
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
import com.bookblitzpremium.upcomingproject.ui.utility.isMediumHeight
import com.bookblitzpremium.upcomingproject.ui.utility.isTablet

@SuppressLint("ContextCastToActivity")
@Composable
fun DynamicTravelPage(onNextButtonClicked: () -> Unit, navController: NavController) {
    val activity = LocalContext.current as? Activity ?: return PlaceholderUI()

    val windowSizeClass = getWindowSizeClass(activity)

    val isTabletLandscape = isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

    val isPhonePortrait = !isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

//    val showTabletUI = when {
//        isTabletLandscape -> true  // Show tablet layout
//        isPhonePortrait -> false   // Show phone layout
//        else -> false
//    }

    if(isTabletLandscape){
//        TravelHeaderTable()
    }else if (isPhonePortrait){

    }else{

    }
}

