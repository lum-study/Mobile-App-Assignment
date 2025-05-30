package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
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

    var email by rememberSaveable { mutableStateOf("") }
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
            toastMessage = context.getString(R.string.please_enter_a_valid_email_address)
            return false
        }

        if (password.isBlank()) {
            toastMessage = context.getString(R.string.please_enter_a_valid_password)
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
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
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
                label = stringResource(R.string.enter_emails),
                placeholder = stringResource(R.string.enter_your_emails),
                leadingIcon = Icons.Default.Email,
                trailingIcon = Icons.Default.Clear,
                shape = RoundedCornerShape(8.dp),
                isEmailField = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )

            CustomTextFieldPassword(
                value = password,
                onValueChange = { password = it },
                label = stringResource(R.string.enter_password),
                placeholder = stringResource(R.string.enter_password),
                leadingIcon = Icons.Default.Lock,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            )

            Text(
                text = stringResource(R.string.forgot_password),
                style = AppTheme.typography.bodyLarge,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(start = 16.dp, top = 0.dp)
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
                    valueHorizontal = 16.dp,
                    onClick ={
                        if(isFormValid()){
                            viewModel.login(email,password)
                        }
                    }
                )

                Text(
                    text = stringResource(R.string.register_account),
                    style = AppTheme.typography.bodyLarge,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 16.dp, top = 30.dp)
                        .clickable {
                            navController.navigate(AppScreen.Register.route)
                        }
                )
            }
        }
    }

    CheckStatusLoading(
        isLoading = authState is AuthState.Loading,
        backgroundAlpha = 0.5f,
        indicatorColor = MaterialTheme.colorScheme.primary,
    )
}