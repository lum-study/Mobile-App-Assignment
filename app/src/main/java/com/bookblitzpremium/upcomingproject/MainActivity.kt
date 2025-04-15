package com.bookblitzpremium.upcomingproject

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.bookblitzpremium.upcomingproject.MyApplication.Companion.CHANNEL_ID
import com.bookblitzpremium.upcomingproject.ViewModel.RemoteDatabase
import com.bookblitzpremium.upcomingproject.ViewModel.User2
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotels
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Response
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.HotelViewModel
import com.bookblitzpremium.upcomingproject.di.HotelModule
import com.bookblitzpremium.upcomingproject.ui.navigation.AppNavigation
import com.bookblitzpremium.upcomingproject.ui.screen.HomeScreen
import com.bookblitzpremium.upcomingproject.ui.screen.InvoiceScreen
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
fun UserListScreen() {
    val users = remember { mutableStateListOf<User2>() }

    val callViewModel = viewModel<RemoteDatabase>()

    LaunchedEffect(true) {
        callViewModel.readUsersFromDatabase {
            users.clear()
            users.addAll(it)
        }
    }

    LazyColumn {
        items(users) { user ->
            Text(text = "${user.name} - ${user.course} - Age: ${user.age}")
        }
    }
}