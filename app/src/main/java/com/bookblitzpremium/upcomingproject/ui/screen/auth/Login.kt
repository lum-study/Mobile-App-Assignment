package com.bookblitzpremium.upcomingproject.ui.screen.auth

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.SignInWithGoogle
import com.bookblitzpremium.upcomingproject.ui.components.VideoPlayer
import com.bookblitzpremium.upcomingproject.ui.components.videoUri
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme


//@Composable
//fun DynamicLoginPage(onNextButtonClicked: () -> Unit, navController: NavController) {
//    val activity = LocalContext.current as? Activity
//
//    if (activity != null) {
//        val windowSizeClass = getWindowSizeClass(activity)
//
//        when {
//            isTablet(windowSizeClass) && isMediumHeight(windowSizeClass) -> {
//                LoginPage(true, onNextButtonClicked, navController)
//            }
//            !isTablet(windowSizeClass) && isMediumHeight(windowSizeClass) -> {
//                LoginPage(false, onNextButtonClicked, navController)
//            }
//            else -> {
//                LoginPage(false, onNextButtonClicked, navController)
//            }
//        }
//    } else {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Activity is null - Preview mode or invalid context")
//        }
//    }
//}

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

@Composable
fun LoginPage(showToggleToTablet: Boolean ,onNextButtonClicked: () -> Unit, navController: NavController){

    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        VideoPlayer(
            videoUri = videoUri,
            modifier = Modifier
                .fillMaxSize()

        )

        // 📝 Login UI - Positioned above the video
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
                .padding(horizontal = 28.dp)
                .offset(x = offsetValueX),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ){
            Text(
                text = "Welcome Back!",
                style = AppTheme.typography.expandBold,
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )

            CustomTextField(
                value = "",
                onValueChange = { },
                label = "Username",
                placeholder = "Enter your username",
                leadingIcon = Icons.Default.Person,
                trailingIcon = Icons.Default.Clear,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = valueHorizontal, vertical = 16.dp)

            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = "",
                onValueChange = { },
                label = "Password",
                placeholder = "Enter your Password",
                leadingIcon = Icons.Default.Lock,
                trailingIcon = Icons.Default.Clear,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = valueHorizontal, vertical = 16.dp)

            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Forgot Password?",
                style = AppTheme.typography.bodyLarge,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = valueHorizontal, top = 30.dp)
                    .clickable {
                        navController.navigate(AppScreen.ForgotPassword.route)
                    }
            )

            Spacer(modifier = Modifier.height(24.dp))

            ButtonHeader( R.string.login, valueHorizontal,onNextButtonClicked = onNextButtonClicked )

            SignInWithGoogle(valueHorizontal)

            Text(
                text = "Register Account",
                style = AppTheme.typography.bodyLarge,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = valueHorizontal, top = 30.dp)
                    .clickable {
                        navController.navigate(AppScreen.Register.route)
                    }
            )
        }
    }
}
