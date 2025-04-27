package com.bookblitzpremium.upcomingproject

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.slideInHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
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
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalPaymentViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.navigation.AppNavigation
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.FirebaseApp
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate

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
//              InitializeDatabase()
                App()
//                ReadBooking()
            }
        }
    }
}

@Composable
fun ReadBooking(
    hotelViewModel: LocalHotelBookingViewModel = hiltViewModel()
) {
    val hotelBookings by hotelViewModel.hotelBookings.collectAsState()
//
//    LaunchedEffect(Unit) {
//        hotelViewModel.ge() // Call it once when the screen opens
//    }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
    ){
        Text(
            text = "HotelBooking"
        )
        hotelBookings.forEach { booking ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = "Hotel ID: ${booking.hotelID}", fontSize = 18.sp)
                        Text(text = "numberOFClient: ${booking.numberOFClient}", fontSize = 14.sp)
                        Text(text = "Start Date: ${booking.startDate}", fontSize = 14.sp)
                        Text(text = "End Date: ${booking.endDate}", fontSize = 14.sp)
                        Text(text = "paymentID:${booking.paymentID}", fontSize = 14.sp)
                        Text(text = "userid: ${booking.userid}", fontSize = 14.sp)
                        Text(text = "numberOfRoom: ${booking.numberOfRoom}", fontSize = 14.sp)
                        Text(text = "Total Price: RM ${booking.paymentID}", fontSize = 14.sp)
                    }

                }
        }
    }
}

//@Composable
//fun ReadPayment(
//    paymentRemoteViewModel: RemotePaymentViewModel = hiltViewModel(),
//    RemotehotelViewModel: RemoteHotelBookingViewModel = hiltViewModel(),
//    localhotelViewModel: LocalHotelBookingViewModel = hiltViewModel(),
////    localPaymentViewModels: LocalPaymentViewModel = hiltViewModel()
//) {
//    val paymentRemote by paymentRemoteViewModel.payment.collectAsState()
//    val hotelBookings by RemotehotelViewModel.hotelBookings.collectAsState()
//    val localHotelBooking by localhotelViewModel.hotelBookings.collectAsState()
////    val localPayment by localPaymentViewModels.paymentBooking.collectAsState()
//
//    LaunchedEffect(Unit) {
//        paymentRemoteViewModel.getAllPayment()
//        RemotehotelViewModel.getAllHotelBooking()
////        localPaymentViewModels.getAllPayment()
//    }
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        Column(
//            modifier = Modifier.verticalScroll(rememberScrollState())
//        ) {
//            Text(text = "RemotePaymentViewModel")
//            paymentRemote.lastOrNull()?.let { booking ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(text = "totalAmount: ${booking.totalAmount}", fontSize = 14.sp)
//                        Text(text = "createDate: ${booking.createDate}", fontSize = 14.sp)
//                        Text(text = "paymentMethod: ${booking.paymentMethod}", fontSize = 14.sp)
//                        Text(text = "paymentID: ${booking.id}", fontSize = 14.sp)
//                        Text(text = "userID: ${booking.userID}", fontSize = 14.sp)
//                        Text(text = "cardNumber: ${booking.cardNumber}", fontSize = 14.sp)
//                        Text(text = "currency: ${booking.currency}", fontSize = 14.sp)
//                    }
//                }
//            }
//        }
//
//        Column(
//            modifier = Modifier.verticalScroll(rememberScrollState())
//        ) {
//            Text(text = "RemoteHotelBooking")
//            hotelBookings.lastOrNull()?.let { booking ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(text = "Hotel ID: ${booking.hotelID}", fontSize = 18.sp)
//                        Text(text = "numberOFClient: ${booking.numberOFClient}", fontSize = 14.sp)
//                        Text(text = "Start Date: ${booking.startDate}", fontSize = 14.sp)
//                        Text(text = "End Date: ${booking.endDate}", fontSize = 14.sp)
//                        Text(text = "paymentID: ${booking.paymentID}", fontSize = 14.sp)
//                        Text(text = "userID: ${booking.userid}", fontSize = 14.sp)
//                        Text(text = "numberOfRoom: ${booking.numberOfRoom}", fontSize = 14.sp)
//                        Text(text = "Total Price: RM ${booking.paymentID}", fontSize = 14.sp)
//                    }
//                }
//            }
//        }
//
//        Column(
//            modifier = Modifier.verticalScroll(rememberScrollState())
//        ) {
//            Text(text = "LocalHotelBooking")
//            localHotelBooking.lastOrNull()?.let { booking ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(text = "Hotel ID: ${booking.hotelID}", fontSize = 18.sp)
//                        Text(text = "numberOFClient: ${booking.numberOFClient}", fontSize = 14.sp)
//                        Text(text = "Start Date: ${booking.startDate}", fontSize = 14.sp)
//                        Text(text = "End Date: ${booking.endDate}", fontSize = 14.sp)
//                        Text(text = "paymentID: ${booking.paymentID}", fontSize = 14.sp)
//                        Text(text = "userID: ${booking.userid}", fontSize = 14.sp)
//                        Text(text = "numberOfRoom: ${booking.numberOfRoom}", fontSize = 14.sp)
//                        Text(text = "Total Price: RM ${booking.paymentID}", fontSize = 14.sp)
//                    }
//                }
//            }
//        }

////
//        Column(
//            modifier = Modifier.verticalScroll(rememberScrollState())
//        ) {
//            Text(text = "Payment")
//            localPayment.lastOrNull()?.let { booking ->
//                Card(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(8.dp),
//                    shape = RoundedCornerShape(12.dp),
//                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//                ) {
//                    Column(modifier = Modifier.padding(16.dp)) {
//                        Text(text = "totalAmount: ${booking.totalAmount}", fontSize = 14.sp)
//                        Text(text = "createDate: ${booking.createDate}", fontSize = 14.sp)
//                        Text(text = "paymentMethod: ${booking.paymentMethod}", fontSize = 14.sp)
//                        Text(text = "paymentID: ${booking.id}", fontSize = 14.sp)
//                        Text(text = "userID: ${booking.userID}", fontSize = 14.sp)
//                        Text(text = "cardNumber: ${booking.cardNumber}", fontSize = 14.sp)
//                        Text(text = "currency: ${booking.currency}", fontSize = 14.sp)
//                    }
//                }
//            }
//        }
//    }
//}

@Composable
fun Booking() {

    val paymentView: LocalPaymentViewModel = hiltViewModel()
    val paymentdata by paymentView.paymentBooking.collectAsState()

    paymentView.getAllPayment()

    val payment = Payment(
        createDate = LocalDate.now().toString(),
        totalAmount = 0.0,
        paymentMethod = PaymentMethod.CreditCard.toString(),
        cardNumber = "dsggfdgfgfhgfh",
        currency = "Ringgit Malaysia",
        userID = "userID"
    )

    val coroutineScope = rememberCoroutineScope()

//    Button(
//        onClick = {
//            paymentView.addRoom(payment)
//        }
//    ) {
//        Text(text = "Test")
//    }


    // Fetch rooms when the screen is first composed

    Column(
        modifier = Modifier.verticalScroll(rememberScrollState())
    ) {
        Text(text = "Payment")
        paymentdata.lastOrNull()?.let { booking ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "totalAmount: ${booking.totalAmount}", fontSize = 14.sp)
                    Text(text = "createDate: ${booking.createDate}", fontSize = 14.sp)
                    Text(text = "paymentMethod: ${booking.paymentMethod}", fontSize = 14.sp)
                    Text(text = "paymentID: ${booking.id}", fontSize = 14.sp)
                    Text(text = "userID: ${booking.userID}", fontSize = 14.sp)
                    Text(text = "cardNumber: ${booking.cardNumber}", fontSize = 14.sp)
                    Text(text = "currency: ${booking.currency}", fontSize = 14.sp)
                }
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
fun SingleDynamicColumn() {
    var showColumn by remember { mutableStateOf(false) } // State to track column visibility

    Row(modifier = Modifier.padding(16.dp)) {
        // Button to toggle the column
        Button(onClick = { showColumn = !showColumn }) {
            Text(if (showColumn) "Remove Column" else "Add Column")
        }

        // Animate the single column's appearance and disappearance
        AnimatedVisibility(
            visible = showColumn,
            enter = fadeIn(animationSpec = tween(durationMillis = 300, easing = LinearOutSlowInEasing)) + slideInHorizontally(initialOffsetX = { it }), // Fade in and slide from right
            exit = shrinkHorizontally() + fadeOut()
        ) {
           //add the screen
            ScreenLayout()
        }
    }
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
                                    popUpTo(AppScreen.Home.route){
                                        inclusive = item.navigation.route == AppScreen.HomeGraph.route
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
fun ScreenLayout() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        var clicked by remember { mutableStateOf(false) }
        // Top Section
        repeat(4){
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)) // Outer rounded corner
            ) {
                Image(
                    painter = painterResource(id = R.drawable.beach),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)) // Only top rounded
                        .align(Alignment.TopCenter)
                        .clickable(
                            onClick = {
                                clicked = true
                            }
                        )
                )

                if(clicked){

                }

                Text(
                    text = "Hello",
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                )

                Button(
                    onClick = { /* To be implemented */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .align(Alignment.BottomEnd),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF666666)),
                    shape = RoundedCornerShape(bottomStart = 16.dp, bottomEnd = 16.dp)
                ) {
                    Text("ddsdgfgfg")
                }
            }
        }
    }
}