package com.bookblitzpremium.upcomingproject.Booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.HandleRotateState
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
import com.bookblitzpremium.upcomingproject.ui.components.HeaderDetails
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.net.URLEncoder
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters


@Composable
fun HotelBookingHorizontalScreen(
    navController: NavController,
    hotelID: String,
    saveData: HandleRotateState,
    isOrder: String = "false",
    bookingID:String = "",
    numberOfRoom :String = "",
    numberOFClient :String = "",
    startDate :String = "",
    endDate :String = "",
    viewModel: LocalHotelViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    val hotelDetails by saveData.hotelDetails.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getHotelByID(hotelID)
    }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)
    var previousDeviceType by rememberSaveable { mutableStateOf(deviceType) }

    LaunchedEffect(Unit) {
        if (deviceType == previousDeviceType) {
            saveData.clearHotelDetails()
        }
        previousDeviceType = deviceType
    }

    val initialRoomCount: Int = if (bookingID.isNotEmpty()) {
        numberOfRoom.toIntOrNull() ?: 1
    } else {
        hotelDetails.numberOfRoom.toIntOrNull() ?: 1
    }

    val initialClientCount: Int = if (bookingID.isNotEmpty()) {
        numberOFClient.toIntOrNull() ?: 1
    } else {
        hotelDetails.numberOFClient.toIntOrNull() ?: 1
    }

    var numberOfRoom by remember { mutableStateOf(initialRoomCount) }
    var numberOFClient by remember { mutableStateOf(initialClientCount ) }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val today = LocalDate.now()
    val twoDaysFromNow = today.plusDays(2)

    var startDate by remember { mutableStateOf(if(bookingID.isNotEmpty()) startDate else if(hotelDetails.startDate.isNotEmpty()) hotelDetails.startDate else today.format(formatter).toString()) }
    var endDate by remember { mutableStateOf( if(bookingID.isNotEmpty()) endDate else if(hotelDetails.endDate.isNotEmpty()) hotelDetails.endDate else twoDaysFromNow.format(formatter).toString()) }
    println("startDate of the Horizontal + ${startDate}")
    println("endDate of the Horizontal + ${endDate}")
    // Dialog states
    var showFigureDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }

    val hotel by viewModel.selectedHotel.collectAsState()

    LaunchedEffect(startDate,endDate) {
        saveData.updateStartDateDetails(startDate)
        saveData.updateEndDateDetails(endDate)
        saveData.updateRoomCount(numberOfRoom.toString())
        saveData.updateAdultCount(numberOFClient.toString())
    }

    if (hotel!=null) {
        val hotelData = hotel!!
        Row(
            modifier = modifier.fillMaxSize()
                .padding(horizontal = 16.dp)
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
                        imageUrl = hotelData.imageUrl,
                        modifier = Modifier
                            .fillMaxSize()
                            .clip(RoundedCornerShape(16.dp)),
                        contentScale = ContentScale.Crop
                    )

                    val isOrderBoolean = isOrder == "true"
                    if(isOrderBoolean){
                        //display empty for order
                    }else{
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Transparent)
                                .align(Alignment.BottomEnd)
                                .padding(horizontal = 16.dp, vertical = 16.dp),
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

                            val coroutineScope = rememberCoroutineScope()

                            if (showFigureDialog) {
                                DialogFigure(
                                    onDismissRequest = {
                                        if (numberOfRoom >= 0 && numberOFClient >= 0) {
                                            coroutineScope.launch {
                                                saveData.updateRoomCount(numberOfRoom.toString())
                                                saveData.updateAdultCount(numberOFClient.toString())
                                            }
                                        }

                                        showFigureDialog = false
                                    },
                                    onDateSelected = { figure, room ->
                                        numberOFClient = figure ?: 0
                                        numberOfRoom = room ?: 0
                                    }
                                )
                            }

                            if (showDateDialog) {
                                DialogDate(
                                    navController = navController,
                                    onDismissRequest = {
                                        if(startDate.isNotEmpty() && endDate.isNotEmpty()){
                                            coroutineScope.launch {
                                                saveData.updateStartDateDetails(startDate)
                                                saveData.updateEndDateDetails(endDate)
                                            }
                                        }
                                        showDateDialog = false },
                                    onDateSelected = { start, end ->
                                        startDate = start.toString()
                                        endDate = end.toString()
                                    }
                                )
                            }
                        }
                    }

                }

                var copyPaymentID by remember { mutableStateOf("") }
                val paymentViewModel: RemotePaymentViewModel = hiltViewModel()
                val coroutineScope = rememberCoroutineScope()

                val totalPrice = numberOFClient.toDouble() * hotelData.price

                val payment = remember(copyPaymentID) {
                    Payment(
                        createDate = LocalDate.now().toString(),
                        totalAmount = totalPrice,
                        paymentMethod = "Credit Card", // Fixed: Use a valid payment method
                        cardNumber = "",
                        currency = "Ringgit Malaysia",
                        userID = FirebaseAuth.getInstance().currentUser?.uid.toString()
                    ).copy(id = copyPaymentID) // Update paymentID reactively
                }

                Column(
                    modifier = modifier
                        .weight(1f)
                        .padding(16.dp)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(AppTheme.colorScheme.background)
                ) {
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
                            Column(
                                modifier = Modifier
                                    .padding(16.dp)
                            ){
                                FeatureDisplay(
                                    hotel = hotelData.feature,
                                    rating = hotelData.rating,
                                    tabletScreen = true,
                                )
                            }
                        }

                        item {
                            BookingSummaryTable(
                                startDate = startDate.toString(),
                                endDate = endDate.toString(),
                                roomCount = numberOfRoom.toString(),
                                adultCount = numberOFClient.toString()
                            )
                        }

                        item {
                            HotelReviewsSection(
                                showBackButton = 1,
                                modifier = Modifier,
                                hotelID = hotelData.id
                            )
                        }

                        val hotelID = hotelID
                        val totalPerson = numberOFClient
                        val roomBooked = numberOfRoom
                        val paymentMethod = payment.paymentMethod

                        val isOrderBoolean = isOrder == "true"
                        if(isOrderBoolean){
                            //display empty for order
                        }else{
                            item {
                                val cardNumber = ""
                                Button(
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = AppTheme.colorScheme.primary,
                                        contentColor = AppTheme.colorScheme.onPrimary
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    enabled = startDate != null && endDate != null &&  numberOfRoom!= 0 && numberOFClient != 0,
                                    onClick = {
                                        coroutineScope.launch {

                                            if (numberOfRoom >= 0 && numberOFClient >= 0) {
                                                saveData.updateRoomCount(numberOfRoom.toString())
                                                saveData.updateAdultCount(numberOFClient.toString())
                                            }
                                            if(startDate.isNotEmpty() && endDate.isNotEmpty()){
                                                saveData.updateStartDateDetails(startDate)
                                                saveData.updateEndDateDetails(endDate)
                                            }
                                            saveData.updateTotalPrice(totalPrice.toString())
                                            val paymentID = paymentViewModel.addReturnIDPayment(payment)
                                            if (paymentID.isNotEmpty()) {
                                                val encodedPaymentID = URLEncoder.encode(paymentID, "UTF-8")
                                                saveData.updatePaymentId(paymentID)
                                                navController.navigate(
                                                    "${AppScreen.BookingReview.route}/$hotelID/$startDate/$endDate/$totalPerson/$roomBooked/$totalPrice/$paymentMethod/$cardNumber/$encodedPaymentID"
                                                )
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

//@Composable
//fun PaymentImageReview(hotelImageUrl: String) {
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .background(AppTheme.colorScheme.background),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Text(
//            text = "Packet",
//            style = AppTheme.typography.titleLarge,
//            color = AppTheme.colorScheme.onBackground,
//            modifier = Modifier.padding(bottom = 20.dp)
//        )
//
//        UrlImage(
//            imageUrl = hotelImageUrl,
//            modifier = Modifier
//                .width(350.dp)
//                .height(250.dp)
//                .clip(RoundedCornerShape(8.dp)),
//            contentScale = ContentScale.Crop,
//        )
//    }
//}

//@Composable
//fun HotelInfoCard(
//    hotel: Hotel,
//    reviewCount: Int,
//    navController: NavController,
//    modifier: Modifier = Modifier
//) {
//    Card(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        shape = RoundedCornerShape(16.dp),
//        elevation = CardDefaults.cardElevation(8.dp),
//        colors = CardDefaults.cardColors(containerColor = AppTheme.colorScheme.background)
//    ) {
//        Column {
//            // 1) Hotel image with name overlay
//            Box {
//                UrlImage(
//                    imageUrl = hotel.imageUrl,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .height(200.dp)
//                        .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
//                )
//                // Gradient based on theme
//                Box(
//                    Modifier
//                        .matchParentSize()
//                        .background(
//                            Brush.verticalGradient(
//                                colors = listOf(Color.Transparent, AppTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)),
//                                startY = 100f
//                            )
//                        )
//                )
//                Text(
//                    text = hotel.name,
//                    style = AppTheme.typography.largeBold,
//                    color = AppTheme.colorScheme.onPrimary,
//                    modifier = Modifier
//                        .align(Alignment.BottomStart)
//                        .padding(16.dp)
//                )
//            }
//
//            Spacer(Modifier.height(12.dp))
//
//            // 2) Location & price row
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp),
//                verticalAlignment = Alignment.CenterVertically,
//                horizontalArrangement = Arrangement.SpaceBetween
//            ) {
//                Row(verticalAlignment = Alignment.CenterVertically) {
//
//                    IconButton(
//                        onClick = {
//
//                        }
//                    ) {
//                        Icon(
//                            Icons.Default.LocationOn,
//                            null,
//                            tint = AppTheme.colorScheme.primary
//                        )
//                    }
//                    Spacer(Modifier.width(4.dp))
//                    Text(
//                        hotel.address,
//                        style = AppTheme.typography.labelMedium,
//                        color = AppTheme.colorScheme.secondary
//                    )
//                }
//                Text(
//                    text = "$${"%.2f".format(hotel.price)}/night",
//                    style = AppTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
//                    color = AppTheme.colorScheme.primary
//                )
//            }
//
//            Spacer(Modifier.height(12.dp))
//
//            // 3) Rating chips
//            Row(
//                modifier = Modifier
//                    .horizontalScroll(rememberScrollState())
//                    .padding(start = 16.dp, bottom = 12.dp),
//                horizontalArrangement = Arrangement.spacedBy(8.dp)
//            ) {
//                // star rating chip
//                AssistChip(
//                    onClick = { },
//                    label = { Text("${hotel.rating} ★", style = AppTheme.typography.labelMedium, color = AppTheme.colorScheme.onSurface) }
//                )
//                // review count chip
//                AssistChip(
//                    onClick = { },
//                    label = { Text("$reviewCount reviews", style = AppTheme.typography.labelMedium, color = AppTheme.colorScheme.onSurface) }
//                )
//                // feature chip
//                AssistChip(
//                    onClick = { },
//                    label = { Text(hotel.feature, style = AppTheme.typography.labelMedium, color = AppTheme.colorScheme.onSurface) }
//                )
//            }
//        }
//    }
//}

@Composable
fun HotelInfoSection(
    showBackButton: Int,
    modifier: Modifier,
    navController: NavController,
    hotel: Hotel
) {
    val textOffset = 24.dp

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

                    Spacer(modifier = Modifier.weight(1f))

                    Box(
                        modifier = Modifier
                            .offset(x = -50.dp)
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