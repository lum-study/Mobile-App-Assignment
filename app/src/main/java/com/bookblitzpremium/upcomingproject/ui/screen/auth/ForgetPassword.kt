package com.bookblitzpremium.upcomingproject.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.VerifyEmail
import com.bookblitzpremium.upcomingproject.isValidEmail
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.components.CustomDialog
import com.bookblitzpremium.upcomingproject.ui.components.TextEmailSent
import com.bookblitzpremium.upcomingproject.ui.components.TextHeader

@Preview(showBackground = true)
@Composable
fun PreviewForgetPassword() {
    val navController = rememberNavController()
    ForgetPassword(
        showToggleToTablet = false, // Example value
        onNextButtonClicked = {} ,
        viewModel = viewModel(),
        navController = navController
    )
}


@Composable
fun ForgetPassword(
    showToggleToTablet: Boolean,
    onNextButtonClicked: () -> Unit,
    viewModel: AuthViewModel,
    navController: NavController
){
    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
    val offsetValueY = if (showToggleToTablet) 120.dp else 200.dp


    var emails by rememberSaveable { mutableStateOf("") }
    var localViewModel : LocalUserViewModel = hiltViewModel()
    var checkTrigger by remember { mutableStateOf(0) }
    val emailExists by localViewModel.emailExists.collectAsState()
    val success by localViewModel.success.collectAsState()

//    // Validate and check email when input changes or button is clicked
//    LaunchedEffect(emails, checkTrigger) {
//        if (emails.isNotEmpty() && isValidEmail(emails)) {
//            localViewModel.checkUserEmail(emails)
//        }
//    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Tesla logo (replace R.drawable.logo with your actual logo resource)
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = null,
//                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(64.dp)
            )

            // Language selector
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow, // Use a globe icon
                    contentDescription = "Language",
                    tint = Color.Black,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "en-MY",
                    fontSize = 16.sp,
                    color = Color.Black
                )
            }
        }

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .offset(x = offsetValueX),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {

            TextHeader( stringResource(R.string.forget_password) )

            Spacer(modifier = Modifier.height(16.dp))

            TextEmailSent()

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = emails,
                onValueChange = { emails = it },
                label = "Enter",
                placeholder = "Enter your Emails",
                shape = RoundedCornerShape(12.dp),
                leadingIcon = Icons.Default.Email,
                trailingIcon = Icons.Default.Clear,
                keyBoardType = KeyboardType.Email,  // ✅ Correctly passing KeyboardType
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = valueHorizontal, vertical = 16.dp)
            )

            var verifyEmailEnter by rememberSaveable { mutableStateOf(false) }

            Spacer(modifier = Modifier.height(10.dp))
            ButtonHeader(
                textResId = R.string.login,
                valueHorizontal = valueHorizontal,
                onClick = {
                    if (emails.isBlank()) {
                        verifyEmailEnter = true
                    }else{
                        checkTrigger++
                        viewModel.sendPasswordResetEmail(emails)
                        navController.navigate("${AppScreen.OTP.route}/$emails")
                    }
//                    viewModel.resetVerifyEmailState()
                }
            )


            if( verifyEmailEnter){
                CustomDialog(
                    onNextClick = {
                        verifyEmailEnter = false
                    },
                    onDismissRequest = {
                        verifyEmailEnter = false
                    }
                )
            }else{

            }

            // Success message
            success?.let { successMessage ->
                Text(
                    text = successMessage,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(8.dp)
                )
            }

            // Email existence result and navigation
            emailExists?.let { exists ->
                if (exists) {
                    Text(
                        text = "Email is already registered",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(8.dp)
                    )
                } else {
                    Text(
                        text = "Email is available",
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }

//            CheckStatusLoading(
//                isLoading = verifyEmail is VerifyEmail.Loading,
//                backgroundAlpha = 0.5f,
//                indicatorColor = MaterialTheme.colorScheme.primary,
//            )
//
//            if (verifyEmail is VerifyEmail.Error) {
//                val message = (verifyEmail as VerifyEmail.Error).message
//                Text(text = message, color = Color.Red)
//            }
//
//
//            LaunchedEffect(verifyEmail) {
//                if (verifyEmail is VerifyEmail.Verified) {
//                    navController.navigate("${AppScreen.OTP.route}/$emails")
//                    viewModel.resetVerifyEmailState()
//                }
//            }
        }
    }
}