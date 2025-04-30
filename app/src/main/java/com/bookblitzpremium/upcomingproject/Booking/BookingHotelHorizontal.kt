package com.bookblitzpremium.upcomingproject.Booking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
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
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
import com.bookblitzpremium.upcomingproject.ui.components.HeaderDetails
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.screen.booking.generateHotelDescription
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch
import java.time.LocalDate


@Composable
fun HotelBookingHorizontalScreen(
    onNextButtonClicked: () -> Unit,
    hotelID: String,
    navController: NavController,
    viewModel: LocalHotelViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {

    LaunchedEffect(Unit) {
        viewModel.getHotelByID(hotelID)
    }

    var roomCount: Int? by remember { mutableStateOf(1)}
    var adultCount: Int? by remember { mutableStateOf(1)}

    var startDate by remember { mutableStateOf<String?>(null) }
    var endDate by remember { mutableStateOf<String?>(null) }

    // Dialog states
    var showFigureDialog by remember { mutableStateOf(false) }
    var showDateDialog by remember { mutableStateOf(false) }
    val hotel by viewModel.selectedHotel.collectAsState()

    var rede by remember { mutableStateOf(false) }

    if (hotel != null) {
        val hotelData = hotel!!
        Log.d("HotelHeaderTable", "Hotel ID: ${hotelData.id}, Address: ${hotelData.address}")

        Row(modifier = modifier.fillMaxSize()) {
            Row(
                modifier = modifier.fillMaxSize()
            ) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                ){
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
                        horizontalArrangement = spacedBy(16.dp)
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
                            Text(text = "Pick Dates", style = TextStyle(fontSize = 16.sp, color = Color.White))
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
                            Text(text = "Select Guests", style = TextStyle(fontSize = 16.sp, color = Color.White))
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
                            onDismissRequest = {  showDateDialog = false },
                            onDateSelected = { start, end ->
                                startDate = start.toString()
                                endDate = end.toString()
                            }
                        )
                    }
                }

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                        .background(Color.White)
                ) {
                    val totalPrice = (roomCount ?: 0).toDouble() * hotelData.price
                    var copyPaymnetID = ""

                    val payment = hotelDetails(
                        totalPrice = totalPrice.toString(),
                        totalPerson =  (adultCount ?: 0).toString(),
                        roomBooked = (roomCount ?: 0).toString(),
                        startDate = startDate.toString(),
                        endDate = endDate.toString(),
                        paymentID = copyPaymnetID
                    )

                    if(rede){
                        PaymentDetails(
                            navController = navController,
                            payment = payment,
                            hotel = hotelData,
                            modifier = Modifier
                        )

                    }else{
                        LazyColumn {
                            item {
                                HotelInfoSection(
                                    showBackButton = 2,
                                    modifier = Modifier,
                                    address = hotelData.address,
                                    rating = hotelData.rating,
                                    hotelName = hotelData.name,
                                    reviewTime = 1
                                )
                            }

                            item { HotelDescriptionSection(showBackButton = 1, modifier = Modifier,generateHotelDescription(hotelData.name,hotelData.rating)) }

                            item{
                                FeatureDisplay(
                                    hotel = hotelData
                                )
                            }

                            item{
                                BookingSummaryTable(
                                    startDate = startDate.toString(),
                                    endDate = endDate.toString(),
                                    roomCount = roomCount.toString(),
                                    adultCount = adultCount.toString()
                                )
                            }

                            item{
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
                                        containerColor = Color.Black,
                                        contentColor = Color.White
                                    ),
                                    shape = RoundedCornerShape(16.dp),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),
                                    enabled = startDate != null && endDate != null && roomCount != null && adultCount !=null,
                                    onClick = {

                                        coroutineScope.launch {
                                            try {
                                                val paymentID = paymentViewModel.addPayment(payment)
                                                if (paymentID.isNotEmpty()) {
                                                    copyPaymnetID = paymentID
                                                    rede = true
                                                } else {
                                                    // Handle empty paymentID
                                                }
                                            } catch (e: Exception) {
                                                e.printStackTrace()
                                            }
                                        }
                                    }
                                ) {
                                    Text(text = stringResource(R.string.next_button))
                                }
                            }
                            item { Spacer(modifier = Modifier.height(100.dp)) }
                        }
                    }
                }
            }
        }
    } else {
        // Handle loading or error state
        CircularProgressIndicator(modifier = Modifier)
    }
}


@Composable
fun PaymentImageReview(hotelImageUrl: String) {
    Column( // ⬅️ Replacing LazyColumn with Column
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "Packet",
            style = AppTheme.typography.titleLarge,
            modifier = Modifier
                .padding(bottom = 20.dp)
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
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(8.dp)
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
                // Dark gradient to make text readable
                Box(
                    Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color(0x80000000)),
                                startY = 100f
                            )
                        )
                )
                Text(
                    text = hotel.name,
                    style = MaterialTheme.typography.headlineSmall.copy(color = Color.White, fontWeight = FontWeight.Bold),
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
                    Icon(Icons.Default.LocationOn, null, tint = MaterialTheme.colorScheme.primary)
                    Spacer(Modifier.width(4.dp))
                    Text(hotel.address, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                }
                Text(
                    text = "$${"%.2f".format(hotel.price)}/night",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(Modifier.height(12.dp))

            // 3) Rating chips
            Row(
                modifier = Modifier
                    .horizontalScroll(rememberScrollState())
                    .padding(start = 16.dp, bottom = 12.dp),
                horizontalArrangement = spacedBy(8.dp)
            ) {
                // star rating chip
                AssistChip(
                    onClick = { },
                    label = { Text("${hotel.rating} ★") }
                )
                // review count chip
                AssistChip(
                    onClick = { },
                    label = { Text("$reviewCount reviews") }
                )
                // feature chip
                AssistChip(
                    onClick = { },
                    label = { Text(hotel.feature) }
                )
            }
        }
    }
}


@Composable
fun HotelInfoSection(
    showBackButton: Int,
    modifier: Modifier,
    address:String,
    rating: Double,
    hotelName:String,
    reviewTime:Int
) {
    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
    val rangeBetweenLocation = if (showBackButton == 1) 340.dp  else if (showBackButton == 2) 150.dp else 500.dp

    Column(
        modifier = Modifier
            .background(Color.White , RoundedCornerShape(32.dp))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = spacedBy(8.dp),
                modifier = Modifier
            ) {

                if(showBackButton == 2){
                    Divider(
                        modifier = Modifier
                            .offset(x = 100.dp, y = -4.dp)
                            .fillMaxWidth(0.5f)
                            .height(4.dp)
                            .background(Color.Red)
                            .shadow(
                                elevation = 0.dp,
                                shape = RoundedCornerShape(32.dp),
                                clip = true,
                                ambientColor = Color.LightGray,
                            )
                    )
                } else if (showBackButton != 2 && showBackButton!= 1){
                    Divider(
                        modifier = Modifier
                            .offset(x = 260.dp, y = -4.dp)
                            .fillMaxWidth(0.3f)
                            .height(4.dp)
                            .background(Color.LightGray)
                            .shadow(
                                elevation = 0.dp,
                                shape = RoundedCornerShape(32.dp),
                                clip = true,
                                ambientColor = Color.LightGray,
                            )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeaderDetails(hotelName, textOffset, modifier = Modifier)

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
                    text = address,
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.offset(x = textOffset)
                )

                Row(modifier = Modifier.offset(x = textOffset)) {
                    repeat(rating.toInt()){
                        Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
                    }
                    Text(
                        text = "Review ${reviewTime.toString()}",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}
