package com.bookblitzpremium.upcomingproject.Booking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement.spacedBy
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.bookblitzpremium.upcomingproject.ui.components.TripPackageBookingDialog
import com.bookblitzpremium.upcomingproject.ui.screen.booking.DetailsSection
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentOptionScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.time.LocalDate


@Composable
fun PaymentDetails(
    navController: NavController,
    payment: hotelDetails,
    hotel:Hotel,
    modifier: Modifier
) {
    var showReview by remember { mutableStateOf(false) }
    var paymentMethod by remember { mutableStateOf(PaymentMethod.DebitCard) }
    var cardNumber by remember { mutableStateOf("") }
    val remoteBookingViewModel: RemoteHotelBookingViewModel = hiltViewModel()

    //Loading
    val isLoading by remoteBookingViewModel.loading.collectAsState()

    //Handle Alert Dialog
    var showDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxHeight()
            .background(Color.White)
    ){
        if(showReview){
//            TravelHeaderTable()
        }else{
            LazyColumn( // 🔹 Now the whole screen scrolls
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp),
                verticalArrangement = spacedBy(16.dp) // Add spacing between sections
            ) {
                item {
                    Column(
                        verticalArrangement = spacedBy(4.dp),
                        modifier = Modifier
                            .padding(16.dp)
                    ) {
                        PaymentImageReview(hotel.imageUrl)

                        Spacer(modifier = Modifier.height(20.dp))

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ){
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

                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(0.98f)
                                    .padding(bottom = AppTheme.size.normal)
                                    .padding(horizontal = 28.dp)
                            ) {
                                Text(
                                    text = "Pricing",
                                    style = AppTheme.typography.titleLarge
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

                            val coroutineScope = rememberCoroutineScope()
                            val currentUser = FirebaseAuth.getInstance().currentUser
                            var userID = currentUser?.uid.toString()
                            val remotePaymentViewModel: RemotePaymentViewModel = hiltViewModel()

                            if(showDialog){
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
                                    }
                                )
                            }

                            Row {
                                Button(
                                    onClick = {
                                        val booking = HotelBooking(
                                            startDate = payment.startDate,
                                            endDate = payment.endDate,
                                            numberOFClient = payment.totalPerson.toIntOrNull() ?: 1,
                                            numberOfRoom = payment.roomBooked.toIntOrNull() ?: 1,
                                            hotelID = hotel.id,
                                            userid = userID, // Add real user ID if available
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
                                            userID = userID.toString()
                                        )

                                        remotePaymentViewModel.updatePaymentBoth(localPayment)

                                        coroutineScope.launch {
                                            try {
                                                remoteBookingViewModel.addNewIntegratedRecord(booking)
                                                showDialog = true

                                            } catch (e: Exception) {
                                                Log.e("HotelBookingForm", "Failed to save booking: ${e.message}")
                                            }
                                        }
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AppTheme.colorScheme.primary, // Use primary for button
                                        contentColor = AppTheme.colorScheme.onPrimary // Text/icon on primary
                                    ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ) {
                                    Text(
                                        text = "Next",
                                        color = AppTheme.colorScheme.onPrimary // Text on primary
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
}
