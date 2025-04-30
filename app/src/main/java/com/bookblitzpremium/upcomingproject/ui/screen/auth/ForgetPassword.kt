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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.media3.common.util.Log
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CustomDialog
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.TextEmailSent
import com.bookblitzpremium.upcomingproject.ui.components.TextHeader
import kotlinx.coroutines.launch

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
//    var localViewModel : LocalUserViewModel = hiltViewModel()
    var checkTrigger by remember { mutableStateOf(0) }
//    val emailExists by localViewModel.emailExists.collectAsState()
//    val success by localViewModel.success.collectAsState()
    val remoteUser : RemoteUserViewModel = hiltViewModel()

//    // Validate and check email when input changes or button is clicked
//    LaunchedEffect(emails, checkTrigger) {
//        if (emails.isNotEmpty() && isValidEmail(emails)) {
//            localViewModel.checkUserEmail(emails)
//        }
//    }

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

            val scope = rememberCoroutineScope()
            val context = LocalContext.current

            ButtonHeader(
                textResId = R.string.login,
                valueHorizontal = valueHorizontal,
                onClick = {

                    scope.launch {
                        try {
                            val existingId = remoteUser.checkEmails(emails)
                            if (existingId.isNotEmpty()) {
                                Log.e("Verification", "Email found: Proceeding to OTP")
                                viewModel.sendPasswordResetEmail(emails)
                                navController.navigate("${AppScreen.OTP.route}/$emails")
                            } else {
                                Log.e("Verification", "Email not found")
                                Toast.makeText(context, "Email not registered!", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            Log.e("Verification", "Error verifying email: ${e.localizedMessage}")
                            Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }
    }
}

