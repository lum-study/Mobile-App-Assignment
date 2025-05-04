package com.bookblitzpremium.upcomingproject.ui.screen.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.ToastUtils

@Composable
fun HotelBookingListScreen(navController: NavController, userId: String) {
    val viewModelHotelBooking: LocalHotelBookingViewModel = hiltViewModel()

    // Fetch data on first load
    LaunchedEffect(Unit) {
        viewModelHotelBooking.fetchHotelBookingsByUserId(userId)
    }

    val bookingData by viewModelHotelBooking.hotelUserID.collectAsState()
    val hotelMap by viewModelHotelBooking.hotelsMap.collectAsState()

    when {
        false -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        bookingData.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No Bookings Found")
            }
        }

        else -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(bookingData) { booking ->
                    val hotel = hotelMap[booking.hotelID]
                    hotel?.let {
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
}

@Composable
fun HotelBookingItem(
    hotelImageUrl: String?,
    booking: String,
    navController: NavController
) {
    val context = LocalContext.current

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
                        } else {
                            ToastUtils.showSingleToast(context,"Booking ID is empty" )
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
