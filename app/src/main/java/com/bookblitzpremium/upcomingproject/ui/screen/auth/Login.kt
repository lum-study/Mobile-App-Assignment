package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.PlayArrow
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextFieldPassword
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme


@Composable
fun LoginPage(
    showToggleToTablet: Boolean,
    navController: NavController,
    viewModel: AuthViewModel,
    email: String = ""
) {
    val valueHorizontal: Dp = if (showToggleToTablet) 46.dp else 16.dp
    val offsetValueX: Dp = if (showToggleToTablet) 620.dp else 0.dp
    val maxSizeAvailable: Float = if (showToggleToTablet) 0.4f else 1f

    var email by rememberSaveable { mutableStateOf( if (email.isEmpty()) "" else email) }
    var password by rememberSaveable { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastTrigger by remember { mutableStateOf(0) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Error -> {
                val message = (authState as AuthState.Error).message
                toastMessage= message
                toastTrigger++
            }
            is AuthState.Authenticated -> {
                toastMessage = "Login succesfully"
            }
            else -> {}
        }
    }

    // Show Toast for validation errors or password verification
    LaunchedEffect(toastMessage, toastTrigger) {
        toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            toastMessage = null
        }
    }

    fun isFormValid(): Boolean {
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            toastMessage = "Please enter a valid email address"
            return false
        }

        if (password.isBlank()) {
            toastMessage = "Please enter a valid password"
            return false
        }
        return true
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.icon_logo),
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth(maxSizeAvailable)
                .padding(horizontal = 28.dp)
                .offset(x = offsetValueX),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
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
                trailingIcon = Icons.Default.Clear,
                shape = RoundedCornerShape(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = valueHorizontal, vertical = 12.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            CustomTextFieldPassword(
                value = password,
                onValueChange = { password = it },
                label = "Password",
                placeholder = "Enter your Password",
                shape = RoundedCornerShape(12.dp),
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
                        navController.navigate(AppScreen.ForgotPassword.route) {
                            launchSingleTop = true
                        }
                    }
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.padding(top = 40.dp)
            ) {
                ButtonHeader(
                    textResId = R.string.login,
                    valueHorizontal = valueHorizontal,
                    onClick ={
                        if(isFormValid()){
                            viewModel.login(email,password, onClick = {})
                        }
                    }
                )

                Text(
                    text = stringResource(R.string.register_account),
                    style = AppTheme.typography.bodyLarge,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = valueHorizontal, top = 30.dp)
                        .clickable {
                            navController.navigate(AppScreen.Register.route)
                        }
                )

                CheckStatusLoading(
                    isLoading = authState is AuthState.Loading,
                    backgroundAlpha = 0.5f,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                )
            }
        }
    }
}