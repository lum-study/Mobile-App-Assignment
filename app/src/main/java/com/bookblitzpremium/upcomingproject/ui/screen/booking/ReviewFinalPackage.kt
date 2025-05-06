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
import androidx.compose.runtime.saveable.rememberSaveable
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
import com.bookblitzpremium.upcomingproject.HandleRotateState
import com.bookblitzpremium.upcomingproject.HotelDetails
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingStatus
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
//    val navController = rememberNavController()
//    ReviewFinalPackageSelected(
//        navController = navController,
//        modifier = Modifier,
//        hotelID = "fdgdfgf",
//        totalPrice = "1000.00",
//        startDate = "2025-05-23",
//        endDate = "2025-05-25",
//        totalPerson = "4",
//        roomBooked = "1",
//        paymentID = "vgdfgfhfh",
//        paymentMethod = "DebitCard",
//        cardNumber = "1234567890123456",
//        tabletPortrait = "false"
//    )
}

@Composable
fun DialogPaymentMethod(
    onDismissRequest: () -> Unit,
    saveData: HandleRotateState, // Add saveData parameter
    onDateSelected: (PaymentMethod, String) -> Unit
) {
    val hotelDetails by saveData.hotelDetails.collectAsState() // Collect state from saveData
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }
    ) {
        Box(
            modifier = Modifier
                .height(400.dp)
                .width(500.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            // Initialize from saveData
            var paymentMethod by remember {
                mutableStateOf(
                    stringToPaymentMethod(hotelDetails.paymentMethodString) ?: PaymentMethod.NotSelected
                )
            }
            var cardNumber by rememberSaveable { // Use rememberSaveable for rotation persistence
                mutableStateOf(hotelDetails.cardNumber.ifEmpty { "" })
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp), // Increased spacing for better layout
                modifier = Modifier.padding(30.dp)
            ) {
                Text(
                    text = "Payment method",
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface
                )
                PaymentOptionScreen(
                    selectedPaymentMethod = paymentMethod,
                    onPaymentMethodChange = { paymentMethod = it },
                    cardNumber = cardNumber,
                    onCardNumberChange = { cardNumber = it.filter { it.isDigit() } }
                )
            }

            IconButton(
                onClick = {
                    saveData.updatePaymentMethodEnum(paymentMethod)
                    saveData.updateCardNumber(cardNumber)
                    onDateSelected(paymentMethod, cardNumber)
                    onDismissRequest()
                },
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

fun stringToPaymentMethod(method: String?): PaymentMethod {
    return when (method) {
        PaymentMethod.DebitCard.title -> PaymentMethod.DebitCard
        PaymentMethod.CreditCard.title -> PaymentMethod.CreditCard
        PaymentMethod.EWallet.title -> PaymentMethod.EWallet
        PaymentMethod.NotSelected.title -> PaymentMethod.NotSelected
        else -> PaymentMethod.NotSelected // Default to NotSelected for invalid input
    }
}

@Composable
fun ReviewFinalPackageSelected(
    hotelID: String,
    saveData: HandleRotateState,
    navController: NavController,
    modifier: Modifier,
    tabletPortrait: String = "false"
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(AppTheme.colorScheme.background),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val context = LocalContext.current

        val hotelOnChange by saveData.hotelDetails.collectAsState()

        val localHotelViewModel: LocalHotelViewModel = hiltViewModel()
        val remoteBookingViewModel: RemoteHotelBookingViewModel = hiltViewModel()

        // Hotel
        val loading by localHotelViewModel.loading.collectAsState()
        val hotel by localHotelViewModel.selectedHotel.collectAsState()

        var paymentMethod by remember {
            mutableStateOf<PaymentMethod>(
                if (hotelOnChange.paymentMethod != PaymentMethod.NotSelected) {
                    hotelOnChange.paymentMethod
                } else {
                    PaymentMethod.NotSelected
                }
            )
        }

        var cardNumber by rememberSaveable {
            mutableStateOf(
                if (hotelOnChange.cardNumber.isNotEmpty()) hotelOnChange.cardNumber else ""
            )
        }

        // Loading
        val isLoading by remoteBookingViewModel.loading.collectAsState()

        // Handle Alert Dialog
        var showDialog by remember { mutableStateOf(false) }

        val success by remoteBookingViewModel.success.collectAsState()

        var dialogTrue by remember { mutableStateOf(false) }

        LaunchedEffect(hotelID) { // Key with hotelID to avoid redundant calls
            if (hotelID.isNotEmpty()) {
                localHotelViewModel.getHotelByID(hotelID)
            }
        }

        LaunchedEffect(success) {
            if (success) {
                val notificationService = NotificationService(context)
                notificationService.showNotification("Booking Successfully", "Thank you for supporting us")
                saveData.clearHotelDetails()
            }
        }

        val error by remoteBookingViewModel.error.collectAsState()

        if (showDialog) {
            TripPackageBookingDialog(
                isLoading = isLoading,
                hasError = error ?: "",
                onHomeButtonClick = {
                    showDialog = it
                    saveData.clearHotelDetails()
                    navController.navigate(AppScreen.Home.route) {
                        popUpTo(AppScreen.Home.route) { inclusive = true }
                    }
                },
                onViewOrderButtonClick = {
                    showDialog = it
                    navController.navigate(AppScreen.OrderGraph.route) {
                        popUpTo(AppScreen.Home.route)
                    }
                },
                onDismissButtonClick = { showDialog = false }
            )
        }

        if (loading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator(color = AppTheme.colorScheme.primary)
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
                if (isTablet) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier
                                .weight(1f)
                                .padding(if (isTablet) 16.dp else 0.dp)
                        ) {
                            HotelInfoContent(hotelData, AppTheme.typography.largeBold)
                        }

                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AppTheme.colorScheme.primary,
                                contentColor = AppTheme.colorScheme.onSecondary
                            ),
                            onClick = { dialogTrue = true },
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))
                                .padding(16.dp)
                                .weight(1f)
                        ) {
                            Text(
                                text = paymentMethod.title,
                                style = AppTheme.typography.mediumBold,
                                color = AppTheme.colorScheme.onSecondary,
                            )
                        }

                        if (dialogTrue) {
                            DialogPaymentMethod(
                                onDismissRequest = {
                                    saveData.updateCardNumber(cardNumber)
                                    saveData.updatePaymentMethod(paymentMethod.title) // Use title for String field
                                    saveData.updatePaymentMethodEnum(paymentMethod)
                                    dialogTrue = false
                                },
                                onDateSelected = { paymentMethods, cardNumbers ->
                                    paymentMethod = stringToPaymentMethod(paymentMethods.title)
                                    cardNumber = cardNumbers
                                },
                                saveData = saveData
                            )
                        }
                        println("${hotelOnChange.paymentMethod} + ${hotelOnChange.cardNumber} + ${hotelOnChange.paymentMethodString}")
                    }
                } else {
                    HotelInfoContent(hotelData, AppTheme.typography.largeBold)
                }
            }

            val heightSize = if (isTablet) 100.dp else 80.dp

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(heightSize)
                    .background(AppTheme.colorScheme.surface)
                    .border(1.dp, AppTheme.colorScheme.primary)
                    .clip(RoundedCornerShape(32.dp))
                    .padding(if (isTablet) 16.dp else 0.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier.weight(1f).padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LegendItem1(
                        icon = Icons.Filled.CalendarToday,
                        iconDescription = "Check-In Icon",
                        label = "Check-In",
                        date = hotelOnChange.startDate, // Use hotelOnChange
                        modifier = Modifier
                    )
                }

                Divider(
                    color = AppTheme.colorScheme.primary,
                    modifier = Modifier.width(1.dp).fillMaxHeight()
                )

                Box(
                    modifier = Modifier.weight(1f).padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LegendItem1(
                        icon = Icons.Filled.CalendarToday,
                        iconDescription = "Check-Out Icon",
                        label = "Check-Out",
                        date = hotelOnChange.endDate, // Use hotelOnChange
                        modifier = Modifier
                    )
                }
            }

            if (!isTablet) Spacer(modifier = Modifier.weight(1f))

            DetailsSection(
                totalPrice = hotelOnChange.totalPrice,
                totalPerson = hotelOnChange.numberOFClient,
                roomBooked = hotelOnChange.numberOfRoom,
                tabletPortrait = tabletPortrait,
                modifier = Modifier
            )

            val currentUser = FirebaseAuth.getInstance().currentUser
            val userID = currentUser?.uid.toString()

            Button(
                onClick = {
                    println("Debug - hotelDetail: $hotelOnChange")
                    println("Debug - hotelID: $hotelID")
                    println("Debug - userID: $userID")
                    println("Debug - paymentMethod: $paymentMethod")
                    println("Debug - cardNumber: $cardNumber")

                    val booking = HotelBooking(
                        startDate = hotelOnChange.startDate,
                        endDate = hotelOnChange.endDate,
                        numberOFClient = hotelOnChange.numberOFClient.toIntOrNull() ?: 1,
                        numberOfRoom = hotelOnChange.numberOfRoom.toIntOrNull() ?: 1,
                        hotelID = hotelData.id,
                        userid = userID.toString(), // Add real user ID if available
                        paymentID = hotelOnChange.paymentID,
                        status = BookingStatus.Confirmed.title
                    )

                    val localPayment = Payment(
                        id = booking.paymentID,
                        createDate = LocalDate.now().toString(),
                        totalAmount = hotelOnChange.totalPrice.toDoubleOrNull() ?: 0.0,
                        paymentMethod = if(isTablet) paymentMethod.title else hotelOnChange.paymentMethodString,
                        cardNumber = hotelOnChange.cardNumber,
                        currency = "Ringgit Malaysia",
                        userID = userID
                    )

                    remoteBookingViewModel.addNewIntegratedRecord(booking, localPayment)
                    showDialog = true
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colorScheme.primary,
                    contentColor = AppTheme.colorScheme.onPrimary
                ),
                enabled = if(isTablet) cardNumber.isNotEmpty() && paymentMethod != PaymentMethod.NotSelected else true,
                modifier = Modifier.fillMaxWidth().padding(if (isTablet) 16.dp else 0.dp)
            ) {
                Text(text = "Next", color = AppTheme.colorScheme.onPrimary)
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
                    text = extractMalaysianState(hotelData.address).toString(),
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