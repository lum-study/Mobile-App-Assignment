package com.bookblitzpremium.upcomingproject

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.navigation.AppNavigation
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


@Composable
fun Heelo(){
    Text(text = "dfgffhgf")
}

//@Composable
//fun MainApp() {
//    val navController = rememberNavController()
//    val authViewModel: UserLogin = hiltViewModel()
//
//    // Observe navigation commands from AuthViewModel
//    val navigationCommand by authViewModel.navigationCommand.collectAsState()
//
//    LaunchedEffect(navigationCommand) {
//        navigationCommand?.let { destination ->
//            Log.d("MainApp", "Navigating to $destination")
//            navController.navigate(destination) {
//                popUpTo(navController.graph.startDestinationId) {
//                    inclusive = true
//                }
//                launchSingleTop = true
//            }
//            authViewModel.clearNavigationCommand()
//        }
//    }
//
//    NavHost(navController = navController, startDestination = AppScreen.AuthGraph.route) {
//        authNavGraph(navController, authViewModel)
//        homeNavGraph(navController, authViewModel)
//    }
//}
//
//fun NavGraphBuilder.authNavGraph(navController: NavController, authViewModel: UserLogin) {
//    navigation(startDestination = AppScreen.Login.route, route = AppScreen.AuthGraph.route) {
//        composable(AppScreen.Login.route) {
//            Log.d("Navigation", "Navigating to LoginPage")
//            LoginPage(
//                showToggleToTablet = false,
//                navController = navController,
//                viewModel = authViewModel
//            )
//        }
//        // Other routes like Register, OTP...
//    }
//}
//
//fun NavGraphBuilder.homeNavGraph(navController: NavController, authViewModel: UserLogin) {
//    navigation(startDestination = AppScreen.Home.route, route = AppScreen.HomeGraph.route) {
//        composable(AppScreen.Home.route) {
//            Log.d("Navigation", "Navigating to HomeGraph")
//            HomeScreen(
//                onLogout = {
//                    Log.d("Navigation", "Logging out, navigating to LoginPage")
//                    authViewModel.signOut()
//                }
//            )
//        }
//    }
//}