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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.HandleRotateState
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
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
fun HotelBookingVerticalScreen(
    defaultSize: Dp,
    maxSize: Dp,
    hotelID: String,
    isOrder: String = "false",
    bookingID:String = "",
    numberOfRoom :String = "",
    numberOFClient :String = "",
    startDate :String = "",
    endDate :String = "",
    navController: NavController,
    saveData: HandleRotateState,
    hotelViewModel: LocalHotelViewModel = hiltViewModel(),
) {
    var topHeight by remember { mutableStateOf(defaultSize) }
    val minHeight = 100.dp
    val maxHeight = maxSize
    val dragSpeedFactor = 0.3f

    val hotelDetails by saveData.hotelDetails.collectAsState()

    LaunchedEffect(Unit) {
        hotelViewModel.getHotelByID(hotelID)
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

    println("startDate of the Vertical + ${startDate}")
    println("endDate of the Vertical + ${endDate}")

    var showFigureDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }

    val hotel by hotelViewModel.selectedHotel.collectAsState()

    LaunchedEffect(startDate,endDate) {
        saveData.updateStartDateDetails(startDate)
        saveData.updateEndDateDetails(endDate)
        saveData.updateRoomCount(numberOfRoom.toString())
        saveData.updateAdultCount(numberOFClient.toString())
    }

    if (hotel!=null) {
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

                    val isOrderBoolean = isOrder == "true"
                    if(isOrderBoolean){
                        //dispaly empty for order
                    }else{
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
                                text = stringResource(R.string.pick_dates),
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
                                text = stringResource(R.string.select_guests),
                                style = AppTheme.typography.mediumBold,
                                color = AppTheme.colorScheme.onPrimary
                            )
                        }
                    }
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
                            showFigureDialog = false },
                        onDateSelected = { figure,room  ->
                            numberOFClient = figure ?: 1
                            numberOfRoom = room ?: 1
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
                adultCount = numberOFClient,
                roomBooked = numberOfRoom,
                isOrder = isOrder,
                saveData = saveData,
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
    isOrder: String = "false",
    saveData: HandleRotateState,
    navController: NavController
) {
    val textOffset = 24.dp
    val coroutineScope = rememberCoroutineScope()
    val paymentViewModel: RemotePaymentViewModel = hiltViewModel()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp)
            .background(
                AppTheme.colorScheme.background,
                RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
            )
    ) {
        HorizontalDivider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f)
                .height(6.dp)
                .clip(RoundedCornerShape(32.dp))
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta -> onDrag(delta) }
                ),
            color = AppTheme.colorScheme.surfaceVariant,
            thickness = 6.dp
        )

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth() // Ensures the row can push to the end
        ) {
            HeaderDetails(
                hotel.name,
                textOffset,
                modifier = Modifier.padding(top = 2.dp)
            )

            Spacer(modifier = Modifier.weight(1f)) // Pushes the icon to the end

            Box(
                modifier = Modifier
                    .size(48.dp)
                    .offset(x = -50.dp)
                    .background(AppTheme.colorScheme.surfaceVariant, RoundedCornerShape(16.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = stringResource(R.string.location),
                    tint = AppTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .size(28.dp)
                        .clickable {
                            navController.navigate("${AppScreen.Maps.route}/${hotel.name}")
                        }
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 8.dp),
            thickness = 2.dp,
            color = AppTheme.colorScheme.onSurface
        )

        Text(
            text = hotel.address,
            color = AppTheme.colorScheme.secondary,
            style = AppTheme.typography.labelMedium,
            modifier = Modifier.offset(x = textOffset)
        )

        Column(
            modifier = Modifier
                .padding(16.dp)
        ){
            FeatureDisplay(
                hotel = hotel.feature,
                rating = hotel.rating,
                tabletScreen = true,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth()

            )
        }

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

        val hotelID = hotel.id
        val startDate = startDate
        val endDate = endDate
        val paymentMethod = payment.paymentMethod.toString()
        val cardNumber = payment.cardNumber.toString()

        val isOrderBoolean = isOrder == "true"
        if(isOrderBoolean){ //display empty for order
        } else{
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
                        if (roomBooked >= 0 && adultCount >= 0) {
                            saveData.updateRoomCount(roomBooked.toString())
                            saveData.updateAdultCount(adultCount.toString())
                        }
                        if(startDate.isNotEmpty() && endDate.isNotEmpty()){
                            saveData.updateStartDateDetails(startDate)
                            saveData.updateEndDateDetails(endDate)
                        }
                        saveData.updateTotalPrice(totalPrice)
                        val paymentID = paymentViewModel.addReturnIDPayment(payment)
                        if (paymentID.isNotEmpty()) {
                            saveData.updatePaymentId(paymentID)
                            val encodedPaymentID = URLEncoder.encode(paymentID, "UTF-8")
                            val baseRoute =
                                "${AppScreen.BookingReview.route}/$hotelID/$startDate/$endDate/$totalPerson/$roomBooked/$totalPrice/$paymentMethod/$cardNumber/$encodedPaymentID"
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
}
