package com.bookblitzpremium.upcomingproject.ui.screen.hotel

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.Booking.HotelBookingHorizontalScreen
import com.bookblitzpremium.upcomingproject.Booking.HotelBookingVerticalScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.ui.screen.booking.HotelDetailScreen
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

@Composable
fun HotelBookingScreenLayout(
    navController: NavController,
    hotelID :String,
    tripPackageID :String,
){
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    when (deviceType){
        DeviceType.MobilePortrait -> {
            HotelDetailScreen(
                navController = navController,
                hotelBookingId = hotelID,
                tripPackageID = tripPackageID
            )
        }

        DeviceType.TabletLandscape -> {
            HotelBookingHorizontalScreen(
                hotelID = hotelID,
                navController = navController,
                modifier = Modifier
            )
        }

        else -> {
            HotelBookingVerticalScreen(
                defaultSize = 300.dp,
                maxSize =  500.dp,
                hotelID = hotelID,
                navController = navController,
            )
        }
    }
}
