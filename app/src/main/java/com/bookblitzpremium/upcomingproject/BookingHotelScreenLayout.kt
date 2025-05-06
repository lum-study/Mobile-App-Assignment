package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
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
    hotelID: String,
    tripPackageID: String = "",
    isOrder : String = "false",
    saveData: HandleRotateState
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    println("$windowSizeClass + $deviceType")

    when (deviceType) {
        DeviceType.MobilePortrait -> {
            HotelDetailScreen(
                navController = navController,
                hotelBookingId = hotelID,
                tripPackageID = tripPackageID
            )
        }
        DeviceType.TabletLandscape -> {
            HotelBookingHorizontalScreen(
                navController = navController,
                hotelID = hotelID,
                saveData = saveData,
                isOrder = isOrder,
                modifier = Modifier
            )
        }
        else -> {
            HotelBookingVerticalScreen(
                defaultSize = 500.dp,
                maxSize = 800.dp,
                hotelID = hotelID,
                isOrder = isOrder,
                navController = navController,
                saveData = saveData,
            )
        }
    }
}