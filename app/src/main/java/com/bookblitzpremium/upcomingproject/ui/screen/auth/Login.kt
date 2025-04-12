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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ViewModel.UserLogin
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.SignInWithGoogle
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme


@Composable
fun LoginPage(showToggleToTablet: Boolean ,onNextButtonClicked: () -> Unit, navController: NavController){

    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f

    val viewModel  = viewModel<UserLogin>()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFFFFF9E5), // Light yellow
//                        Color(0xFFE6F0FA)  // Light blue
//                    )
//                )
//            )
    ) {

//        VideoPlayer(
//            videoUri = videoUri,
//            modifier = Modifier
//                .fillMaxSize()
//
//        )


        // 📝 Login UI - Positioned above the video
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
                .padding(horizontal = 28.dp,)
                .offset(x = offsetValueX),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ){

//            Spacer(modifier = Modifier.height(300.dp))

            Text(
                text = "Welcome Back!",
                style = AppTheme.typography.largeBold,
                modifier = Modifier
                    .padding(top = 0.dp, bottom = 30.dp)
                    .align(Alignment.CenterHorizontally)
            )

            CustomTextField(
                value = email,
                onValueChange = { email = it },
                label = "Username",
                placeholder = "Enter your username",
                leadingIcon = Icons.Default.Person,
                trailingIcon = Icons.Default.Clear,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = valueHorizontal, vertical = 12.dp)

            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = password,
                onValueChange = {  password = it },
                label = "Password",
                placeholder = "Enter your Password",
                leadingIcon = Icons.Default.Lock,
                trailingIcon = Icons.Default.Clear,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = valueHorizontal, vertical = 16.dp)

            )

            Text(
                text = "Forgot Password?",
                style = AppTheme.typography.bodyLarge,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(start = valueHorizontal, top = 0.dp)
                    .clickable {
                        navController.navigate(AppScreen.ForgotPassword.route)
                    }
            )

           Column(
               verticalArrangement = Arrangement.spacedBy(10.dp)
               , modifier = Modifier
                   .padding(top = 40.dp)
           ){

               ButtonHeader(
                   textResId = R.string.login,
                   valueHorizontal = valueHorizontal,
                   userFunction = {
                       viewModel.login(email, password)
                   },
                   navigationPage = {navController.navigate(AppScreen.HomeGraph.route)}
               )

//               ButtonHeader( R.string.forget_password, valueHorizontal,onNextButtonClicked = onNextButtonClicked )

                SignInWithGoogle(valueHorizontal, UserLogin(),email, password)

           }
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


//if enter more than three times assume forget passwrod pop up forget passwrpd