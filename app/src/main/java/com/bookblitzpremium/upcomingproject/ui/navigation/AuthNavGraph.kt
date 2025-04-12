package com.bookblitzpremium.upcomingproject.ui.navigation

import android.Manifest
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Person
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.Icon
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.graphics.drawable.toBitmap
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.bookblitzpremium.upcomingproject.App
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ViewModel.UserLogin
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.components.CustomDialog
import com.bookblitzpremium.upcomingproject.ui.components.NoticationToUser
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicChangePassword
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicForgetPasswordPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicLoginPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicOTPPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.DynamicRegisterPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.EntryPage
import com.bookblitzpremium.upcomingproject.ui.screen.auth.OnboardingFlow
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingAmount
import com.bookblitzpremium.upcomingproject.ui.screen.booking.BookingDatePage
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ReviewFinalPackageSelected
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.HotelScreen
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.MobieLayout
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.OverlappingContentTest
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.ProductDetailScreen
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.SelectingFigure


fun NavGraphBuilder.authNavGraph(navController: NavHostController) {
    navigation(
        startDestination = AppScreen.Login.route,
        route = AppScreen.AuthGraph.route
    ) {


        composable(AppScreen.Login.route) {

            val context = LocalContext.current
//            NoticationToUser()
//            CustomDialog(onDismissRequest = {}, onNextClick = {})
            DynamicLoginPage(
                onNextButtonClicked = {
                    navController.navigate(AppScreen.HomeGraph.route) {
                        popUpTo(
                            AppScreen.AuthGraph.route
                        ) { inclusive = true }
                        launchSingleTop = true
                    }
                },
                navController = navController
            )

//            EntryPage(onGetStartedClick = {  navController.navigate(AppScreen.HomeGraph.route)})
//            OnboardingFlow(
//                onFinish= {  navController.navigate(AppScreen.HomeGraph.route)}
//            )
//            MobieLayout1(2, 500.dp, 500.dp)
//            SelectingFigure(2, modifier = Modifier)

//            ButtonGrid()
//            RoundedBottomSheet()
//            BookingDatePage(modifier = Modifier,navController)
//            OverlappingContentTest(2, navController)
//            BookingPeople(modifier = Modifier)
//            BookingAmount(modifier = Modifier,navController)
//            ReviewFinalPackageSelected()
//            HotelScreen()

        }

        composable(AppScreen.Register.route) {
            DynamicRegisterPage(  // Check the correct spelling here
                onNextButtonClicked = { navController.navigate(AppScreen.Home.route) },
                navController = navController
            )
        }

        composable(AppScreen.OTP.route) {
            DynamicOTPPage(
                onNextButtonClicked = { navController.navigate(AppScreen.Home.route) },
                navController = navController
            )
        }

        composable(AppScreen.ForgotPassword.route) {
            DynamicForgetPasswordPage(
                onNextButtonClicked = { navController.navigate(AppScreen.Home.route) },
                navController = navController
            )
        }

        composable(AppScreen.ChangePassword.route) {
            DynamicChangePassword(
                onNextButtonClicked = { navController.navigate(AppScreen.Home.route) },
                navController = navController
            )
        }

        composable(AppScreen.EntryPage.route) {
            EntryPage(onGetStartedClick = {
                navController.navigate(AppScreen.EntryPage2.route)
            })
        }

        composable(AppScreen.EntryPage2.route) {
            OnboardingFlow(onFinish = {
                navController.navigate(AppScreen.Login.route)
            })
        }


//        composable(AppScreen.Home.route) {
//            DynamicHotelDetails(
//                onNextButtonClicked = { navController.navigate(AppScreen.Register.route) },
//                navController = navController
//            )
//        }
    }
}

