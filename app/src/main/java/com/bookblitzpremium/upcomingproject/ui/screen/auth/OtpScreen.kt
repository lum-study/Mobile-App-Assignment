package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.bookblitzpremium.upcomingproject.ui.RequestNotificationPermissions

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Preview(showBackground = true)
@Composable
fun PreviewOtpScreen() {
    val navController = rememberNavController()

    // Mock state and viewModel for preview purposes
    val state = OtpState()

    val focusRequesters = List(4) { FocusRequester() } // 6 focus requesters for the OTP fields
    val userModel: AuthViewModel = hiltViewModel()

    OtpScreen(
        state = state,
        focusRequesters = focusRequesters,
        onAction = {},
        viewModel = userModel,
        navController = navController,
        modifier = Modifier
    )
}


@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun OtpScreen(
    state: OtpState,
    focusRequesters: List<FocusRequester>,
    viewModel: AuthViewModel,
    onAction: (OtpAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    var permissionGranted by remember { mutableStateOf(false) }

    // Request permission for notifications
    RequestNotificationPermissions(
        onPermissionGranted = { granted -> permissionGranted = granted }
    )

    val context = LocalContext.current

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Get the OTP code first", fontSize = 32.sp)

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
        ) {
            state.code.forEachIndexed { index, number ->
                OtpInputField(
                    number = number,
                    focusRequester = focusRequesters[index],
                    onFocusChanged = { if (it) onAction(OtpAction.OnChangeFieldFocused(index)) },
                    onNumberChanged = { newNumber -> onAction(OtpAction.OnEnterNumber(newNumber, index)) },
                    onKeyboardBack = { onAction(OtpAction.OnKeyboardBack) },
                )
            }
        }

        Text(
            text = "Resend OTP",
            style = MaterialTheme.typography.bodyMedium.copy(
                fontSize = 16.sp,
                color = Color.Black
            ),
            modifier = Modifier
                .padding(top = 24.dp)
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp)
                .clickable {
                    if (permissionGranted) {
                        viewModel.sendOTP(context)
                    } else {
                        Toast.makeText(context, "Please allow notifications", Toast.LENGTH_SHORT).show()
                    }
                }
        )

        if (state.isValid != null || state.isExpired) {
            LaunchedEffect(key1 = state.isValid, key2 = state.isExpired) {
                if (state.isExpired) {
                    Toast.makeText(context, "OTP expired. Please request a new one.", Toast.LENGTH_SHORT).show()
                } else if (state.isValid == true) {
                    Toast.makeText(context, "Valid code", Toast.LENGTH_SHORT).show()
                    navController.navigate(AppScreen.ForgotPassword.route) {
                        popUpTo(0){
                            inclusive = true
                        }
                        launchSingleTop = true // prevents multiple instances of the same screen from being created.
                    }

                } else {
                    Toast.makeText(context, "Invalid code", Toast.LENGTH_SHORT).show()
                    navController.navigate(AppScreen.Login.route) {
                        popUpTo(AppScreen.AuthGraph.route) {
                            inclusive = true
                        }
                        launchSingleTop = true // prevents multiple instances of the same screen from being created.
                    }
                    viewModel.updateStateOfOTP()
                }
            }
        }

        if (state.isExpired) {
            Text(
                text = "OTP expired. Please request a new one.",
                color = Color.Red,
                fontSize = 16.sp
            )
        }
    }
}


