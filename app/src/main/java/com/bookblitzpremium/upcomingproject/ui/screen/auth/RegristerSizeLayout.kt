//package com.bookblitzpremium.upcomingproject.ui.screen.auth
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.platform.LocalContext
//import androidx.navigation.NavController
//import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
//import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
//import com.bookblitzpremium.upcomingproject.ui.utility.isMediumHeight
//import com.bookblitzpremium.upcomingproject.ui.utility.isTablet
//
//@SuppressLint("ContextCastToActivity")
//@Composable
//fun DynamicRegisterPage(onNextButtonClicked: () -> Unit, navController: NavController, userModel: AuthViewModel) {
//    val activity = LocalContext.current as? Activity ?: return PlaceholderUI()
//
//    val windowSizeClass = getWindowSizeClass(activity)
//
//    val isTabletLandscape = isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)
//
//    val isPhonePortrait = !isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)
//
//    val showTabletUI = when {
//        isTabletLandscape -> true  // Show tablet layout
//        isPhonePortrait -> false   // Show phone layout
//        else -> false
//    }
//
////    RegisterPage( navController ,userLoginViewModel)
//}
