package com.bookblitzpremium.upcomingproject.TabletAuth

import android.widget.Toast
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextFieldPassword
import com.bookblitzpremium.upcomingproject.ui.screen.auth.OtpInputField
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import kotlinx.coroutines.launch

fun String.encodeToUri(): String = this.toUri().toString().substringAfterLast("/")

@Composable
fun TabletLoginScreen(
    navController: NavController,
    tabletScreen: Boolean,
    email: String = "",
    password: String = "",
    viewModel: AuthViewModel
) {
    var email by rememberSaveable { mutableStateOf(email) }
    var password by rememberSaveable { mutableStateOf(password) }

    val authState by viewModel.authState.collectAsState()
    val context = LocalContext.current
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastTrigger by remember { mutableStateOf(0) }

    LaunchedEffect(authState) {
        when (authState) {
            is AuthState.Error -> {
                val message = (authState as AuthState.Error).message
                toastMessage = message
                toastTrigger++
            }
            else -> {}
        }
    }

    LaunchedEffect(email,password) {
        viewModel.updateEmails(email)
        viewModel.updatePassword(password)
    }

    LaunchedEffect(toastMessage, toastTrigger) {
        toastMessage?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            toastMessage = null
        }
    }

    fun getPasswordErrorMessage(password: String): String? {
        if (password.length < 8) {
            return "Password must be at least 8 characters long"
        }
        return null
    }

    fun isFormValid(): Boolean {
        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        getPasswordErrorMessage(password)?.let { errorMessage ->
            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.height(100.dp))

        Text(
            text = "Welcome back",
            style = AppTheme.typography.largeBold,
            color = AppTheme.colorScheme.onBackground,
            modifier = Modifier.padding(top = 16.dp)
        )

        Text(
            text = "Get yours loving trip package",
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colorScheme.secondary,
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
            val currentStep = 1
            repeat(2) { index ->
                val stepNumber = index + 1
                val isActive = stepNumber == currentStep
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isActive) AppTheme.colorScheme.primary else AppTheme.colorScheme.surfaceVariant,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .then(
                            if (stepNumber <= currentStep) {
                                Modifier.clickable {
                                    if (authState is AuthState.Authenticated) {
                                        when (stepNumber) {
                                            1 -> navController.navigate("${AppScreen.Login.route}/${email.encodeToUri()}/${password.encodeToUri()}")
                                        }
                                    }
                                }
                            } else {
                                Modifier
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$stepNumber",
                        color = AppTheme.colorScheme.onPrimary,
                        style = AppTheme.typography.smallSemiBold
                    )
                }
                if (index < 1) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                            .background(AppTheme.colorScheme.surfaceVariant)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Main Content: Personal Information
        Text(
            text = "PERSONAL INFORMATION",
            style = AppTheme.typography.mediumBold,
            color = AppTheme.colorScheme.onBackground,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
        )

        Text(
            text = "Provide the basic information to get you registered with us.",
            style = AppTheme.typography.labelMedium,
            color = AppTheme.colorScheme.secondary,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, bottom = 16.dp)
        )

        CustomTextField(
            value = email,
            onValueChange = { email = it },
            label = "Enter email",
            placeholder = "Enter your email",
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
            label = "Enter password",
            placeholder = "Enter your password",
            leadingIcon = Icons.Default.Lock,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp)
        )

        Text(
            text = "Forgot Password?",
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colorScheme.secondary,
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
                    navController.navigate(AppScreen.Home.route){
                        popUpTo(AppScreen.Login.route){
                            inclusive = true
                        }
                    }
                },
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

        if (tabletScreen == false) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate(AppScreen.Register.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Sign up a account",
                        style = AppTheme.typography.mediumBold,
                        color = AppTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if(isFormValid()){
                            viewModel.login(email, password, onClick = {
                                navController.navigate("${AppScreen.WelcomeLoginScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}")
                                viewModel.clearEmailPassword()
                            },true)
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Next",
                        style = AppTheme.typography.mediumBold,
                        color = AppTheme.colorScheme.onPrimary
                    )
                }
            }
        } else {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        navController.navigate(AppScreen.Register.route)
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Sign up a account",
                        style = AppTheme.typography.mediumBold,
                        color = AppTheme.colorScheme.onPrimary
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        if(isFormValid()){
                            viewModel.login(email, password, onClick = {
                                navController.navigate("${AppScreen.WelcomeLoginScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}")
                                viewModel.clearEmailPassword()
                            },true)
                        }
                    },
                    modifier = Modifier
                        .weight(0.5f)
                        .height(48.dp)
                        .border(1.dp, AppTheme.colorScheme.onBackground, RoundedCornerShape(24.dp)),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Text(
                        text = "Next",
                        style = AppTheme.typography.mediumBold,
                        color = AppTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }

    CheckStatusLoading(
        isLoading = authState is AuthState.Loading,
        backgroundAlpha = 0.5f,
        indicatorColor = AppTheme.colorScheme.primary
    )
}

@Composable
fun LoginWelcomeScreen(
    navController: NavController,
    tabletScreen: Boolean,
    email: String = "",
    password: String = "",
) {
    val viewModel: AuthViewModel = hiltViewModel()
    val authState by viewModel.authState.collectAsState()



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colorScheme.background)
    ) {
        Image(
            painter = painterResource(id = if (tabletScreen) R.drawable.hiking_potrait else R.drawable.hiking_new),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val valueVertical = if (tabletScreen) 60.dp else 40.dp
            Spacer(modifier = Modifier.height(valueVertical))
            Spacer(modifier = Modifier.weight(1f))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.background.copy(alpha = 0.9f))
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Thanks for joining us",
                        style = AppTheme.typography.largeBold,
                        color = AppTheme.colorScheme.onBackground,
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Text(
                        text = "register to get loving trip package",
                        style = AppTheme.typography.bodyLarge,
                        color = AppTheme.colorScheme.secondary,
                        modifier = Modifier.padding(top = 4.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val currentStep = 2
                        repeat(2) { index ->
                            val stepNumber = index + 1
                            val isActive = stepNumber == currentStep
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .background(
                                        color = if (isActive) AppTheme.colorScheme.primary else AppTheme.colorScheme.surfaceVariant,
                                        shape = RoundedCornerShape(32.dp)
                                    )
                                    .then(
                                        if (stepNumber <= currentStep) {
                                            Modifier.clickable {
                                                if (authState is AuthState.Authenticated) {
                                                    when (stepNumber) {
                                                        1 -> navController.navigate("${AppScreen.Login.route}/${email.encodeToUri()}/${password.encodeToUri()}")
                                                        2 -> navController.navigate("${AppScreen.WelcomeLoginScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}")
                                                    }
                                                }
                                            }
                                        } else {
                                            Modifier
                                        }
                                    ),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$stepNumber",
                                    color = AppTheme.colorScheme.onPrimary,
                                    style = AppTheme.typography.smallSemiBold
                                )
                            }
                            if (index < 1) {
                                Box(
                                    modifier = Modifier
                                        .width(24.dp)
                                        .height(2.dp)
                                        .background(AppTheme.colorScheme.surfaceVariant)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            viewModel.clearEmailPassword()
                            navController.navigate(AppScreen.Home.route){
                                popUpTo(AppScreen.Login.route){
                                    inclusive = true
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.primary
                        ),
                        shape = RoundedCornerShape(24.dp)
                    ) {
                        Text(
                            text = "Next",
                            style = AppTheme.typography.mediumBold,
                            color = AppTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(valueVertical))
        }
    }
}

@Composable
fun PaymentDialog(
    onDismissRequest: () -> Unit,
    onNextClick: () -> Unit = {},
    state: OtpState,
    navController: NavController,
    focusRequesters: List<FocusRequester>,
    onAction: (OtpAction) -> Unit,
    viewModel: AuthViewModel = hiltViewModel(),
    userModel: AuthViewModel = hiltViewModel()
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
                .background(AppTheme.colorScheme.background)
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
                    modifier = Modifier.size(100.dp)
                )

                if (isRecomposed) {
                    val context = LocalContext.current

                    LaunchedEffect(Unit) {
                        viewModel.sendOTP(context)
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "OTP Verification",
                            style = AppTheme.typography.largeBold,
                            color = AppTheme.colorScheme.onBackground,
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                        )

                        OTP()

                        Text(
                            text = "Resend OTP",
                            style = AppTheme.typography.bodyLarge,
                            color = AppTheme.colorScheme.primary,
                            modifier = Modifier
                                .padding(top = 24.dp)
                                .border(
                                    width = 1.dp,
                                    color = AppTheme.colorScheme.secondary,
                                    shape = RoundedCornerShape(12.dp)
                                )
                                .padding(10.dp)
                                .clickable {
                                    viewModel.sendOTP(context)
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
                                } else {
                                    Toast.makeText(context, "Invalid code", Toast.LENGTH_SHORT).show()
                                    navController.navigate(AppScreen.Login.route) {
                                        popUpTo(AppScreen.AuthGraph.route) {
                                            inclusive = true
                                        }
                                        launchSingleTop = true
                                    }
                                    viewModel.updateStateOfOTP()
                                }
                            }
                        }

                        if (state.isExpired) {
                            Text(
                                text = "OTP expired. Please request a new one.",
                                color = AppTheme.colorScheme.error,
                                style = AppTheme.typography.bodyLarge
                            )
                        }
                    }
                } else {
                    Text(
                        text = "Forget Passwords",
                        style = AppTheme.typography.largeBold,
                        color = AppTheme.colorScheme.onBackground,
                        textAlign = TextAlign.Center
                    )

                    CustomTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = "Enter",
                        placeholder = "Enter your Emails",
                        shape = RoundedCornerShape(12.dp),
                        leadingIcon = Icons.Default.Email,
                        trailingIcon = Icons.Default.Clear,
                        keyBoardType = androidx.compose.ui.text.input.KeyboardType.Email,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 0.dp, vertical = 16.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                val context = LocalContext.current
                var scope = rememberCoroutineScope()
                var remoteUser: RemoteUserViewModel = hiltViewModel()
                ButtonHeader(
                    textResId = R.string.next_button,
                    valueHorizontal = 16.dp,
                    onClick = {
                        scope.launch {
                            try {
                                val existingId = remoteUser.checkEmails(email)
                                if (existingId.isNotEmpty()) {
                                    viewModel.sendPasswordResetEmail(email)
                                    isRecomposed = !isRecomposed
                                } else {
                                    Toast.makeText(context, "Email not registered!", Toast.LENGTH_SHORT).show()
                                }
                            } catch (e: Exception) {
                                Toast.makeText(context, "Something went wrong. Please try again.", Toast.LENGTH_SHORT).show()
                            }
                        }
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
                    tint = AppTheme.colorScheme.onBackground,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}

@Composable
fun OTP(
    userModel: AuthViewModel = hiltViewModel()
) {
    val state by userModel.state.collectAsStateWithLifecycle()
    val focusRequesters = remember {
        List(4) { FocusRequester() }
    }

    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.code.none { it == null }
        if (allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    OTPScreen(
        state = state,
        focusRequesters = focusRequesters,
        onAction = { action ->
            when (action) {
                is OtpAction.OnEnterNumber -> {
                    if (action.number != null) {
                        focusRequesters[action.index].freeFocus()
                    }
                }
                else -> Unit
            }
            userModel.onAction(action)
        },
    )
}

@Composable
fun OTPScreen(
    state: OtpState,
    focusRequesters: List<FocusRequester>,
    onAction: (OtpAction) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 60.dp, vertical = 24.dp)
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
}