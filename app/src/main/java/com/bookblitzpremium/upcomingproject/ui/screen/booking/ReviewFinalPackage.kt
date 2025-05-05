package com.bookblitzpremium.upcomingproject.ui.screen.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.components.NotificationService
import com.bookblitzpremium.upcomingproject.ui.components.TripPackageBookingDialog
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentOptionScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
fun PreviewFinalPackage() {
    val navController = rememberNavController()
    ReviewFinalPackageSelected(
        navController = navController,
        modifier = Modifier,
        hotelID = "fdgdfgf",
        totalPrice = "1000.00",
        startDate = "2025-05-23",
        endDate = "2025-05-25",
        totalPerson = "4",
        roomBooked = "1",
        paymentID = "vgdfgfhfh",
        paymentMethod = "DebitCard",
        cardNumber = "1234567890123456",
        tabletPortrait = "false"
    )
}


@Composable
fun DialogPaymentMethod(
    onDismissRequest: () -> Unit,
    onDateSelected: (String?, String?) -> Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .height(400.dp)
                .width(500.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            var paymentMethod by remember { mutableStateOf(PaymentMethod.DebitCard) }
            var cardNumber by remember { mutableStateOf("") }

            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
                , modifier = Modifier
                    .padding(30.dp)
            ) {
                Text(
                    text = "Payment method",
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface // Text on surface
                )
                PaymentOptionScreen(
                    selectedPaymentMethod = paymentMethod,
                    onPaymentMethodChange = {
                        paymentMethod = it
                    },
                    cardNumber = cardNumber,
                    onCardNumberChange = {
                        cardNumber = it.filter { it.isDigit() }
                    },
                )
                onDateSelected(paymentMethod.toString(), cardNumber)
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


@Composable
fun ReviewFinalPackageSelected(
    navController: NavController,
    modifier: Modifier,
    hotelID: String,
    totalPrice: String,
    startDate: String,
    endDate: String,
    totalPerson: String,
    roomBooked: String,
    paymentID: String,
    paymentMethod: String,
    cardNumber: String,
    tabletPortrait: String = "false"
) {

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(AppTheme.colorScheme.background), // Use background for column
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        val viewModel: LocalHotelViewModel = hiltViewModel()
        val remoteBookingViewModel: RemoteHotelBookingViewModel = hiltViewModel()
        val loading = viewModel.loading.collectAsState()
        val hotel by viewModel.selectedHotel.collectAsState()
        var paymentMethods by remember { mutableStateOf<String?>(null) }
        var cardNumbers by remember { mutableStateOf<String?>(null) }

        //Loading
        val isLoading by remoteBookingViewModel.loading.collectAsState()

        //Handle Alert Dialog
        var showDialog by remember { mutableStateOf(false) }

        val success by remoteBookingViewModel.success.collectAsState()

        val context = LocalContext.current
        LaunchedEffect(success) {
            if (success) {
                val notificationService = NotificationService(context)
                notificationService.showNotification("Booking Successfully", "Thank you for supporting us")
            }
        }

        if (showDialog) {
            TripPackageBookingDialog(
                isLoading = isLoading,
                onHomeButtonClick = {
                    showDialog = it
                    navController.navigate(AppScreen.Home.route) {
                        popUpTo(AppScreen.Home.route) {
                            inclusive = true
                        }
                    }
                },
                onViewOrderButtonClick = {
                    showDialog = it
                    navController.navigate(AppScreen.OrderGraph.route) {
                        popUpTo(AppScreen.Home.route)
                    }
                },
                onDismissButtonClick = {
                    showDialog = false
                }
            )
        }

        LaunchedEffect(Unit) {
            viewModel.getHotelByID(hotelID)
        }

        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = AppTheme.colorScheme.primary // Use primary for loader
                )
            }
        } else if (hotel != null) {
            val hotelData = hotel!!
            val isTablet = tabletPortrait == "true"

            StyledImage(hotelData.imageUrl.toString(), isTablet.toString())

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
            ) {
                if ((isTablet.toString() == "true")) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(if(isTablet) 16.dp else 0.dp)
                        ){
                            HotelInfoContent(hotelData, AppTheme.typography.largeBold)
                        }

                        var dialogTrue by remember { mutableStateOf(false) }

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colorScheme.primary,
                                contentColor = AppTheme.colorScheme.onSecondary
                            ),
                            onClick ={
                                dialogTrue = true
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .padding(16.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = paymentMethods ?: "Payment Method",
                                style = AppTheme.typography.mediumBold,
                                color = AppTheme.colorScheme.onSecondary,
                            )
                        }

                        if(dialogTrue){
                            DialogPaymentMethod(
                                onDismissRequest = {dialogTrue = false} ,
                                onDateSelected = { paymentMethod, cardNumber ->
                                    paymentMethods = (paymentMethod ?: "").toString()
                                    cardNumbers = (cardNumber ?: "").toString()
                                }
                            )
                        }

                    }
                } else {
                    HotelInfoContent(hotelData, AppTheme.typography.largeBold)
                }
            }

            val heightSize = if((isTablet.toString() == "true")) 100.dp else 80.dp

//            MapsComponent(hotelData.name, onClick = { navController.navigate("${AppScreen.Maps.route}/${hotelData.name}")}, modifier = Modifier.fillMaxWidth().height(if(isTablet) 300.dp else 200.dp).background(color = Color.Black).padding(if(isTablet)16.dp else 0.dp).clip(shape = RoundedCornerShape(32.dp)),)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightSize)
                    .background(AppTheme.colorScheme.surface) // Use surface for background
                    .border(1.dp, AppTheme.colorScheme.primary)
                    .clip(RoundedCornerShape(32.dp))
                    .padding(if(isTablet) 16.dp else 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Check-In half
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LegendItem1(
                        icon = Icons.Filled.CalendarToday,
                        iconDescription = "Check-In Icon",
                        label = "Check-In",
                        date = startDate,
                        modifier = Modifier
                    )
                }

                // Vertical divider
                Divider(
                    color = AppTheme.colorScheme.primary, // Use primary for divider
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )

                // Check-Out half
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LegendItem1(
                        icon = Icons.Filled.CalendarToday,
                        iconDescription = "Check-Out Icon",
                        label = "Check-Out",
                        date = endDate,
                        modifier = Modifier
                    )
                }
            }

            if((isTablet.toString() == "false")) Spacer(modifier = Modifier.weight(1f))

            DetailsSection(
                totalPrice = totalPrice,
                totalPerson = totalPerson,
                roomBooked = roomBooked,
                tabletPortrait = tabletPortrait,
                modifier = Modifier
            )

            val currentUser = FirebaseAuth.getInstance().currentUser
            var userID = currentUser?.uid.toString()

            Button(
                onClick = {
                    val booking = HotelBooking(
                        startDate = startDate,
                        endDate = endDate,
                        numberOFClient = totalPerson.toIntOrNull() ?: 1,
                        numberOfRoom = roomBooked.toIntOrNull() ?: 1,
                        hotelID = hotelData.id,
                        userid = userID.toString(), // Add real user ID if available
                        paymentID = paymentID
                    )

                    val localPayment = Payment(
                        id = paymentID,
                        createDate = LocalDate.now().toString(),
                        totalAmount = totalPrice.toDoubleOrNull() ?: 0.0,
                        paymentMethod = (if(isTablet) paymentMethods else paymentMethod).toString(),
                        cardNumber = (if(isTablet) cardNumbers else cardNumber).toString(),
                        currency = "Ringgit Malaysia",
                        userID = userID.toString()
                    )

                    remoteBookingViewModel.addNewIntegratedRecord(booking,localPayment)
                    showDialog = true

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colorScheme.primary, // Use primary for button
                    contentColor = AppTheme.colorScheme.onPrimary // Text/icon on primary
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(if(isTablet) 16.dp else 0.dp)

            ) {
                Text(
                    text = "Next",
                    color = AppTheme.colorScheme.onPrimary // Text on primary
                )
            }
        }
    }
}


@Composable
fun HotelInfoContent(
    hotelData: Hotel,
    textSize: TextStyle
){
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ){
        Text(
            text = hotelData.name,
            style = textSize,
            color = AppTheme.colorScheme.onSurface // Text on surface
        )
        Row(
            modifier = Modifier,
        ) {
            repeat(3) {
                Text(
                    text = "⭐",
                    color = AppTheme.colorScheme.secondary, // Use secondary for stars
                    modifier = Modifier
                )
            }

            Row(
                modifier = Modifier
                    .padding(start = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(50.dp)
            ) {
                Text(
                    text = "Malaysia Johor",
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface, // Text on surface
                    modifier = Modifier
                        .padding(start = 12.dp)
                )
            }
        }
    }
}



@Composable
fun LegendItem1(
    icon: ImageVector,
    iconDescription: String,
    label: String,
    date: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
                tint = AppTheme.colorScheme.primary, // Use primary for icon
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = AppTheme.typography.smallBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }

        Column {
            Text(
                text = date,
                style = AppTheme.typography.smallBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val startDate = "2023-05-23"
    val endDate = "2023-05-25"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = AppTheme.colorScheme.primary)
            .height(50.dp)
            .background(AppTheme.colorScheme.surface), // Use surface for background
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Check-In section
        LegendItem1(
            icon = Icons.Filled.CalendarToday,
            iconDescription = "Check-In Icon",
            label = "Check-In",
            date = startDate,
            modifier = Modifier.weight(1f)
        )

        // Spacer for consistent spacing between sections
        Spacer(modifier = Modifier.width(16.dp))

        // Check-Out section
        LegendItem1(
            icon = Icons.Filled.CalendarToday,
            iconDescription = "Check-Out Icon",
            label = "Check-Out",
            date = endDate,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
fun DetailsSection(
    totalPrice: String,
    totalPerson: String,
    roomBooked: String,
    startDate:String = "",
    endDate:String = "",
    tabletPortrait: String = "false",
    modifier: Modifier
) {
    val isTablet = tabletPortrait == "true"

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(if(isTablet) 16.dp else 0.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Adult",
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
            Text(
                text = totalPerson,
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Room",
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
            Text(
                text = roomBooked,
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }

        if (startDate.isNotEmpty() && endDate.isNotEmpty()) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "startDate",
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface // Text on surface
                )
                Text(
                    text = startDate.toString(),
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface // Text on surface
                )
            }


            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "endDate",
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface // Text on surface
                )
                Text(
                    text = endDate.toString(),
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface // Text on surface
                )
            }

        }

        Divider(
            color = AppTheme.colorScheme.primary, // Use primary for divider
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Price",
                style = AppTheme.typography.largeBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
            Text(
                text = "RM $totalPrice",
                style = AppTheme.typography.largeBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }
    }
}

@Composable
fun StyledImage(
    hotelImages: String,
    tabletPortrait: String
) {
    val isTablet = tabletPortrait == "true"
    val imageHeight = if (isTablet) 400.dp else 200.dp

    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surface // Use surface for card
        )
    ) {
        UrlImage(
            imageUrl = hotelImages,
            modifier = Modifier
                .fillMaxWidth()
                .padding(if(isTablet) 16.dp else 0.dp)
                .height(imageHeight),
            contentScale = ContentScale.Crop,
        )
    }
}