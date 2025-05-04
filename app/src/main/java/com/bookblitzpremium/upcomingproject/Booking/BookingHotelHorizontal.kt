package com.bookblitzpremium.upcomingproject.Booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.AssistChip
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
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
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters


@Composable
fun HotelBookingHorizontalScreen(
    hotelID: String,
    navController: NavController,
    viewModel: LocalHotelViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    LaunchedEffect(Unit) {
        viewModel.getHotelByID(hotelID)
    }

    var roomCount: Int? by remember { mutableStateOf(1) }
    var adultCount: Int? by remember { mutableStateOf(4) }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()
    val nextFriday = today.with(TemporalAdjusters.next(DayOfWeek.FRIDAY))

    var startDate by remember { mutableStateOf(today.format(formatter)) }
    var endDate by remember { mutableStateOf(nextFriday.format(formatter)) }

    // Dialog states
    var showFigureDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    val hotel by viewModel.selectedHotel.collectAsState()

    var rede by remember { mutableStateOf(false) }

    if (hotel != null) {
        val hotelData = hotel!!

        Row(modifier = modifier.fillMaxSize()) {
            Row(
                modifier = modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ) {
                    UrlImage(
                        imageUrl = hotelData.imageUrl,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    if (rede) {
                        // Placeholder for payment UI
                    } else {
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
                                    roomCount = room ?: 0
                                    adultCount = figure ?: 0
                                }
                            )
                        }

                        if (showDateDialog) {
                            DialogDate(
                                navController = navController,
                                onDismissRequest = { showDateDialog = false },
                                onDateSelected = { start, end ->
                                    startDate = start.toString()
                                    endDate = end.toString()
                                }
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(AppTheme.colorScheme.background)
                ) {
                    val totalPrice = (roomCount ?: 0).toDouble() * hotelData.price
                    var copyPaymnetID = ""

                    val payment = hotelDetails(
                        totalPrice = totalPrice.toString(),
                        totalPerson = (adultCount ?: 0).toString(),
                        roomBooked = (roomCount ?: 0).toString(),
                        startDate = startDate.toString(),
                        endDate = endDate.toString(),
                        paymentID = copyPaymnetID
                    )

                    if (rede) {
                        PaymentDetails(
                            navController = navController,
                            payment = payment,
                            hotel = hotelData,
                            modifier = Modifier
                        )
                    } else {
                        LazyColumn {
                            item {
                                HotelInfoSection(
                                    showBackButton = 2,
                                    modifier = Modifier,
                                    navController = navController,
                                    hotel = hotelData
                                )
                            }

                            item {
                                FeatureDisplay(
                                    hotel = hotelData.feature,
                                    rating = hotelData.rating,
                                    tabletScreen = true,
                                    modifier = Modifier.padding(16.dp)
                                )
                            }

                            item {
                                BookingSummaryTable(
                                    startDate = startDate.toString(),
                                    endDate = endDate.toString(),
                                    roomCount = roomCount.toString(),
                                    adultCount = adultCount.toString()
                                )
                            }

                            item {
                                HotelReviewsSection(
                                    showBackButton = 1,
                                    modifier = Modifier,
                                    hotelID = hotelData.id
                                )
                            }

                            item {
                                var userID = FirebaseAuth.getInstance().currentUser?.uid

                                var coroutineScope = rememberCoroutineScope()
                                val paymentViewModel: RemotePaymentViewModel = hiltViewModel()

                                val payment = Payment(
                                    createDate = LocalDate.now().toString(),
                                    totalAmount = totalPrice,
                                    paymentMethod = startDate.toString(),
                                    cardNumber = "",
                                    currency = "Ringgit Malaysia",
                                    userID = userID.toString()
                                )

                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AppTheme.colorScheme.primary,
                                        contentColor = AppTheme.colorScheme.onPrimary
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    enabled = startDate != null && endDate != null && roomCount != null && adultCount != null,
                                    onClick = {
                                        coroutineScope.launch {
                                            val paymentID = paymentViewModel.addReturnIDPayment(payment)
                                            if (paymentID.isNotEmpty()) {
                                                copyPaymnetID = paymentID
                                                rede = true
                                            }
                                        }
                                    }
                                ) {
                                    Text(
                                        text = stringResource(R.string.next_button),
                                        style = AppTheme.typography.mediumBold,
                                        color = AppTheme.colorScheme.onPrimary
                                    )
                                }
                            }
                            item { Spacer(modifier = Modifier.height(100.dp)) }
                        }
                    }
                }
            }
        }
    } else {
        CircularProgressIndicator(
            color = AppTheme.colorScheme.primary,
            modifier = Modifier
        )
    }
}

@Composable
fun PaymentImageReview(hotelImageUrl: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(AppTheme.colorScheme.background),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Packet",
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colorScheme.onBackground,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        UrlImage(
            imageUrl = hotelImageUrl,
            modifier = Modifier
                .width(350.dp)
                .height(250.dp)
                .clip(RoundedCornerShape(8.dp)),
            contentScale = ContentScale.Crop,
        )
    }
}

@Composable
fun HotelInfoCard(
    hotel: Hotel,
    reviewCount: Int,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.background)
    ) {
        Column {
            // 1) Hotel image with name overlay
            Box {
                UrlImage(
                    imageUrl = hotel.imageUrl,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                )
                // Gradient based on theme
                Box(
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, AppTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)),
                                startY = 100f
                            )
                        )
                )
                Text(
                    text = hotel.name,
                    style = AppTheme.typography.largeBold,
                    color = AppTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                )
            }

            Spacer(Modifier.height(12.dp))

            // 2) Location & price row
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {

                    IconButton(
                        onClick = {

                        }
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            null,
                            tint = AppTheme.colorScheme.primary
                        )
                    }
                    Spacer(Modifier.width(4.dp))
                    Text(
                        hotel.address,
                        style = AppTheme.typography.labelMedium,
                        color = AppTheme.colorScheme.secondary
                    )
                }
                Text(
                    text = "$${"%.2f".format(hotel.price)}/night",
                    style = AppTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = AppTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(12.dp))

            // 3) Rating chips
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 16.dp, bottom = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // star rating chip
                AssistChip(
                    onClick = { },
                    label = { Text("${hotel.rating} ★", style = AppTheme.typography.labelMedium, color = AppTheme.colorScheme.onSurface) }
                )
                // review count chip
                AssistChip(
                    onClick = { },
                    label = { Text("$reviewCount reviews", style = AppTheme.typography.labelMedium, color = AppTheme.colorScheme.onSurface) }
                )
                // feature chip
                AssistChip(
                    onClick = { },
                    label = { Text(hotel.feature, style = AppTheme.typography.labelMedium, color = AppTheme.colorScheme.onSurface) }
                )
            }
        }
    }
}

@Composable
fun HotelInfoSection(
    showBackButton: Int,
    modifier: Modifier,
    navController: NavController,
    hotel: Hotel
) {
    val textOffset = 24.dp
    val rangeBetweenLocation = 150.dp

    Column(
        modifier = Modifier
            .background(AppTheme.colorScheme.background, RoundedCornerShape(32.dp))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeaderDetails(
                        hotel.name,
                        textOffset,
                        modifier = Modifier
                    )

                    Spacer(modifier = Modifier.width(250.dp))

                    Box(
                        modifier = Modifier
                            .offset(x = rangeBetweenLocation)
                            .size(48.dp)
                            .background(AppTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp))
                            .clickable { /* Handle location click */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = AppTheme.colorScheme.onBackground,
                            modifier = Modifier.size(28.dp).clickable(
                                onClick = {
                                    navController.navigate("${AppScreen.Maps.route}/${hotel.name}")
                                }
                            )
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
            }
        }
    }
}