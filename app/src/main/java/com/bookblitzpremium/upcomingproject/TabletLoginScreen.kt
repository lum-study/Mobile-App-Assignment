package com.bookblitzpremium.upcomingproject

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextFieldPassword
import com.bookblitzpremium.upcomingproject.ui.screen.auth.OtpInputField
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import kotlin.collections.forEachIndexed

@Composable
fun LoginApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "step1") {
        composable("step1") { LoginScreen1(navController,true) }
        composable("step2") { LoginScreen2(navController,true) }
    }
}

@Composable
fun LoginScreen1(
    navController: NavController,
    tabletScreen : Boolean,
    viewModel: AuthViewModel = hiltViewModel()
) {

    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }
    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    var showDialog by remember { mutableStateOf(false) }

    var localViewModel : LocalUserViewModel = hiltViewModel()
    var checkTrigger by remember { mutableStateOf(0) }

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
                toastMessage = "Login succesful"

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

    var canProceed = remember { mutableStateOf(false) }

    LaunchedEffect(isFormValid()) {
        if (isFormValid()) {
            canProceed.value = true
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        val valueVertical: Dp = if (tabletScreen) 60.dp else 300.dp

        Spacer(modifier = Modifier.height(valueVertical))
        // Header: Title and Stepper
        Text(
            text = "Sign Up for Free",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "register to get loving trip package",
            style = TextStyle(
                fontSize = 16.sp,
                color = Color.Gray
            ),
            modifier = Modifier.padding(top = 4.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))


        // Stepper (1/3) with clickable bubbles
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentStep = 1 // This can be dynamic based on the current route
            repeat(2) { index ->
                val stepNumber = index + 1
                val isActive = stepNumber == currentStep
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isActive) Color.Black else Color.LightGray,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .then(
                            if (stepNumber <= currentStep || canProceed.value) {
                                Modifier.clickable {
                                    when (stepNumber) {
                                        1 -> navController.navigate("step1") { popUpTo(navController.graph.startDestinationId) }
                                        2 -> navController.navigate("step2") { popUpTo(navController.graph.startDestinationId) }
                                    }
                                }
                            } else {
                                Modifier // no click
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$stepNumber",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                if (index < 1) { // Draw line between steps
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Main Content: Personal Information
        Text(
            text = "PERSONAL INFORMATION",
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Text(
            text = "Provide the basic information to get you registered with us.",
            style = TextStyle(
                fontSize = 14.sp,
                color = Color.Gray
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 16.dp)
        )

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Username",
            placeholder = "Enter your username",
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal =0.dp, vertical = 12.dp)
        )

        CustomTextFieldPassword(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            placeholder = "Enter your Password",
            modifier = Modifier
                .fillMaxWidth()
        )

        Text(
            text = "Forgot Password?",
            style = AppTheme.typography.bodyLarge,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 10.dp)
                .clickable {
                    showDialog = true
                }
        )

        val state by viewModel.state.collectAsStateWithLifecycle()
        val focusRequesters = remember {
            List(4) { FocusRequester() }
        }


        if (showDialog) {
            PaymentDialog(
                onDismissRequest = { showDialog = false },
                onNextClick = {
                    showDialog = false
                    navController.navigate("step2")
                } ,
                state = state,
                focusRequesters = focusRequesters,
                navController = navController,
                onAction = { action ->
                    when (action) {
                        is OtpAction.OnEnterNumber -> {
                            if (action.number != null) {
                                focusRequesters[action.index].freeFocus()
                            }
                        }

                        else -> Unit
                    }
                    viewModel.onAction(action)
                }
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (tabletScreen) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Content for Row layout
                Button(
                    onClick = {
                        localViewModel.loginLocalUser(email, password) { uid, error ->
                            Log.e("User", uid.toString())
                            if (error != null) {
                                Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                toastTrigger++
                            } else if (uid != null) {
                                val username = email.substringBefore("@")
                                val user = User(
                                    id = uid,
                                    name = username,
                                    email = email,
                                    password = password
                                )
                                localViewModel.insertNewUser(user)
                                viewModel.login(email, password)
                                navController.navigate("step2")
                            }
                        }
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp)
                       ,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Sign up a account",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Next Button
                Button(
                    onClick = {
                       navController.navigate("step2")
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp)
                        .border( 1.dp, Color.Black, RoundedCornerShape(24.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (valueVertical == 20.dp) Color.Black else Color.White
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Next",
                        color = Color.Black,
                        fontSize = 16.sp
                    )
                }
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Content for Column layout
                Button(
                    onClick = {
                        navController.navigate("step2")
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Sign up a account",
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Next Button
                Button(
                    onClick = {
                        if (isFormValid()) {
                            localViewModel.loginLocalUser(email, password) { uid, error ->
                                Log.e("User", uid.toString())
                                if (error != null) {
                                    Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
                                    toastTrigger++
                                } else if (uid != null) {
                                    val username = email.substringBefore("@")
                                    val user = User(
                                        id = uid,
                                        name = username,
                                        email = email,
                                        password = password
                                    )
                                    localViewModel.insertNewUser(user)
                                    viewModel.login(email, password)
                                    navController.navigate("step2")
                                }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Next",
                        color = Color.Black,
                        fontSize = 16.sp
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
}

@Composable
fun LoginScreen2(navController: NavController, tabletScreen: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White) // Optional, can be removed if the image fully covers
    ) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.hiking_new),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(1200.dp,800.dp)
        )

        // Content over the background image
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Align elements in the center
        ) {
            val valueVertical: Dp = if (tabletScreen) 0.dp else 100.dp
            Spacer(modifier = Modifier.height(valueVertical))

            // Header: Title and Stepper
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(y = -60.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .offset(y = -150.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Thanks for joining us",
                        style = TextStyle(
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        ),
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = "register to get loving trip package",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = Color.Gray
                        ),
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Stepper (1/3) with clickable bubbles
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val currentStep = 2 // This can be dynamic based on the current route
                        repeat(2) { index ->
                            val stepNumber = index + 1
                            val isActive = stepNumber == currentStep
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = if (isActive) Color.Black else Color.LightGray,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .clickable {
                                        // Navigate to the corresponding step
                                        when (stepNumber) {
                                            1 -> navController.navigate("step1") { popUpTo(navController.graph.startDestinationId) }
                                            2 -> navController.navigate("step2") { popUpTo(navController.graph.startDestinationId) }
                                            3 -> navController.navigate("step3") { popUpTo(navController.graph.startDestinationId) }
                                        }
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$stepNumber",
                                    color = Color.White,
                                    fontSize = 12.sp
                                )
                            }
                            if (index < 1) { // Draw line between steps
                                Box(
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(2.dp)
                                        .background(Color.LightGray)
                                )
                            }
                        }
                    }
                }
            }

            // Next Button
            Button(
                onClick = {
                    navController.navigate("step1")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .padding(top = 20.dp), // Adjust the button's padding to ensure it has enough space
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Next",
                    color = Color.White,
                    fontSize = 16.sp
                )
            }
        }
    }
}




@Composable
fun PaymentDialog(
    onDismissRequest: () -> Unit,
    onNextClick: () -> Unit,
    state: OtpState,
    navController: NavController,
    focusRequesters: List<FocusRequester>,
    onAction: (OtpAction) -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    Dialog(
        onDismissRequest = {}
    ) {
        var email by rememberSaveable { mutableStateOf("") }
        var isRecomposed by remember { mutableStateOf(false) }
        Box(
            modifier = Modifier
                .height(450.dp)
                .width(500.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.height(20.dp))
                Image(
                    painter = painterResource(id = R.drawable.hotel_full),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp) // Adjust size as needed
                )

                Text(
                    text = "Forget Passwords",
                    style = AppTheme.typography.largeBold,
                    textAlign = TextAlign.Center
                )

                // This Column will recompose when `isRecomposed` state changes
                if (isRecomposed) {

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
                                    viewModel.sendPasswordResetEmail(email = email)
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

                Spacer(modifier = Modifier.weight(1f))

                ButtonHeader(
                    textResId = R.string.next_button,
                    valueHorizontal = 16.dp,
                    onClick = {
                        // Trigger recomposition here by updating the state
                        isRecomposed = !isRecomposed
                        onNextClick()
                    }
                )

            }

            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close dialog",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

