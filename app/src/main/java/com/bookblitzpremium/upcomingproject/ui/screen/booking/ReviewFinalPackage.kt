package com.bookblitzpremium.upcomingproject.ui.screen.booking

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemotePaymentViewModel
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import kotlinx.coroutines.launch
import java.time.LocalDate

@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
fun PreviewFinalPackage() {
    val navController = rememberNavController()

    ReviewFinalPackageSelected(
        navController = navController,
        modifier = Modifier,
        hotelID = "fdgdfgf",
        totalPrice = "1000.00",
        startDate = "2025-05-23",
        endDate = "2025-05-25",
        totalPerson = "4",
        roomBooked = "1",
        paymentID = "vgdfgfhfh",
        paymentMethod = "DebitCard",
        cardNumber = "1234567890123456"
    )
}

@Composable
fun ReviewFinalPackageSelected(
    navController: NavController,
    modifier: Modifier,
    hotelID: String,
    totalPrice: String,
    startDate: String,
    endDate: String,
    totalPerson: String,
    roomBooked: String,
    paymentID: String,
    paymentMethod: String,
    cardNumber: String
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize()
            .background(AppTheme.colorScheme.background), // Use background for column
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val viewModel: LocalHotelViewModel = hiltViewModel()
        val localBookingViewModel: LocalHotelBookingViewModel = hiltViewModel()
        val remoteBookingViewModel: RemoteHotelBookingViewModel = hiltViewModel()
        val remotePaymentViewModel: RemotePaymentViewModel = hiltViewModel()
        val loading = viewModel.loading.collectAsState()
        val hotel by viewModel.selectedHotel.collectAsState()

        LaunchedEffect(Unit) {
            viewModel.getHotelByID(hotelID)
        }

        if (loading.value) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = AppTheme.colorScheme.primary // Use primary for loader
                )
            }
        } else if (hotel != null) {
            val hotelData = hotel!!

            StyledImage(hotelData.imageUrl.toString())

            Spacer(modifier = Modifier.height(18.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = hotelData.name,
                    style = AppTheme.typography.largeBold,
                    color = AppTheme.colorScheme.onSurface // Text on surface
                )
                Row(
                    modifier = Modifier,
                ) {
                    repeat(3) {
                        Text(
                            text = "⭐",
                            color = AppTheme.colorScheme.secondary, // Use secondary for stars
                            modifier = Modifier
                        )
                    }

                    Row(
                        modifier = Modifier
                            .padding(start = 24.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(50.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.LocationOn,
                            contentDescription = "Location",
                            tint = AppTheme.colorScheme.primary, // Use primary for icon
                            modifier = Modifier
                                .size(24.dp)
                        )

                        Text(
                            text = "Malaysia Johor",
                            style = AppTheme.typography.mediumBold,
                            color = AppTheme.colorScheme.onSurface, // Text on surface
                            modifier = Modifier
                                .padding(start = 12.dp)
                        )
                    }
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(12.dp))
                    .background(AppTheme.colorScheme.surface) // Use surface for background
                    .border(1.dp, AppTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Check-In half
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LegendItem1(
                        icon = Icons.Filled.CalendarToday,
                        iconDescription = "Check-In Icon",
                        label = "Check-In",
                        date = startDate,
                        modifier = Modifier
                    )
                }

                // Vertical divider
                Divider(
                    color = AppTheme.colorScheme.primary, // Use primary for divider
                    modifier = Modifier
                        .width(1.dp)
                        .fillMaxHeight()
                )

                // Check-Out half
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LegendItem1(
                        icon = Icons.Filled.CalendarToday,
                        iconDescription = "Check-Out Icon",
                        label = "Check-Out",
                        date = endDate,
                        modifier = Modifier
                    )
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            DetailsSection(
                totalPrice = totalPrice,
                totalPerson = totalPerson,
                roomBooked = roomBooked,
                modifier = Modifier
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
                        paymentID = paymentID
                    )

                    val localPayment = Payment(
                        id = paymentID,
                        createDate = LocalDate.now().toString(),
                        totalAmount = totalPrice.toDoubleOrNull() ?: 0.0,
                        paymentMethod = paymentMethod,
                        cardNumber = cardNumber,
                        currency = "Ringgit Malaysia",
                        userID = "userID"
                    )

                    remotePaymentViewModel.updatePayment(localPayment)

                    coroutineScope.launch {
                        try {
                            // Step 1: Save to Firestore and get the ID
                            val firestoreId = remoteBookingViewModel.addHotelBooking(booking)
                            // Step 2: Update the booking with the Firestore ID
                            val updatedBooking = booking.copy(id = firestoreId)
                            // Step 3: Save to local database with the Firestore ID
                            localBookingViewModel.insertHotelBooking(updatedBooking)
                            // Step 4: Navigate after both operations are complete
                            navController.navigate(AppScreen.Home.route)
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
        }
    }
}

@Composable
fun LegendItem1(
    icon: ImageVector,
    iconDescription: String,
    label: String,
    date: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = iconDescription,
                tint = AppTheme.colorScheme.primary, // Use primary for icon
                modifier = Modifier.size(24.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = label,
                style = AppTheme.typography.smallBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }

        Column {
            Text(
                text = date,
                style = AppTheme.typography.smallBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    val startDate = "2023-05-23"
    val endDate = "2023-05-25"

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(width = 1.dp, color = AppTheme.colorScheme.primary)
            .height(50.dp)
            .background(AppTheme.colorScheme.surface), // Use surface for background
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Check-In section
        LegendItem1(
            icon = Icons.Filled.CalendarToday,
            iconDescription = "Check-In Icon",
            label = "Check-In",
            date = startDate,
            modifier = Modifier.weight(1f)
        )

        // Spacer for consistent spacing between sections
        Spacer(modifier = Modifier.width(16.dp))

        // Check-Out section
        LegendItem1(
            icon = Icons.Filled.CalendarToday,
            iconDescription = "Check-Out Icon",
            label = "Check-Out",
            date = endDate,
            modifier = Modifier.weight(1f)
        )
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
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
//            .background(AppTheme.colorScheme.surface), // Use surface for background
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Adult",
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
            Text(
                text = totalPerson,
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Room",
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
            Text(
                text = roomBooked,
                style = AppTheme.typography.mediumBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }

        Divider(
            color = AppTheme.colorScheme.primary, // Use primary for divider
            thickness = 1.dp
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Total Price",
                style = AppTheme.typography.largeBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
            Text(
                text = "RM $totalPrice",
                style = AppTheme.typography.largeBold,
                color = AppTheme.colorScheme.onSurface // Text on surface
            )
        }
    }
}

@Composable
fun StyledImage(
    hotelImages: String
) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surface // Use surface for card
        )
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