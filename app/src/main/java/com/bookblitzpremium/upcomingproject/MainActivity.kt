package com.bookblitzpremium.upcomingproject

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.model.AuthState
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextFieldPassword
import com.bookblitzpremium.upcomingproject.ui.components.SignInWithGoogle
import com.bookblitzpremium.upcomingproject.ui.navigation.AppNavigation
import com.bookblitzpremium.upcomingproject.ui.screen.auth.LoginPage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint

enum class TravelScreen() {
    LOGIN,
    REGRISTER,
    HOME,
    PROFILE,
    BOOKINGS,
    Start,
    FORGETPASSWORD,
    OTP
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("MainActivity", "onCreate started")
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            AppTheme{
                App()
                val navController = rememberNavController()

//                LoginPage(false,navController,)
//                LoginPage(
//                    showToggleToTablet = false,
//                    navController = navController,
//                )
//                InvoiceScreen()
//                MainApp()
//                Heelo()
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 360, heightDp = 806)
@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val startDestination = AppScreen.AuthGraph.route
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = AppScreen.valueOf(
        backStackEntry?.destination?.route ?: AppScreen.AuthGraph.route
    )

    Scaffold(
        topBar = {
            if (currentScreen.hasTopBar) {
                TitleBar(
                    currentScreen = currentScreen,
                    canNavigateBack = navController.previousBackStackEntry != null,
                    navigateUp = { navController.navigateUp() },
                    modifier = Modifier.padding(start = 20.dp)

                )
            }
        },
        bottomBar = {
            if(currentScreen.hasBottomBar){
            BottomAppBar(
                containerColor = Color(0xFFC4C4C4),
                contentColor = Color.White,
                modifier = Modifier.height(65.dp)
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom app bar",
                    color = Color.Black
                )
            }
            }
        }
    ) { innerPadding ->
        AppNavigation(navController, startDestination, Modifier.padding(innerPadding))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = currentScreen.name.replace(Regex("([a-z])([A-Z])"), "$1 $2"),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center, // ✅ Ensure text is centered
                modifier = Modifier.fillMaxWidth()
                    .offset(x = -20.dp)
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.height(74.dp), // ✅ Set correct height
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        modifier = Modifier.size(24.dp) // ✅ Adjust icon size
                    )
                }
            }
        }
    )
}

@Composable
fun HomeScreen(
    onLogout: () -> Unit,
    viewModel: AuthViewModel = hiltViewModel()
) {
    val user by viewModel.userDetails.collectAsState() // Fixed typo

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (user != null) { // Now correctly checks user.value
            Text("Welcome, ${user?.displayName ?: "User"}!")
            Spacer(modifier = Modifier.height(8.dp))
            Text("Email: ${user?.email ?: "N/A"}")
            Spacer(modifier = Modifier.height(8.dp))
            Text("UID: ${user?.uid}")
            Spacer(modifier = Modifier.height(16.dp))
            if (user?.isEmailVerified == false) {
                Text("Please verify your email.")
            }
        } else {
            Text("No user data available.")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = onLogout) {
            Text("Logout")
        }
    }
}
//
//@RequiresApi(Build.VERSION_CODES.TIRAMISU)
//@Composable
//fun LoginPage(
//    showToggleToTablet: Boolean,
//    navController: NavController,
////    viewModel: AuthViewModel = hiltViewModel()
//) {
//    val valueHorizontal: Dp = if (showToggleToTablet) 46.dp else 16.dp
//    val offsetValueX: Dp = if (showToggleToTablet) 620.dp else 0.dp
//    val maxSizeAvailable: Float = if (showToggleToTablet) 0.4f else 1f
//
//    var email by rememberSaveable { mutableStateOf("") }
//    var password by rememberSaveable { mutableStateOf("") }
//    val authState by viewModel.authState.collectAsState()
//    val context = LocalContext.current
//
//    // Show error message if authState is Error
//    LaunchedEffect(authState) {
//        if (authState is AuthState.Error) {
//            val errorMessage = (authState as AuthState.Error).message
//            androidx.media3.common.util.Log.d("LoginPage", "authState is Error: $errorMessage")
//            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
//        }
//    }
//
//    //validation
//    fun getPasswordErrorMessage(password: String): String? {
//        if (password.length < 8) {
//            return "Password must be at least 8 characters long"
//        }
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
//        return null
//    }
//
//
//    fun isFormValid(): Boolean {
//        if (email.isBlank() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            Toast.makeText(context, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
//            return false
//        }
//
//        getPasswordErrorMessage(password)?.let { errorMessage ->
//            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
//            return false
//        }
//
//        return true
//    }
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(maxSizeAvailable)
//                .padding(horizontal = 28.dp)
//                .offset(x = offsetValueX),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center
//        ) {
//            Text(
//                text = "Welcome Back!",
//                style = AppTheme.typography.largeBold,
//                modifier = Modifier
//                    .padding(top = 0.dp, bottom = 30.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
//
//            CustomTextField(
//                value = email,
//                onValueChange = { email = it },
//                label = "Username",
//                placeholder = "Enter your username",
//                leadingIcon = Icons.Default.Person,
//                trailingIcon = Icons.Default.Clear,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = valueHorizontal, vertical = 12.dp)
//            )
//
//            Spacer(modifier = Modifier.height(16.dp))
//
//            CustomTextFieldPassword(
//                value = password,
//                onValueChange = { password = it },
//                label = "Password",
//                placeholder = "Enter your Password",
//                leadingIcon = Icons.Default.Lock,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = valueHorizontal, vertical = 16.dp)
//            )
//
//            Text(
//                text = "Forgot Password?",
//                style = AppTheme.typography.bodyLarge,
//                textDecoration = TextDecoration.Underline,
//                modifier = Modifier
//                    .align(Alignment.End)
//                    .padding(start = valueHorizontal, top = 0.dp)
//                    .clickable {
//                        androidx.media3.common.util.Log.e("Navigation", "Navigating to OTP screen")
//                        navController.navigate(AppScreen.OTP.route) {
//                            launchSingleTop = true
//                        }
//                    }
//            )
//
//            Column(
//                verticalArrangement = Arrangement.spacedBy(10.dp),
//                modifier = Modifier.padding(top = 40.dp)
//            ) {
//                ButtonHeader(
//                    textResId = R.string.login,
//                    valueHorizontal = valueHorizontal,
//                    onClick = {
//                        if(isFormValid()){
//                            viewModel.login(email, password)
//                        }
//                    }
//                )
//
//                if (authState !is AuthState.Authenticated) {
//                    SignInWithGoogle(
//                        valueHorizontal = valueHorizontal,
//                        viewModel = viewModel,
//                        email = email,
//                        password = password
//                    )
//                }
//
//                Text(
//                    text = stringResource(R.string.register_account),
//                    style = AppTheme.typography.bodyLarge,
//                    textDecoration = TextDecoration.Underline,
//                    modifier = Modifier
//                        .align(Alignment.Start)
//                        .padding(start = valueHorizontal, top = 30.dp)
//                        .clickable {
//                            androidx.media3.common.util.Log.d("Navigation", "Navigating to Register screen")
//                            navController.navigate(AppScreen.Register.route)
//                        }
//                )
//            }
//        }
//
//        CheckStatusLoading(
//            isLoading = authState is AuthState.Loading,
//            backgroundAlpha = 0.5f,
//            indicatorColor = MaterialTheme.colorScheme.primary,
//        )
//
//    }
//}


@Composable
fun Heelo(){
    Text(text = "dfgffhgf")
}


