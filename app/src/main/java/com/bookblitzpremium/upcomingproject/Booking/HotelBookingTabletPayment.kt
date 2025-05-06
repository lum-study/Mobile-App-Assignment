package com.bookblitzpremium.upcomingproject.Booking

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.HandleRotateState
import com.bookblitzpremium.upcomingproject.HotelDetails
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingStatus
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.components.MapsComponent
import com.bookblitzpremium.upcomingproject.ui.components.NotificationService
import com.bookblitzpremium.upcomingproject.ui.components.TripPackageBookingDialog
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.screen.booking.DetailsSection
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentOptionScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate



@Composable
fun PaymentDetails(
    navController: NavController,
//    hotelDetail: HotelDetails,
    saveData: HandleRotateState,
    hotelID: String,
    modifier: Modifier = Modifier
) {
    val dataOnChange by saveData.hotelDetails.collectAsState()

    var paymentMethod by remember {
        mutableStateOf<PaymentMethod>(
            if (dataOnChange.paymentMethod != PaymentMethod.NotSelected) {
                dataOnChange.paymentMethod
            } else {
                PaymentMethod.NotSelected
            }
        )
    }

    var cardNumber by rememberSaveable {
        mutableStateOf(
            if (dataOnChange.cardNumber.isNotEmpty()) dataOnChange.cardNumber else ""
        )
    }

    val remoteBookingViewModel: RemoteHotelBookingViewModel = hiltViewModel() // Ensure proper scoping

    // Loading and Error States
    val isLoading by remoteBookingViewModel.loading.collectAsState()

    // Handle Alert Dialog
    var showDialog by remember { mutableStateOf(false) }

    val success by remoteBookingViewModel.success.collectAsState()

    val context = LocalContext.current
    LaunchedEffect(success) {
        if (success) {
            val notificationService = NotificationService(context)
            notificationService.showNotification("Booking Successfully", "Thank you for supporting us")
            saveData.clearHotelDetails()
        }
    }

    LaunchedEffect(paymentMethod, cardNumber) {
        saveData.updatePaymentMethodEnum(paymentMethod)
        saveData.updatePaymentMethod(paymentMethod.title)
        saveData.updateCardNumber(cardNumber)
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

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .padding(16.dp)
            .background(AppTheme.colorScheme.background)
    ) {
        Row(
            modifier = modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
            ) {
                UrlImage(
                    imageUrl = dataOnChange.hotel.imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )
            }

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
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

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.98f)
                                    .padding(bottom = 16.dp)
                            ) {
                                Text(
                                    text = "Pricing",
                                    style = AppTheme.typography.titleLarge,
                                    color = AppTheme.colorScheme.onBackground
                                )
                            }

                            DetailsSection(
                                totalPrice = dataOnChange.totalPrice,
                                totalPerson = dataOnChange.totalPerson,
                                roomBooked = dataOnChange.roomBooked,
                                startDate = dataOnChange.startDate,
                                endDate = dataOnChange.endDate,
                                modifier = Modifier
                            )

                            val currentUser = FirebaseAuth.getInstance().currentUser
                            val userID = currentUser?.uid.toString()
                            println("Debug - Firebase currentUser: $currentUser, userID: $userID")

                            Button(
                                onClick = {
                                    // Log initial state and enabled condition
                                    println("Debug - Button clicked, enabled: ${cardNumber.isNotEmpty() && paymentMethod != PaymentMethod.NotSelected}")
                                    println("Debug - hotelDetail (dataOnChange): $dataOnChange")
                                    println("Debug - hotelID: $hotelID")
                                    println("Debug - userID: $userID")
                                    println("Debug - paymentMethod: $paymentMethod")
                                    println("Debug - cardNumber: $cardNumber")

                                    // Validate and convert fields
                                    val startDate = dataOnChange.startDate
                                    println("Debug - startDate from dataOnChange: $startDate")
                                    val endDate = dataOnChange.endDate
                                    println("Debug - endDate from dataOnChange: $endDate")
                                    val totalPersonInt = dataOnChange.totalPerson.toIntOrNull()
                                    if (totalPersonInt == null) {
                                        println("Debug - Warning: totalPerson '${dataOnChange.totalPerson}' is not a valid integer, defaulting to 1")
                                    } else {
                                        println("Debug - totalPerson converted to: $totalPersonInt")
                                    }
                                    val numberOfClient = totalPersonInt ?: 1
                                    val roomBookedInt = dataOnChange.roomBooked.toIntOrNull()
                                    if (roomBookedInt == null) {
                                        println("Debug - Warning: roomBooked '${dataOnChange.roomBooked}' is not a valid integer, defaulting to 1")
                                    } else {
                                        println("Debug - roomBooked converted to: $roomBookedInt")
                                    }
                                    val numberOfRoom = roomBookedInt ?: 1
                                    val paymentID = dataOnChange.paymentID
                                    println("Debug - paymentID from dataOnChange: $paymentID")
                                    val totalPriceDouble = dataOnChange.totalPrice.toDoubleOrNull()
                                    if (totalPriceDouble == null) {
                                        println("Debug - Warning: totalPrice '${dataOnChange.totalPrice}' is not a valid double, defaulting to 0.0")
                                    } else {
                                        println("Debug - totalPrice converted to: $totalPriceDouble")
                                    }

                                    // Create HotelBooking object
                                    val booking = HotelBooking(
                                        startDate = startDate,
                                        endDate = endDate,
                                        numberOFClient = numberOfClient,
                                        numberOfRoom = numberOfRoom,
                                        hotelID = hotelID,
                                        userid = userID,
                                        paymentID = paymentID,
                                        status = BookingStatus.Confirmed.title
                                    )
                                    println("Debug - Created HotelBooking: $booking")

                                    // Create Payment object
                                    val localPayment = Payment(
                                        id = paymentID,
                                        createDate = LocalDate.now().toString(),
                                        totalAmount = totalPriceDouble ?: 0.0,
                                        paymentMethod = paymentMethod.title,
                                        cardNumber = cardNumber,
                                        currency = "Ringgit Malaysia",
                                        userID = userID
                                    )
                                    println("Debug - Created Payment: $localPayment")

                                    // Log and call ViewModel
                                    println("Debug - Calling addNewIntegratedRecord with booking: $booking, payment: $localPayment")
                                    remoteBookingViewModel.addNewIntegratedRecord(booking, localPayment)
                                    println("Debug - addNewIntegratedRecord called, awaiting result")

                                    // Log dialog state change
                                    showDialog = true
                                    println("Debug - showDialog set to true")
                                },
                                enabled = cardNumber.isNotEmpty() && paymentMethod != PaymentMethod.NotSelected,
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppTheme.colorScheme.primary,
                                    contentColor = AppTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)
                            ) {
                                Text(
                                    text = "Next",
                                    style = AppTheme.typography.mediumBold,
                                    color = AppTheme.colorScheme.onPrimary
                                )
                            }
                            Spacer(modifier = Modifier.height(100.dp))
                        }
                    }
                }
            }
        }
    }
}