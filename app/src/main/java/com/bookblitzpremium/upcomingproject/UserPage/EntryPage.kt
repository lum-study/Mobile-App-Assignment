//package com.BookBlitzPremium.Upcomingproject.UserPage
//
//import android.annotation.SuppressLint
//import android.app.Activity
//import android.content.Context
//import android.net.Uri
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.ArrowForward
//import androidx.compose.material3.Button
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.unit.dp
//import com.bookblitzpremium.upcomingproject.R
//import com.BookBlitzPremium.Upcomingproject.ui.theme.AppTheme
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.TextFieldDefaults
//import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
//import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
//import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
//import androidx.compose.runtime.*
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
//import androidx.compose.ui.draw.blur
//import androidx.compose.ui.graphics.vector.ImageVector
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.unit.sp
//import androidx.compose.ui.viewinterop.AndroidView
//import androidx.media3.common.MediaItem
//import androidx.media3.common.Player
//import androidx.media3.exoplayer.ExoPlayer
//import androidx.media3.ui.AspectRatioFrameLayout
//import androidx.media3.ui.PlayerView
//import androidx.navigation.NavController
//
//
//@SuppressLint("ContextCastToActivity")
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Composable
//fun DynamicLoginPage(  onNextButtonClicked: () -> Unit, navController: NavController ) {
//    val activity = LocalContext.current as? Activity
//
//    if (activity != null) {
//        val windowSizeClass = calculateWindowSizeClass(activity = activity)
//        when {
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
//                LoginPage(true , onNextButtonClicked = onNextButtonClicked, navController = navController )
//            }
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
//                LoginPage(false ,onNextButtonClicked = onNextButtonClicked,navController = navController )
//            }
//            else -> {
//                LoginPage(false, onNextButtonClicked = onNextButtonClicked,navController = navController )
//            }
//        }
//    } else {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Activity is null - Preview mode or invalid context")
//        }
//    }
//}
//
//
//
//
//@SuppressLint("ContextCastToActivity")
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Composable
//fun DynamicOTPPage(){
//    val activity = LocalContext.current as? Activity
//
//    if (activity != null) {
//        val windowSizeClass = calculateWindowSizeClass(activity = activity)
//        when {
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
//                OTPpage(true)
//            }
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
//                OTPpage(false)
//            }
//            else -> {
//                OTPpage(false)
//            }
//        }
//    } else {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Activity is null - Preview mode or invalid context")
//        }
//    }
//}
//
//
//@SuppressLint("ContextCastToActivity")
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Composable
//fun DynamicRegisterPage(onNextButtonClicked: () -> Unit,navController: NavController){
//    val activity = LocalContext.current as? Activity
//
//    if (activity != null) {
//        val windowSizeClass = calculateWindowSizeClass(activity = activity)
//        when {
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
//                RegristerPage(true, onNextButtonClicked = onNextButtonClicked,navController = navController)
//            }
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Expanded -> {
//                RegristerPage(false , onNextButtonClicked = onNextButtonClicked,navController = navController)
//            }
//            else -> {
//                RegristerPage(false, onNextButtonClicked = onNextButtonClicked,navController = navController)
//            }
//        }
//    } else {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Activity is null - Preview mode or invalid context")
//        }
//    }
//}
//
//@SuppressLint("ContextCastToActivity")
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Composable
//fun DynamicForgetPasswordPage(
//    onNextButtonClicked: () -> Unit
//){
//    val activity = LocalContext.current as? Activity
//
//    if (activity != null) {
//        val windowSizeClass = calculateWindowSizeClass(activity = activity)
//        when {
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
//                ForgetPassword(true ,onNextButtonClicked = onNextButtonClicked )
//            }
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
//                ForgetPassword(false ,onNextButtonClicked = onNextButtonClicked)
//            }
//            else -> {
//                ForgetPassword(false ,onNextButtonClicked = onNextButtonClicked)
//            }
//        }
//    } else {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Activity is null - Preview mode or invalid context")
//        }
//    }
//}
//
//
//
//
////entry text and design
////// Content Column: Overlay for text and icons
////Column(
////modifier = Modifier
////.fillMaxSize()
////.background(Color.Transparent),
////horizontalAlignment = Alignment.CenterHorizontally
////) {
////    Row(
////        modifier = Modifier
////            .padding(top = 40.dp, start = 36.dp)
////            .width(280.dp)
////    ) {
////        Text(
////            text = wording, // Assuming 'wording' is defined elsewhere
////            style = AppTheme.typography.headlineLarge,
////            modifier = Modifier.padding(top = 160.dp)
////        )
////    }
////
////    Spacer(modifier = Modifier.height(40.dp))
////
////    Row(
////        modifier = Modifier
////            .fillMaxWidth()
////            .padding(top = 80.dp),
////        horizontalArrangement = Arrangement.Center
////    ) {
////        Box(
////            modifier = Modifier
////                .size(48.dp)
////                .clip(CircleShape)
////                .background(Color.LightGray)
////                .padding(12.dp),
////            contentAlignment = Alignment.Center
////        ) {
////            Icon(
////                imageVector = Icons.Default.ArrowForward,
////                contentDescription = "Arrow icon",
////                modifier = Modifier.size(24.dp),
////                tint = Color.Black
////            )
////        }
////    }
////}
