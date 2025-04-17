package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import com.bookblitzpremium.upcomingproject.R
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@SuppressLint("ObsoleteSdkInt")
@OptIn(ExperimentalPermissionsApi::class)
@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun OtpScreen2(
    state: OtpState,
    focusRequesters: List<FocusRequester>,
    viewModel: AuthViewModel,
    onAction: (OtpAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier
){

    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (!isGranted) {
            Toast.makeText(context, "Please allow notifications", Toast.LENGTH_SHORT).show()
        }
    }

    // Check if permission is granted (for UI feedback)
    val permissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true // Not required for pre-Android 13
    }

    // Automatically request permission on Android 13+
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !permissionGranted) {
            launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize() // Fill the entire screen height
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp), // Reduced padding to avoid excessive spacing
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(70.dp))

            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape)
                    .background(Color.Gray) // or any color like Color.Gray
            ) {
                Image(
                    painter = painterResource(id = R.drawable.otp1),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }

//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                when {
//                    permissionState.status.isGranted -> {
//                        Text(
//                            text = "Notification permission granted",
//                            color = Color.Green,
//                            modifier = Modifier.padding(vertical = 8.dp)
//                        )
//                    }
//                    permissionState.status is PermissionStatus.Denied -> {
//                        val shouldShowRationale =
//                            (permissionState.status as PermissionStatus.Denied).shouldShowRationale
//                        if (shouldShowRationale) {
//                            OutlinedButton(
//                                onClick = {
//                                    permissionState.launchPermissionRequest()
//                                }
//                            ) {
//                                Text(text = "Allow Notifications")
//                            }
//                        }
//                    }
//                }

            Text(
                text = "OTP Verification",
                style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.Black),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 30.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 12.dp, vertical = 24.dp)
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
                style = TextStyle(fontSize = 16.sp, color = Color(0xFF673AB7), fontWeight = FontWeight.Medium),
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

                if(state.isValid != null || state.isExpired) {
                    LaunchedEffect(key1 = state.isValid, key2 = state.isExpired) {
                        if (state.isExpired) {
                            Toast.makeText(context, "OTP expired. Please request a new one.", Toast.LENGTH_SHORT).show()
                        } else if (state.isValid == true) {
                            Toast.makeText(context, "Valid code", Toast.LENGTH_SHORT).show()
                            navController.navigate(AppScreen.ForgotPassword.route) {
                                popUpTo(0){
                                    inclusive = true
                                }
                                launchSingleTop = true
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
}

@Composable
fun OtpInputField(
    number: Int?,
    focusRequester: FocusRequester,
    onFocusChanged: (Boolean) -> Unit,
    onNumberChanged: (Int?) -> Unit,
    onKeyboardBack: () -> Unit,
) {
    val text by remember(number) {
        mutableStateOf(
            TextFieldValue(
                text = number?.toString().orEmpty(),
                selection = TextRange(
                    index = if(number != null) 1 else 0
                )
            )
        )
    }
    var isFocused by remember {
        mutableStateOf(false)
    }


    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = Color.Gray
            )
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        BasicTextField(
            value = text,
            onValueChange = { newText ->
                val newNumber = newText.text
                if(newNumber.length <= 1 && newNumber.isDigitsOnly()) {
                    onNumberChanged(newNumber.toIntOrNull())
                }
            },
            cursorBrush = SolidColor(Color.Black),
            singleLine = true,
            textStyle = TextStyle(
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Light,
                fontSize = 16.sp,
                color = Color.Black
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.NumberPassword
            ),
            modifier = Modifier
                .size(65.dp, 65.dp)
                .padding(25.dp)
                .focusRequester(focusRequester)
                .onFocusChanged {
                    isFocused = it.isFocused
                    onFocusChanged(it.isFocused)
                }
                .onKeyEvent { event ->
                    val didPressDelete = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
                    if(didPressDelete && number == null) {
                        onKeyboardBack()
                    }
                    false
                },
            decorationBox = { innerBox ->
                innerBox()
                if(!isFocused && number == null) {
                    Text(
                        text = "-",
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Light,
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentSize()
                    )
                }
            }
        )
    }
}

@Preview
@Composable
private fun OtpInputFieldPreview() {
    OtpInputField(
        number = null,
        focusRequester = remember { FocusRequester() },
        onFocusChanged = {},
        onKeyboardBack = {},
        onNumberChanged = {},
    )
}



//
//@Composable
//fun OtpInputField(
//    number: Int?,
//    focusRequester: FocusRequester,
//    onFocusChanged: (Boolean) -> Unit,
//    onNumberChanged: (Int?) -> Unit,
//    onKeyboardBack: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    var isFocused by remember { mutableStateOf(false) }
//    val text = remember(number) { TextFieldValue(number?.toString() ?: "") }
//
//    BasicTextField(
//        value = text,
//        onValueChange = { newText ->
//            val newNumber = newText.text
//            if (newNumber.length <= 1 && newNumber.isDigitsOnly()) {
//                onNumberChanged(newNumber.toIntOrNull())
//            }
//        },
//        cursorBrush = SolidColor(Color.Black),
//        singleLine = true,
//        textStyle = TextStyle(
//            textAlign = TextAlign.Center,
//            fontWeight = FontWeight.Bold,
//            fontSize = 20.sp,
//            color = Color.Black
//        ),
//        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword),
//        modifier = modifier
//            .size(48.dp, 48.dp)
//            .background(Color(0xFFE0E0E0), RoundedCornerShape(8.dp))
//            .focusRequester(focusRequester)
//            .onFocusChanged {
//                isFocused = it.isFocused
//                onFocusChanged(it.isFocused)
//            }
//            .onKeyEvent { event ->
//                val didPressDelete = event.nativeKeyEvent.keyCode == KeyEvent.KEYCODE_DEL
//                if (didPressDelete && number == null) {
//                    onKeyboardBack()
//                }
//                false
//            },
//        decorationBox = { innerBox ->
//            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
//                innerBox()
//            }
//        }
//    )
//}
