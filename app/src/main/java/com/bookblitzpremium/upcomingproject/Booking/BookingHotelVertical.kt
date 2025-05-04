package com.bookblitzpremium.upcomingproject.Booking

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
import com.bookblitzpremium.upcomingproject.ui.components.HeaderDetails
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.time.LocalDate

@Composable
fun HotelBookingVerticalScreen(
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

    var roomCount by rememberSaveable { mutableStateOf(1) }
    var adultCount by rememberSaveable { mutableStateOf(1) }

    var startDate by rememberSaveable { mutableStateOf("") }
    var endDate by rememberSaveable { mutableStateOf("") }

    var showFigureDialog by rememberSaveable { mutableStateOf(false) }
    var showDateDialog by rememberSaveable { mutableStateOf(false) }

    val hotel by viewModel.selectedHotel.collectAsState()
    if (hotel != null) {
        val hotelData = hotel!!
        Column(modifier = Modifier.fillMaxSize()) {
            // Top Image Section
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(topHeight)
                    .background(AppTheme.colorScheme.surfaceVariant) // Use surfaceVariant instead of black
            ) {
                UrlImage(
                    imageUrl = hotelData.imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop
                )

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
                            containerColor = AppTheme.colorScheme.primary,
                            contentColor = AppTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Pick Dates",
                            style = AppTheme.typography.mediumBold,
                            color = AppTheme.colorScheme.onPrimary
                        )
                    }

                    Button(
                        onClick = { showFigureDialog = true },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.primary,
                            contentColor = AppTheme.colorScheme.onPrimary
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f)
                    ) {
                        Text(
                            text = "Select Guests",
                            style = AppTheme.typography.mediumBold,
                            color = AppTheme.colorScheme.onPrimary
                        )
                    }
                }

                if (showFigureDialog) {
                    DialogFigure(
                        onDismissRequest = { showFigureDialog = false },
                        onDateSelected = { room, figure ->
                            roomCount = room ?: 1
                            adultCount = figure ?: 1
                        }
                    )
                }

                if (showDateDialog) {
                    DialogDate(
                        navController = navController,
                        onDismissRequest = { showDateDialog = false },
                        onDateSelected = { start, end ->
                            startDate = start?.toString() ?: ""
                            endDate = end?.toString() ?: ""
                        }
                    )
                }
            }

            // Main Content with Draggable Section
            DraggableObject(
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

@Composable
fun DraggableObject(
    onDrag: (Float) -> Unit,
    hotel: Hotel,
    startDate: String,
    endDate: String,
    roomBooked: Int,
    adultCount: Int,
    navController: NavController
) {
    val textOffset = 24.dp
    val coroutineScope = rememberCoroutineScope()
    val paymentViewModel: RemotePaymentViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .background(AppTheme.colorScheme.background, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
    ) {
        Divider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f)
                .height(6.dp)
                .clip(RoundedCornerShape(32.dp))
                .background(AppTheme.colorScheme.surfaceVariant)
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
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.width(550.dp))
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .background(AppTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
                    ,
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = AppTheme.colorScheme.onBackground,
                    modifier = Modifier.size(28.dp).clickable {
                        navController.navigate("${AppScreen.Maps.route}/${hotel.name}")
                    }
                )
            }
        }

        Divider(
            color = AppTheme.colorScheme.onSurface,
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )

        Text(
            text = hotel.address,
            color = AppTheme.colorScheme.secondary,
            style = AppTheme.typography.labelMedium,
            modifier = Modifier.offset(x = textOffset)
        )

        FeatureDisplay(
            hotel = hotel.feature,
            rating = hotel.rating,
            tabletScreen = true,
            modifier = Modifier
                .padding(top = 8.dp)
                .fillMaxWidth()
                .padding(16.dp)
        )

        BookingSummaryTable(
            startDate = startDate,
            endDate = endDate,
            roomCount = roomBooked.toString(),
            adultCount = adultCount.toString()
        )

        HotelReviewsSection(
            showBackButton = 1,
            modifier = Modifier,
            hotelID = hotel.id
        )

        val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val totalPrices = roomBooked.toDouble() * hotel.price

        val payment = Payment(
            createDate = LocalDate.now().toString(),
            totalAmount = totalPrices,
            paymentMethod = "",
            cardNumber = "",
            currency = "Ringgit Malaysia",
            userID = userID
        )

        val totalPerson = adultCount.toString()
        val totalPrice = (hotel.price * roomBooked).toString()

        // Encode parameters
        val hotelID = URLEncoder.encode(hotel.id, "UTF-8")
        val startDate = URLEncoder.encode(startDate, "UTF-8")
        val endDate = URLEncoder.encode(endDate, "UTF-8")
        val paymentMethod = payment.paymentMethod.toString()
        val cardNumber = payment.cardNumber.toString()
        val tabletPortrait = "true"

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colorScheme.primary,
                contentColor = AppTheme.colorScheme.onPrimary
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            enabled = startDate.isNotEmpty() && endDate.isNotEmpty() && roomBooked != 0 && adultCount != 0,
            onClick = {
                coroutineScope.launch {
                    val paymentID = paymentViewModel.addReturnIDPayment(payment)
                    if (paymentID.isNotEmpty()) {
                        val encodedPaymentID = URLEncoder.encode(paymentID, "UTF-8")
                        val baseRoute =
                            "${AppScreen.BookingReview.route}/$hotelID/$startDate/$endDate/$totalPerson/$roomBooked/$totalPrice/$paymentMethod/$cardNumber/$encodedPaymentID/$tabletPortrait"
                        navController.navigate(baseRoute)
                    }
                }
            }
        ) {
            Text(
                text = "Next",
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onPrimary
            )
        }
    }
}