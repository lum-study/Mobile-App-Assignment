package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.SignupState
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextFieldPassword
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme


@Composable
fun RegristerPage(
    navController: NavController,
    viewModel: AuthViewModel,
    localViewModel: LocalUserViewModel = hiltViewModel(),
    remoteUserViewModel : RemoteUserViewModel = hiltViewModel()
){
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    var confirmPassword by rememberSaveable { mutableStateOf("") }
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf<String?>(null) } // State for Toast message
    var toastTrigger by remember { mutableStateOf(0) } // Unique trigger for Toast
    val signupState by viewModel.signupState.collectAsState()
    var triggerSignup by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(signupState) {
        if (signupState is SignupState.Error) {
            toastMessage = (signupState as SignupState.Error).message
            toastTrigger++
            viewModel.clearSignUpState()
            triggerSignup = false
        }
    }

    LaunchedEffect(toastMessage, toastTrigger) {
        toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            toastMessage = null
        }
    }

    //validation
    fun getPasswordErrorMessage(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters long"
        }
//        if (!password.contains(Regex(".*[A-Z].*[A-Z].*"))) {
//            return "Password must contain at least 2 uppercase letters"
//        }
//        if (!password.contains(Regex(".*[!@#$&*].*"))) {
//            return "Password must contain at least 1 special character (!@#$&*)"
//        }
//        if (!password.contains(Regex(".*[0-9].*[0-9].*"))) {
//            return "Password must contain at least 2 digits"
//        }
//        if (!password.contains(Regex(".*[a-z].*[a-z].*[a-z].*"))) {
//            return "Password must contain at least 3 lowercase letters"
//        }
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
                shape = RoundedCornerShape(12.dp),
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
                shape = RoundedCornerShape(12.dp),
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
                        triggerSignup = true
                    }
                }
            )


            if(triggerSignup) {
                CheckStatusLoading(
                    isLoading = signupState is SignupState.Loading,
                    backgroundAlpha = 0.5f,
                    indicatorColor = MaterialTheme.colorScheme.primary,
                )

                LaunchedEffect(Unit) {
                    val exists = localViewModel.checkUserEmail(email)
                    if (!exists) {
                        val uid = viewModel.signup(email, password)
                        if (uid.isNotEmpty()) {
                            val username = email.substringBefore("@")
                            val user = User(uid = uid, username = username, email = email, password = password)
                            remoteUserViewModel.addUser(user)
                            localViewModel.insertNewUser(user)
                            viewModel.clearSignUpState()
                            triggerSignup = false
                        }
                    } else {
                        viewModel.setSignupError("Email is already registered")
                        triggerSignup = false
                    }
                }
            }
        }
    }
}