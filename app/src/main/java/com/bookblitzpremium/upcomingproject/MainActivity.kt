package com.bookblitzpremium.upcomingproject

import android.content.pm.ActivityInfo
import android.content.res.Resources
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.Booking.HotelBookingHorizontalScreen
import com.bookblitzpremium.upcomingproject.Booking.HotelBookingVerticalScreen
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BottomNavigation
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.navigation.AppNavigation
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ReviewFinalPackageSelected
import com.bookblitzpremium.upcomingproject.ui.screen.home.DrawerLabel
import com.bookblitzpremium.upcomingproject.ui.screen.home.GreetingProfile
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import java.net.URLDecoder

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val configuration = Resources.getSystem().configuration
        requestedOrientation =
            if (configuration.screenWidthDp >= 600 && configuration.screenHeightDp >= 900) {
                ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
            } else {
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            }



        FirebaseApp.initializeApp(this)
        setContent {
            AppTheme {
//                InitializeDatabase()
//                App()
                TestHotelBooking()
            }
        }
    }
}

@Composable
fun TestHotelBooking() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "${AppScreen.Hotel.route}/0HCQgp6kuauoYiRH094D/{tripPackageID}"
    ) {
        composable(
            "${AppScreen.Hotel.route}/0HCQgp6kuauoYiRH094D/{tripPackageID}"
        ) { backStackEntry ->
            val hotelID = backStackEntry.arguments?.getString("hotelID") ?: "0HCQgp6kuauoYiRH094D"
            val tripPackageID = backStackEntry.arguments?.getString("tripPackageID") ?: ""

//            HotelBookingScreenLayout(
//                navController = navController,
//                hotelID = hotelID,
//                tripPackageID = tripPackageID,
//            )

//            HotelBookingHorizontalScreen(
//                hotelID = "0HCQgp6kuauoYiRH094D",
//                navController = navController,
//            )

            HotelBookingHorizontalScreen(
                hotelID = hotelID,
                navController = navController,
                modifier = Modifier
            )

//            HotelBookingVerticalScreen(
//                showNUmber = 2,
//                defaultSize = 500.dp,
//                maxSize =  800.dp,
//                hotelID = hotelID,
//                navController = navController,
//            )
        }

        composable(
            route = "BookingReview/{hotelID}/{startDate}/{endDate}/{totalPerson}/{roomBooked}/{totalPrice}/{paymentMethod}/{cardNumber}/{paymentID}/{tabletPortrait}"
        ) { backStackEntry ->
            val hotelID = URLDecoder.decode(backStackEntry.arguments?.getString("hotelID") ?: "", "UTF-8")
            val startDate = URLDecoder.decode(backStackEntry.arguments?.getString("startDate") ?: "", "UTF-8")
            val endDate = URLDecoder.decode(backStackEntry.arguments?.getString("endDate") ?: "", "UTF-8")
            val totalPerson = backStackEntry.arguments?.getString("totalPerson")?.toIntOrNull() ?: 0
            val roomBooked = backStackEntry.arguments?.getString("roomBooked")?.toIntOrNull() ?: 0
            val totalPrice = backStackEntry.arguments?.getString("totalPrice")?.toDoubleOrNull() ?: 0.0
            val paymentMethod = URLDecoder.decode(backStackEntry.arguments?.getString("paymentMethod") ?: "", "UTF-8")
            val cardNumber = URLDecoder.decode(backStackEntry.arguments?.getString("cardNumber") ?: "", "UTF-8")
            val paymentId = URLDecoder.decode(backStackEntry.arguments?.getString("paymentID") ?: "", "UTF-8")
            val tabletPortrait = backStackEntry.arguments?.getString("tabletPortrait") == "true"

            ReviewFinalPackageSelected(
                modifier = Modifier,
                navController = navController,
                hotelID = hotelID,
                totalPrice = totalPrice.toString(),
                startDate = startDate,
                endDate = endDate,
                totalPerson = totalPerson.toString(),
                roomBooked = roomBooked.toString(),
                paymentID = paymentId,
                paymentMethod = paymentMethod,
                cardNumber = cardNumber,
                tabletPortrait = tabletPortrait.toString()
            )
        }
    }
}

@Composable
fun EmailTextField() {
    var email by remember { mutableStateOf(TextFieldValue("")) }
    var isError by remember { mutableStateOf(false) }

    // Function to validate email
    fun validateEmail(email: String): Boolean {
        return email.contains("@") && email.isNotBlank()
    }

    OutlinedTextField(
        value = email,
        onValueChange = { newValue ->
            email = newValue
            isError = !validateEmail(newValue.text) // Set error if validation fails
        },
        label = { Text("Email") },
        isError = isError,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .border(
                width = if (isError) 2.dp else 1.dp,
                color = if (isError) Color.Red else Color.Gray,
                shape = MaterialTheme.shapes.small
            ),
        supportingText = {
            if (isError) {
                Text(
                    text = "Invalid email: Must contain '@'",
                    color = Color.Red
                )
            }
        }
    )
}

@Preview(showBackground = true, widthDp = 360, heightDp = 806)
@Composable
fun App(
    navController: NavHostController = rememberNavController(),
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    val userViewModel: AuthViewModel = hiltViewModel()
    val navigationRoute by userViewModel.newNavigationCommand.collectAsState()

    val startDestination =
        if (navigationRoute) AppScreen.HomeGraph.route else AppScreen.AuthGraph.route
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
            if (currentScreen.hasBottomBar && (deviceType == DeviceType.MobilePortrait || deviceType == DeviceType.TabletPortrait)) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        Row {
            if (currentScreen.hasBottomBar && (deviceType == DeviceType.TabletLandscape)) {
                SideBar(navController = navController, username = "", deviceType = deviceType)
            }
            AppNavigation(
                navController,
                startDestination,
                Modifier.padding(innerPadding),
                userViewModel
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBar(
    currentScreen: AppScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = currentScreen.route.replace(Regex("([a-z])([A-Z])"), "$1 $2"),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                modifier = Modifier
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Color.Transparent
        ),
        modifier = modifier.height(72.dp),
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(
                    onClick = navigateUp,
                    modifier = Modifier.size(30.dp)
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
                                        inclusive =
                                            item.navigation.route == AppScreen.HomeGraph.route
                                    }
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
fun SideBar(
    navController: NavHostController,
    username: String,
    deviceType: DeviceType,
) {
    val navItems = BottomNavigation.entries.toTypedArray()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.parent?.route
    var selectedIndex by remember { mutableIntStateOf(0) }

    LaunchedEffect(currentRoute) {
        selectedIndex = navItems.indexOfFirst { it.navigation.route == currentRoute }
    }

    PermanentDrawerSheet(
        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
        drawerContainerColor = Color(0xFFE0E0E0),
        modifier = Modifier.fillMaxWidth(if (deviceType == DeviceType.TabletPortrait) .35f else .25f)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.padding(16.dp)
        ) {
            GreetingProfile()

            HorizontalDivider()

            navItems.forEachIndexed { index, item ->
                NavigationDrawerItem(
                    label = { DrawerLabel(item.icon, item.title) },
                    selected = index == selectedIndex,
                    onClick = {
                        selectedIndex = index
                        navController.navigate(item.navigation.route) {
                            popUpTo(AppScreen.Home.route) {
                                inclusive =
                                    item.navigation.route == AppScreen.HomeGraph.route
                            }
                        }
                    },
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = Color.LightGray,
                        unselectedContainerColor = AppTheme.colorScheme.onPrimary,
                        selectedTextColor = AppTheme.colorScheme.primary,
                        unselectedTextColor = AppTheme.colorScheme.primary
                    )
                )
            }
        }
    }
}