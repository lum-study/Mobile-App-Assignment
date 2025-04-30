package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.view.KeyEvent
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState

@Composable
fun PermissionRequestScreen() {
    val context = LocalContext.current

    // Check if permission is granted (for UI feedback)
    val permissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        ContextCompat.checkSelfPermission(
            context,
            android.Manifest.permission.POST_NOTIFICATIONS
        ) == PackageManager.PERMISSION_GRANTED
    } else {
        true // Not required for pre-Android 13
    }

    // Launcher to request permission
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        // Handle the result of the permission request (optional)
        if (isGranted) {
            // Permission granted, proceed with your app logic
        } else {
            // Permission denied, handle accordingly (e.g., show a message)
        }
    }

    // State to control whether to show the custom permission screen
    var showPermissionScreen by remember { mutableStateOf(false) }

    // Automatically check permission and show custom screen if needed
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !permissionGranted) {
            showPermissionScreen = true // Show custom screen instead of launching directly
        }
    }

    // Show the custom permission screen if needed
    if (showPermissionScreen) {
        CustomPermissionScreen(
            onRequestPermission = {
                // When the user agrees, request the permission
                launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
                showPermissionScreen = false // Hide the screen after requesting
            },
            onDeny = {
                // When the user denies, hide the screen (or handle differently)
                showPermissionScreen = false
            }
        )
    } else {
        // Proceed with the rest of your app (e.g., show the main screen)
        MainAppContent()
    }
}

@Composable
fun CustomPermissionScreen(
    onRequestPermission: () -> Unit,
    onDeny: () -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "We Need Your Permission",
                fontSize = 24.sp,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            Text(
                text = "To keep you updated, we need permission to send notifications. This will allow us to notify you about important updates and events.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(bottom = 32.dp)
            )

            Button(
                onClick = onRequestPermission,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(horizontal = 16.dp)
            ) {
                Text("Grant Permission")
            }

            Spacer(modifier = Modifier.height(16.dp))

            TextButton(
                onClick = onDeny,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            ) {
                Text("Deny")
            }
        }
    }
}

@Composable
fun MainAppContent() {
    // Replace this with your app's main content (e.g., CountrySelectionScreen)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Main App Content")
    }
}

@SuppressLint("ObsoleteSdkInt")
@Composable
fun OtpScreen2(
    state: OtpState,
    focusRequesters: List<FocusRequester>,
    viewModel: AuthViewModel,
    onAction: (OtpAction) -> Unit,
    navController: NavController,
    modifier: Modifier = Modifier,
    email: String
) {

    val context = LocalContext.current

    // Check if permission is granted (for UI feedback)
    var permissionGranted by rememberSaveable {
        mutableStateOf(
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                ContextCompat.checkSelfPermission(
                    context,
                    android.Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
            } else {
                true // Not required for pre-Android 13
            }
        )
    }

    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        permissionGranted = isGranted // Update the state
        if (!isGranted) {
            // Check if we should show rationale (i.e., user denied but didn't select "Don't ask again")
            val activity = context as? ComponentActivity
            val shouldShowRationale = activity?.let {
                ActivityCompat.shouldShowRequestPermissionRationale(it, android.Manifest.permission.POST_NOTIFICATIONS)
            } ?: false

            if (!shouldShowRationale && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                // Permission permanently denied ("Don't ask again" selected)
                Toast.makeText(
                    context,
                    "Notification permission was permanently denied. Please enable it in settings.",
                    Toast.LENGTH_LONG
                ).show()

                // Optionally, direct the user to app settings
                val intent = Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = android.net.Uri.fromParts("package", context.packageName, null)
                }
                context.startActivity(intent)
            } else {
                // Permission denied but can ask again
                Toast.makeText(context, "Please allow notifications", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Automatically request permission on Android 13+
    LaunchedEffect(Unit) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !permissionGranted) {
            launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize() // Fill the entire screen height
            .padding(16.dp)
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
                .fillMaxWidth()
                .padding(16.dp), // Reduced padding to avoid excessive spacing
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(70.dp))

            Text(
                text = "OTP Verification",
                style = TextStyle(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 30.dp)
            )
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 24.dp)
            ) {
                state.code.forEachIndexed { index, number ->
                    OtpInputField(
                        number = number,
                        focusRequester = focusRequesters[index],
                        onFocusChanged = { if (it) onAction(OtpAction.OnChangeFieldFocused(index)) },
                        onNumberChanged = { newNumber ->
                            onAction(
                                OtpAction.OnEnterNumber(
                                    newNumber,
                                    index
                                )
                            )
                        },
                        onKeyboardBack = { onAction(OtpAction.OnKeyboardBack) },
                    )
                }
            }
            Text(
                text = "Resend OTP",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = Color(0xFF673AB7),
                    fontWeight = FontWeight.Medium
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
                            Toast.makeText(
                                context,
                                "Please allow notifications",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            )

            if (state.isValid != null || state.isExpired) {
                LaunchedEffect(key1 = state.isValid, key2 = state.isExpired) {
                    if (state.isExpired) {
                        Toast.makeText(
                            context,
                            "OTP expired. Please request a new one.",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else if (state.isValid == true) {
                        Toast.makeText(context, "Valid code", Toast.LENGTH_SHORT).show()
//                        viewModel.sendPasswordResetEmail(email = email)
                        navController.navigate(AppScreen.Home.route) {
                            popUpTo(0) {
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
                            launchSingleTop =
                                true // prevents multiple instances of the same screen from being created.
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
                    index = if (number != null) 1 else 0
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
                if (newNumber.length <= 1 && newNumber.isDigitsOnly()) {
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
                    if (didPressDelete && number == null) {
                        onKeyboardBack()
                    }
                    false
                },
            decorationBox = { innerBox ->
                innerBox()
                if (!isFocused && number == null) {
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
