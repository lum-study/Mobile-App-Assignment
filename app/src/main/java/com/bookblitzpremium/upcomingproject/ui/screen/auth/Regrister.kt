package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.SignupState
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextFieldPassword
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.util.UUID


@Composable
fun RegristerPage(
    viewModel: AuthViewModel,
    navController: NavController,
){
    val localViewModel: LocalUserViewModel = hiltViewModel()
    val userError = localViewModel.loading.collectAsState()
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    val signupState by viewModel.signupState.collectAsState()
    var hello by rememberSaveable { mutableStateOf(false) }

//    LaunchedEffect(verifyEmailState, navigationCommand) {
//        when (verifyEmailState) {
//            is VerifyEmail.WaitingForVerification -> {
//                navController.navigate(AppScreen.VerifyEmailWaiting.route) {
//                    popUpTo(navController.graph.startDestinationId) {
//                        inclusive = true
//                    }
//                    launchSingleTop = true
//                }
//            }
//            is VerifyEmail.Success -> {
//                if (navigationCommand) {
//                    navController.navigate(AppScreen.Home.route) {
//                        popUpTo(navController.graph.startDestinationId) {
//                            inclusive = true
//                        }
//                        launchSingleTop = true
//                    }
//                    viewModel.clearNavigationCommand()
//                }
//            }
//            is VerifyEmail.Error -> {
//                if ((verifyEmailState as VerifyEmail.Error).message == "User not signed in. Please sign in again.") {
//                    navController.navigate(AppScreen.Register.route) {
//                        popUpTo(navController.graph.startDestinationId) {
//                            inclusive = true
//                        }
//                    }
//                }
//            }
//            else -> { /* Idle or Loading state, do nothing */ }
//        }
//    }

    //validation
    fun getPasswordErrorMessage(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters long"
        }
        if (!password.contains(Regex(".*[A-Z].*[A-Z].*"))) {
            return "Password must contain at least 2 uppercase letters"
        }
        if (!password.contains(Regex(".*[!@#$&*].*"))) {
            return "Password must contain at least 1 special character (!@#$&*)"
        }
        if (!password.contains(Regex(".*[0-9].*[0-9].*"))) {
            return "Password must contain at least 2 digits"
        }
        if (!password.contains(Regex(".*[a-z].*[a-z].*[a-z].*"))) {
            return "Password must contain at least 3 lowercase letters"
        }
        return null
    }

    fun doPasswordsMatch(): Boolean = password == confirmPassword

    fun isFormValid(): Boolean {
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }

        getPasswordErrorMessage(password)?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            return false
        }

        if (confirmPassword.isBlank()) {
            Toast.makeText(context, "Please confirm your password", Toast.LENGTH_SHORT).show()
            return false
        }
        if (!doPasswordsMatch()) {
            Toast.makeText(context, "Passwords do not match", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
                .offset(x = 0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Regrister new Account!",
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
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )

            CustomTextFieldPassword(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                placeholder = "Enter your Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )

            CustomTextFieldPassword(
                value = confirmPassword,
                onValueChange = { confirmPassword = it },
                label = "Confirm Password",
                placeholder = "Enter your Confirm Password",
                leadingIcon = Icons.Default.Lock,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            )

            ButtonHeader(
                textResId = R.string.register_text,
                valueHorizontal = 16.dp,
                onClick = {
                    if(isFormValid()){
                        viewModel.signup(email, password)
                        hello = true
                    }
                }
            )

            if(hello) {
                CheckStatusLoading(
                    isLoading = signupState is SignupState.Loading,
                    backgroundAlpha = 0.5f,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                )

                CheckStatusLoading(
                    isLoading = signupState is SignupState.Loading,
                    backgroundAlpha = 0.5f,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                )

                when (signupState) {
                    is SignupState.Error -> {
                        Text(
                            text = (signupState as SignupState.Error).message,
                            color = Color.Red,
                            modifier = Modifier.padding(16.dp)
                        )
                    }
                    else -> { /* Idle or Success state, do nothing */ }
                }

//                // Handle email verification state
//                when (verifyEmailState) {
//                    is VerifyEmail.Success -> {
//                        Text(
//                            text = "Verification email sent! Please check your inbox.",
//                            color = Color.Green,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                    is VerifyEmail.Error -> {
//                        Text(
//                            text = (verifyEmailState as VerifyEmail.Error).message,
//                            color = Color.Red,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                    else -> { /* Idle or Loading state, do nothing */ }
//                }

//                when (signupState) {
//                    is SignupState.Success -> {
//                        // Navigate to the next screen on success
////                        LaunchedEffect(Unit) {
////                            navController.navigate(AppScreen.HomeGraph.route) {
////                                popUpTo(navController.graph.startDestinationId) {
////                                    inclusive = true
////                                }
////                                launchSingleTop = true
////                            }
////                        }
//
//                        viewModel.NavigationCommandToHome()
//                    }
//                    is SignupState.Error -> {
//                        Text(
//                            text = (signupState as SignupState.Error).message,
//                            color = Color.Red,
//                            modifier = Modifier.padding(16.dp)
//                        )
//                    }
//                    else -> { /* Idle state, do nothing */ }
//                }
            }
        }
    }
}

