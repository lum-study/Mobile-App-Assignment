package com.bookblitzpremium.upcomingproject.ui.screen.booking

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TurnedInNot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.room.PrimaryKey
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import kotlinx.coroutines.launch

@Preview(showBackground = true , widthDp = 500 , heightDp = 1000)
@Composable
fun PreviewFinalPackage(){

    val navController = rememberNavController()

    ReviewFinalPackageSelected(
        navController,
        modifier = Modifier,
        hotelID = "fdgdfgf",
        totalPrice = "fdgdfgf",
        startDate = "fdgdfgf",
        endDate = "fdgdfgf",
        totalPerson = "fdgdfgf",
        roomBooked = "fdgdfgf",
    )
}

@Composable
fun ReviewFinalPackageSelected(
    navController: NavController,
    modifier: Modifier,
    hotelID :String,
    totalPrice :String,
    startDate : String,
    endDate : String,
    totalPerson: String,
    roomBooked: String
){
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
        , verticalArrangement = Arrangement.spacedBy(8.dp)
    ){

        val viewModel : LocalHotelViewModel = hiltViewModel()
        val localBookingViewModel : LocalHotelBookingViewModel = hiltViewModel()
        val remoteBookingViewModel : RemoteHotelBookingViewModel = hiltViewModel()

        val loading = viewModel.loading.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getHotelByID(hotelID)
        }

        val hotel by viewModel.selectedHotel.collectAsState()

        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        } else if (hotel != null) {

            val hotelData = hotel!!

            StyledImage(hotel?.imageUrl.toString())

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ){
                Text(
                    text = hotelData.name,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    modifier = Modifier,
                    ){
                    repeat(3){
                        Text(
                            text = "⭐",
                            modifier = Modifier
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(start = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(50.dp)
                    ){
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                        )

                        Text(
                            text = "Malaysia Johor",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(start = 12.dp)
                        )
                    }
                }
            }

            // Legend
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start =  20.dp),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
            ) {
                LegendItem1(
                    icon = Icons.Filled.CalendarToday,
                    iconDescription = "Check In Icon",
                    label = "CheckIn",
                    date = startDate
                )

                LegendItem1(
                    icon = Icons.Filled.CalendarToday,
                    iconDescription = "Check out Icon",
                    label = " CheckOut",
                    date = endDate
                )

                LegendItem1(
                    icon = Icons.Filled.TurnedInNot,
                    iconDescription = "Ticket Icon",
                    label = " Ticket",
                    date =""
                )
            }

//            Image(
//                painter = painterResource(id = R.drawable.maps),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(150.dp)
//                    .padding(horizontal = 16.dp)
//                    .clip(RoundedCornerShape(16.dp))
//            )
            
            Spacer(modifier = Modifier.weight(1f))

            DetailsSection(
                totalPrice, totalPerson, roomBooked, modifier  = Modifier
            )


            val coroutineScope = rememberCoroutineScope()

            Button(
                onClick = {
                    val booking = HotelBooking(
                        startDate = startDate,
                        endDate = endDate,
                        numberOFClient = totalPerson.toIntOrNull() ?: 1,
                        numberOfRoom = roomBooked.toIntOrNull() ?: 1,
                        hotelID = hotelData.id,
                        userid = "", // Add real user ID if available
                        paymentID = "" // Add real payment ID if available
                    )
//                    coroutineScope.launch{
//                        try {
//                            // Step 1: Save to Firestore and get the ID
//                            val firestoreId = remoteBookingViewModel.addHotelBooking(booking)
//
//                            // Step 2: Update the booking with the Firestore ID
//                            val updatedBooking = booking.copy(id = firestoreId)
//
//                            // Step 3: Save to local database with the Firestore ID
//                            localBookingViewModel.insertHotelBooking(updatedBooking)
//
//                            // Step 4: Navigate after both operations are complete
//                            navController.navigate(AppScreen.Home.route)
//                        } catch (e: Exception) {
//                            Log.e("HotelBookingForm", "Failed to save booking: ${e.message}")
//                        }
//                    }
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black, // Ensure the background is visible
                    contentColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(text = "Next")
            }
        }
    }
}


@Composable
fun LegendItem1(icon: ImageVector, iconDescription: String, label: String,date: String) {
    Column(
        modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ){
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon, // Use the passed icon argument
                contentDescription = iconDescription, // Use the passed description argument
                tint = Color.Black,
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = label, fontSize = 12.sp)
        }

        Column(
        ){
            Text(
                text = date,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun DetailsSection(
    totalPrice: String,
    totalPerson: String,
    roomBooked: String,
    modifier: Modifier
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Adult",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = totalPerson,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Room",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = roomBooked,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Divider(thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Price",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "RM $totalPrice", // or apply formatting if needed
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Composable
fun StyledImage(
    hotelImages: String
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp), // Slight shadow for depth
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)) // Ensure proper clipping
    ) {
        UrlImage(
            imageUrl = hotelImages,
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            contentScale = ContentScale.Crop,
        )
    }
}
