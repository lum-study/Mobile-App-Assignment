package com.bookblitzpremium.upcomingproject.Booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingStatus
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
import com.bookblitzpremium.upcomingproject.ui.components.NotificationService
import com.bookblitzpremium.upcomingproject.ui.components.TripPackageBookingDialog
import com.bookblitzpremium.upcomingproject.ui.screen.booking.DetailsSection
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentOptionScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate

@Composable
fun PaymentDetails(
    navController: NavController,
    payment: hotelDetails,
    hotel: Hotel,
    modifier: Modifier
) {
    var paymentMethod by remember { mutableStateOf(PaymentMethod.DebitCard) }
    var cardNumber by rememberSaveable { mutableStateOf("") }
    val remoteBookingViewModel: RemoteHotelBookingViewModel = hiltViewModel()

    // Loading and Error States
    val isLoading by remoteBookingViewModel.loading.collectAsState()

    // Handle Alert Dialog
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(AppTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Spacer(modifier = Modifier.height(20.dp))

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
                                .padding(bottom = 16.dp) // Replaced AppTheme.size.normal with 16.dp
                                .padding(horizontal = 28.dp)
                        ) {
                            Text(
                                text = "Pricing",
                                style = AppTheme.typography.titleLarge,
                                color = AppTheme.colorScheme.onBackground
                            )
                        }

                        DetailsSection(
                            totalPrice = payment.totalPrice,
                            totalPerson = payment.totalPerson,
                            roomBooked = payment.roomBooked,
                            startDate = payment.startDate,
                            endDate = payment.endDate,
                            modifier = Modifier
                        )

                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val userID = currentUser?.uid.toString()
                        val remotePaymentViewModel: RemotePaymentViewModel = hiltViewModel()

                        Row {
                            Button(
                                onClick = {
                                    val booking = HotelBooking(
                                        startDate = payment.startDate,
                                        endDate = payment.endDate,
                                        numberOFClient = payment.totalPerson.toIntOrNull() ?: 1,
                                        numberOfRoom = payment.roomBooked.toIntOrNull() ?: 1,
                                        hotelID = hotel.id,
                                        userid = userID,
                                        paymentID = payment.paymentID,
                                        status = BookingStatus.Confirmed.title
                                    )

                                    val localPayment = Payment(
                                        id = payment.paymentID,
                                        createDate = LocalDate.now().toString(),
                                        totalAmount = payment.totalPrice.toDoubleOrNull() ?: 0.0,
                                        paymentMethod = paymentMethod.toString(),
                                        cardNumber = cardNumber,
                                        currency = "Ringgit Malaysia",
                                        userID = userID
                                    )

                                    remotePaymentViewModel.updatePaymentBoth(localPayment)
                                    remoteBookingViewModel.addNewIntegratedRecord(booking)
                                    showDialog = true
                                },
                                enabled = cardNumber.isNotEmpty() && paymentMethod.title.isNotEmpty(),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = AppTheme.colorScheme.primary,
                                    contentColor = AppTheme.colorScheme.onPrimary
                                ),
                                modifier = Modifier.fillMaxWidth()
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

                if (showDialog) {
                    val context = LocalContext.current
                    val noticationService = NotificationService(context)
                    noticationService.showNotification("Booking Successfully", "Thank you for supporting us")

                    val error by remoteBookingViewModel.error.collectAsState() // Add error state
                    TripPackageBookingDialog(
                        isLoading = isLoading,
                        hasError = error ?: "",
                        onHomeButtonClick = {
                            showDialog = it
                            navController.navigate(AppScreen.Home.route) {
                                popUpTo(AppScreen.Home.route) { inclusive = true }
                            }
                        },
                        onViewOrderButtonClick = {
                            showDialog = it
                            navController.navigate(AppScreen.OrderGraph.route) {
                                popUpTo(AppScreen.Home.route)
                            }
                        }
                    )
                }
            }
        }
    }
}