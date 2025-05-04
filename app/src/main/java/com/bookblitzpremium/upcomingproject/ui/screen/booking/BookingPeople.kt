package com.bookblitzpremium.upcomingproject.ui.screen.booking


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
import com.bookblitzpremium.upcomingproject.ui.components.TeamMemberDropdown
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentOptionScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.time.LocalDate


@Composable
fun SelectedGuestResult(
    iconRes: ImageVector,
    label: String,
    number: Int
) {
    Box(
        modifier = Modifier
            .width(IntrinsicSize.Min), // Ensures only necessary width
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = iconRes,
                contentDescription = label,
                modifier = Modifier.size(48.dp),
                tint = AppTheme.colorScheme.primary // Use primary for icons
            )


            Text(
                text = number.toString(),
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = AppTheme.colorScheme.onSurface, // Text on surface
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}


@Composable
fun TravelInfoCard() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(AppTheme.colorScheme.primary) // Use primary instead of hardcoded orange
            .padding(16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.icon_logo), // Assumes valid resource
                contentDescription = "Travel Icon",
                tint = AppTheme.colorScheme.onPrimary, // Text/icon on primary
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 16.dp)
            )


            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Enjoy your trip!",
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onPrimary // Text on primary
                )
                Text(
                    text = "Have a safe and pleasant journey.",
                    style = AppTheme.typography.labelMedium,
                    color = AppTheme.colorScheme.onPrimary.copy(alpha = 0.8f) // Slightly transparent
                )
            }
        }
    }
}


@Composable
fun GuestSection(
    modifier: Modifier,
    navController: NavController,
    selectedAdult: Int,
    roomBooked: Int,
    price: String,
    hotelID: String,
    startDate: String,
    endDate: String,
    paymentMethod: PaymentMethod,
    cardNumber: String
) {
    val paymentViewModel: RemotePaymentViewModel = hiltViewModel()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
            .background(AppTheme.colorScheme.background) // Use background for column
    ) {
        Text(
            text = "Total Guest",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colorScheme.onSurface, // Text on surface
            modifier = Modifier
                .padding(start = 12.dp)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SelectedGuestResult(Icons.Filled.Person, "Adults", selectedAdult)
            SelectedGuestResult(Icons.Filled.Hotel, "Room", roomBooked)
        }


        Spacer(modifier = Modifier.weight(1f))


        val coroutineScope = rememberCoroutineScope()
        val paymentmethodToString = paymentMethod.title.toString()


        val currentUser = FirebaseAuth.getInstance().currentUser
        var userID = currentUser?.uid.toString()

        Button(
            onClick = {


                val totalPerson = selectedAdult
                val totalPrice = (price.toDoubleOrNull() ?: 0.0) * roomBooked
                val hotelID = URLEncoder.encode(hotelID, "UTF-8")
                val startDate = URLEncoder.encode(startDate, "UTF-8")
                val endDate = URLEncoder.encode(endDate, "UTF-8")
                val paymentMethod = URLEncoder.encode(paymentmethodToString, "UTF-8")
                val cardNumber = URLEncoder.encode(cardNumber, "UTF-8")


                val payment = Payment(
                    createDate = LocalDate.now().toString(),
                    totalAmount = totalPrice,
                    paymentMethod = paymentMethod,
                    cardNumber = cardNumber,
                    currency = "Ringgit Malaysia",
                    userID = userID.toString()
                )

                coroutineScope.launch {
                    try {
                        val paymentID = paymentViewModel.addPayment(payment)
                        if (paymentID.isNotEmpty()) {
                            val encodedPaymentID = URLEncoder.encode(paymentID, "UTF-8")
                            if(encodedPaymentID.isNotEmpty() && cardNumber.isNotEmpty() && paymentMethod.isNotEmpty()){
                                navController.navigate(
                                    "${AppScreen.BookingReview.route}/$hotelID/$startDate/$endDate/$totalPerson/$roomBooked/$totalPrice/$paymentMethod/$cardNumber/$encodedPaymentID"
                                )
                            }else{
                                //add the resposen to user
                            }
                        } else {
                            // Handle empty paymentID
                            //error appear can make the dialog to redirect the user to the homePage
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colorScheme.primary, // Use primary for button
                contentColor = AppTheme.colorScheme.onPrimary // Text/icon on primary
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Next",
                color = AppTheme.colorScheme.onPrimary // Text on primary
            )
        }
    }
}


@Composable
fun BookingAmount(
    modifier: Modifier,
    navController: NavController,
    hotelID: String,
    hotelPrice: String,
    startDate: String,
    endDate: String
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(vertical = 16.dp, horizontal = 24.dp)
            .background(AppTheme.colorScheme.background), // Use background for column
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TravelInfoCard()


        var selected by rememberSaveable { mutableStateOf<String?>(null) }
        var selectedAdult by rememberSaveable { mutableStateOf(4) }
        var selectedRoom by rememberSaveable { mutableStateOf(1) }
        var paymentMethod by remember { mutableStateOf(PaymentMethod.DebitCard) }
        var cardNumber by remember { mutableStateOf("") }


        val names = listOf(
            "4 Person - 1 Room",
            "8 Person - 2 Room",
            "12 Person - 3 Room",
            "16 Person - 4 Room",
            "20 Person - 5 Room",
            "24 Person - 6 Room",
        )

        Spacer(modifier = Modifier.height(5.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
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
        }


        TeamMemberDropdown(
            options = names,
            selectedOption = selected,
            onOptionSelected = {
                selected = it
                val personRegex = Regex("(\\d+) Person")
                val roomRegex = Regex("(\\d+) Room")

                val personMatch = personRegex.find(it)?.groupValues?.getOrNull(1)?.toIntOrNull()
                val roomMatch = roomRegex.find(it)?.groupValues?.getOrNull(1)?.toIntOrNull()

                selectedAdult = personMatch ?: 1
                selectedRoom = roomMatch ?: 1
            },
            modifier = Modifier
        )


        GuestSection(
            modifier = Modifier,
            navController = navController,
            selectedAdult = selectedAdult,
            roomBooked = selectedRoom,
            price = hotelPrice,
            hotelID = hotelID,
            startDate = startDate,
            endDate = endDate,
            paymentMethod = paymentMethod,
            cardNumber = cardNumber
        )
    }
}
