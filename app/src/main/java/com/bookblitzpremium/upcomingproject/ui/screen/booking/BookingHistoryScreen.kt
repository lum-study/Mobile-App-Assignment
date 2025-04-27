package com.bookblitzpremium.upcomingproject.ui.screen.booking

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.net.URLEncoder

@Composable
fun HotelBookingItem(
    hotelImageUrl: String?,
    booking: String,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = AppTheme.colorScheme.surface // Use surface for card background
        )
    ) {
        hotelImageUrl?.let {
            UrlImage(
                imageUrl = it,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(32.dp))
                    .clickable(onClick = {
                        if (booking.isNotEmpty()) {
                            navController.navigate("${AppScreen.BookingHistory.route}/$booking")
                            Log.d("Navigation", "${AppScreen.BookingHistory.route}/$booking")
                        } else {
                            Log.d("c", "Booking ID is empty")
                        }
                    }),
                contentScale = ContentScale.Crop,
            )
        } ?: run {
            // Show a placeholder when imageUrl is null
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colorScheme.surfaceVariant), // Use surfaceVariant for placeholder
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No Image",
                    color = AppTheme.colorScheme.onSurface, // Text on surface
                    style = AppTheme.typography.mediumBold
                )
            }
        }
    }
}

@Composable
fun HotelBookingListScreen(navController: NavController, userId: String) {
    val viewModelHotelBooking: LocalHotelBookingViewModel = hiltViewModel()
    val viewModelHotel: LocalHotelViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        viewModelHotelBooking.fetchHotelBookingsByUserId(userId)
    }

    val bookingData by viewModelHotelBooking.hotelUserID.collectAsState()
    val hotelData by viewModelHotel.selectedHotel.collectAsState()

    if (bookingData == null) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                color = AppTheme.colorScheme.primary
            )
        }
    } else if (bookingData.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No Bookings Found",
                color = AppTheme.colorScheme.onSurface,
                style = AppTheme.typography.mediumBold
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colorScheme.background)
                .padding(top = 16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(bookingData) { booking ->
                LaunchedEffect(booking) {
                    viewModelHotel.getHotelByID(booking.hotelID)
                }
                hotelData?.let {
                    HotelBookingItem(
                        hotelImageUrl = it.imageUrl,
                        booking = booking.id.toString(),
                        navController = navController
                    )
                }
            }
        }
    }
}