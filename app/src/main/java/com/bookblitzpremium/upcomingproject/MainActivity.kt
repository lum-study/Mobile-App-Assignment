package com.bookblitzpremium.upcomingproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BottomNavigation
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.tablet.MyScreen
import com.bookblitzpremium.upcomingproject.tablet.TravelOffersScreen
import com.bookblitzpremium.upcomingproject.ui.navigation.AppNavigation
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        FirebaseApp.initializeApp(this)
        setContent {
            AppTheme {
//                InitializeDatabase()
                App()
//                MyScreen()
//                ReadLocalDatabase()
//                TravelOffersScreen()
//                AddHotelBookingScreen()
                val navController = rememberNavController()

//                    .setCustomContentView(notificationLayout)
//                    .setCustomBigContentView(notificationLayoutExpanded)
            }
        }
    }
}

@Composable
fun AddHotelBookingScreen(viewModel: RemoteHotelBookingViewModel = hiltViewModel()) {
    val coroutineScope = rememberCoroutineScope()

//    Button(onClick = {
//        val hotelBooking = HotelBooking(
//            startDate = "2025-04-23",
//            endDate = "2025-04-30",
//            numberOFClient = 2,
//            numberOfRoom = 1,
//            hotelID = "hotel123",
//            userid = "123",
//            paymentID = ""
//        )
//        coroutineScope.launch {
//            val id = viewModel.addHotelBooking(hotelBooking)
//            Log.d("HotelBooking", "Added booking with ID: $id")
//        }
//    }) {
//        Text("Add Hotel Booking")
//    }
}


@Composable
fun performUpdate() {
    val viewModel: LocalHotelBookingViewModel = hiltViewModel()
    val booking = HotelBooking(
        id = "1",
        startDate = "2025-04-23",
        endDate = "2025-04-30",
        numberOFClient = 20,
        numberOfRoom = 5,
        hotelID = "0HCQgp6kuauoYiRH094D",
        userid = "",
        paymentID = ""
    )
    val updatedBooking = booking.copy(userid = "123")
    viewModel.updateHotelBooking(updatedBooking)
}


@Preview(showBackground = true, widthDp = 360, heightDp = 806)
@Composable
fun App(
    navController: NavHostController = rememberNavController()
) {
    val startDestination = AppScreen.AuthGraph.route
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = AppScreen.fromRoute(
        backStackEntry?.destination?.route
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
            if (currentScreen.hasBottomBar) {
                BottomNavigationBar(navController)
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
                text = currentScreen.route.replace(Regex("([a-z])([A-Z])"), "$1 $2"),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .offset(x = -20.dp)
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.height(74.dp),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button),
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomAppBar(
        modifier = Modifier.height(90.dp),
        containerColor = Color.Transparent
    ) {
        val navItems = BottomNavigation.entries.toTypedArray()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.parent?.route
        var selectedIndex by remember { mutableIntStateOf(0) }

        LaunchedEffect(currentRoute) {
            selectedIndex = navItems.indexOfFirst { it.navigation.route == currentRoute }
        }
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .height(64.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                navItems.forEachIndexed { index, item ->
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .clickable(
                                enabled = index != selectedIndex,
                            ) {
                                selectedIndex = index
                                navController.navigate(item.navigation.route) {
                                    popUpTo(AppScreen.Home.route) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                            .size(64.dp)
                    ) {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = null,
                            tint = if (index == selectedIndex) AppTheme.colorScheme.primary else Color.Gray,
                            modifier = Modifier
                                .size(32.dp)
                        )
                        Text(
                            text = item.title,
                            style = AppTheme.typography.smallRegular,
                            color = if (index == selectedIndex) AppTheme.colorScheme.primary else Color.Gray,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ReadLocalDatabase(
    viewModel: LocalHotelBookingViewModel = hiltViewModel()
) {

//    val bookings by viewModel.hotelBookings.collectAsState()
    viewModel.fetchHotelBookingsByUserId("123")
    val bookingData by viewModel.hotelBookings.collectAsState()


    val booking = HotelBooking(
        id = "WAQWm6gJqqBRfdIhsZwS",
        startDate = "25-02-2025",
        endDate = "25-02-2025",
        numberOFClient =  1,
        numberOfRoom =  1,
        hotelID = "0HCQgp6kuauoYiRH094D",
        userid = "123", // Add real user ID if available
        paymentID = "" // Add real payment ID if available
    )

    Button(
        onClick = {
            viewModel.insertHotelBooking(booking)
        }
    ) {
        Text(text = "Store")
    }

//    LazyColumn(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(Color.White)
//            .padding(top = 16.dp),
//        verticalArrangement = Arrangement.spacedBy(8.dp)
//    ) {
//        items(bookingData) { booking ->
//            Text("Booking ID: ${booking.id}", fontWeight = FontWeight.Bold)
//            Text("Start Date: ${booking.startDate}")
//            Text("End Date: ${booking.endDate}")
//            Text("Clients: ${booking.numberOFClient}")
//            Text("Rooms: ${booking.numberOfRoom}")
//            Text("Hotel ID: ${booking.hotelID}")
//            Text("User ID: ${booking.userid}")
//            Text("Payment ID: ${booking.paymentID}")
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//    }
}


//    Column(modifier = Modifier.padding(16.dp)) {
//        bookings.forEach { booking ->
//            Text("Booking ID: ${booking.id}", fontWeight = FontWeight.Bold)
//            Text("Start Date: ${booking.startDate}")
//            Text("End Date: ${booking.endDate}")
//            Text("Clients: ${booking.numberOFClient}")
//            Text("Rooms: ${booking.numberOfRoom}")
//            Text("Hotel ID: ${booking.hotelID}")
//            Text("User ID: ${booking.userid}")
//            Text("Payment ID: ${booking.paymentID}")
//            Spacer(modifier = Modifier.height(16.dp))
//        }
//
//        if (bookings.isEmpty()) {
//            Text("No bookings found.")
//        }
//    }






