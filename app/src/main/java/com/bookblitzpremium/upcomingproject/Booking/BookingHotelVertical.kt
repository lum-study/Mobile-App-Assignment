package com.bookblitzpremium.upcomingproject.Booking

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
import com.bookblitzpremium.upcomingproject.ui.components.HeaderDetails
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.time.LocalDate
import kotlin.toString

@Composable
fun HotelBookingVerticalScreen(
    showNUmber: Int,
    defaultSize: Dp,
    maxSize: Dp,
    hotelID: String,
    navController: NavController,
    viewModel: LocalHotelViewModel = hiltViewModel()
) {
    var topHeight by remember { mutableStateOf(defaultSize) }
    val minHeight = 100.dp
    val maxHeight = maxSize
    val dragSpeedFactor = 0.3f

    LaunchedEffect(Unit) {
        viewModel.getHotelByID(hotelID)
    }

    var roomCount by remember { mutableStateOf(1) }
    var adultCount by remember { mutableStateOf(1) }

    // Use non-nullable strings with default empty values
    var startDate by remember { mutableStateOf("") }
    var endDate by remember { mutableStateOf("") }

    var showFigureDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }

    val hotel by viewModel.selectedHotel.collectAsState()
    if (hotel != null) {
        val hotelData = hotel!!

        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topHeight)
                    .background(Color.Black)
            ) {
                UrlImage(
                    imageUrl = hotelData.imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

                if (showNUmber == 1 || showNUmber == 3) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.Transparent)
                            .align(Alignment.BottomEnd)
                            .padding(horizontal = 16.dp, vertical = 30.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Button(
                            onClick = { showDateDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Pick Dates",
                                style = TextStyle(fontSize = 16.sp, color = Color.White)
                            )
                        }

                        Button(
                            onClick = { showFigureDialog = true },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = "Select Guests",
                                style = TextStyle(fontSize = 16.sp, color = Color.White)
                            )
                        }
                    }

                    if (showFigureDialog) {
                        DialogFigure(
                            onDismissRequest = { showFigureDialog = false },
                            onDateSelected = { room, figure ->
                                roomCount = room ?: 1 // Default to 1 if null
                                adultCount = figure ?: 1 // Default to 1 if null
                            }
                        )
                    }

                    if (showDateDialog) {
                        DialogDate(
                            navController = navController,
                            onDismissRequest = { showDateDialog = false },
                            onDateSelected = { start, end ->
                                // Ensure start and end are non-null
                                startDate = start?.toString() ?: ""
                                endDate = end?.toString() ?: ""
                            }
                        )
                    }
                }
            }

            Column(modifier = Modifier.background(Color.Black)) {
                DragableToTop(
                    showBackButton = showNUmber,
                    topHeight = topHeight,
                    onDrag = { delta ->
                        val scaledDelta = delta * dragSpeedFactor
                        val newHeight = (topHeight.value + scaledDelta).dp
                        topHeight = newHeight.coerceIn(minHeight, maxHeight)
                    },
                    hotel = hotelData,
                    startDate = startDate,
                    endDate = endDate,
                    adultCount = adultCount,
                    roomBooked = roomCount,
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun DragableToTop(
    showBackButton: Int,
    topHeight: Dp,
    onDrag: (Float) -> Unit,
    hotel: Hotel,
    startDate: String,
    endDate: String,
    roomBooked: Int,
    adultCount: Int,
    navController: NavController
) {
    val textOffset = if (showBackButton == 1) 24.dp else if (showBackButton == 2) 24.dp else 24.dp
    val rangeBetweenLocation = if (showBackButton == 1) 340.dp else if (showBackButton == 2) 150.dp else 500.dp

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
    ) {
        Divider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f)
                .height(6.dp)
                .offset(y = 4.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(Color.LightGray)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        onDrag(delta)
                    }
                )
        )

        Row(verticalAlignment = Alignment.CenterVertically) {
            HeaderDetails(
                hotel.name,
                textOffset,
                modifier = Modifier.padding(top = 12.dp)
            )

            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .offset(x = rangeBetweenLocation)
                    .size(48.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp))
                    .clickable { /* Handle location click */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Divider(
            color = Color.Gray,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = hotel.address,
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.offset(x = textOffset)
        )

        Row(modifier = Modifier.offset(x = textOffset)) {
            repeat(hotel.rating.toInt()) {
                Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
            }
            Text(
                text = "4.5 - 1231 Reviews",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        HotelDescriptionSection(
            showBackButton = 2,
            modifier = Modifier,
            description = ""
        )

        BookingSummaryTable(
            startDate = startDate,
            endDate = endDate,
            roomCount = roomBooked.toString(),
            adultCount = adultCount.toString()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            HotelReviewsSection(
                showBackButton = 1,
                modifier = Modifier,
                hotelID = hotel.id
            )

            val userID = FirebaseAuth.getInstance().currentUser?.uid
                ?: run {
                    Toast.makeText(context, "User not authenticated", Toast.LENGTH_SHORT).show()
                    return@Column
                }

            val totalPrices = roomBooked.toDouble() * hotel.price
            if (roomBooked <= 0) {
                Toast.makeText(context, "Please select at least one room", Toast.LENGTH_SHORT).show()
                return@Column
            }

            if (startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(context, "Please select check-in and check-out dates", Toast.LENGTH_SHORT).show()
                return@Column
            }

            val paymentViewModel: RemotePaymentViewModel = hiltViewModel()
            val payment = Payment(
                createDate = LocalDate.now().toString(),
                totalAmount = totalPrices,
                paymentMethod = "", // Empty
                cardNumber = "",   // Empty
                currency = "Ringgit Malaysia",
                userID = userID
            )

            val totalPerson = adultCount.toString()
            val totalPrice = (hotel.price * roomBooked).toString()

            // Encode parameters
            val hotelID = URLEncoder.encode(hotel.id, "UTF-8")
            val startDate = URLEncoder.encode(startDate, "UTF-8")
            val endDate = URLEncoder.encode(endDate, "UTF-8")
            val paymentMethod =payment.paymentMethod.toString()
            val cardNumber = payment.cardNumber.toString()
            val tabletPortrait = "true"

            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                onClick = {
                    coroutineScope.launch {
                        try {
                            val paymentID = paymentViewModel.addPayment(payment)
                            if (paymentID.isNotEmpty()) {
                                val encodedPaymentID = URLEncoder.encode(paymentID, "UTF-8")

                                val baseRoute = "${AppScreen.BookingReview.route}/$hotelID/$startDate/$endDate/$totalPerson/$roomBooked/$totalPrice/$paymentMethod/$cardNumber/$encodedPaymentID/$tabletPortrait"

                                navController.navigate(baseRoute)
                            } else {
                                Toast.makeText(context, "Failed to create payment", Toast.LENGTH_SHORT).show()
                            }
                        } catch (e: Exception) {
                            println("Debug - Navigation error: ${e.message}")
                            e.printStackTrace()
                            Toast.makeText(context, "Navigation error: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            ) {
                Text(text = "Proceed to Booking Review")
            }
        }
    }
}