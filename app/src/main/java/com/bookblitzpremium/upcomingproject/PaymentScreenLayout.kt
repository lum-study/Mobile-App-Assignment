package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.Booking.PaymentDetails
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ReviewFinalPackageSelected
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType
import java.net.URLDecoder

@Composable
fun PaymentSizeLayout(
    saveData : HandleRotateState,
    navController: NavController,
    hotelID       : String = "",
    totalPrice    : String = "",
    startDate     : String = "",
    endDate       : String = "",
    totalPerson   : String = "",
    roomBooked    : String = "",
    paymentMethod : String = "",
    paymentId     : String = "",
    cardNumber    : String = "",
) {

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    println("$windowSizeClass + $deviceType")

    val payment = HotelDetails(
        totalPrice = totalPrice,
        startDate = startDate,
        endDate = endDate,
        totalPerson = totalPerson,
        roomBooked = roomBooked,
        paymentID = paymentId,
        paymentMethodString = paymentMethod,
        cardNumber = cardNumber,
    )

    when (deviceType) {
        DeviceType.MobilePortrait -> {
            ReviewFinalPackageSelected(
                hotelID = hotelID,
                modifier = Modifier,
                navController = navController,
                hotelDetail = payment,
                saveData = saveData
            )
        }

        DeviceType.TabletLandscape -> {
            PaymentDetails(
                navController = navController,
//                hotelDetail = payment,
                hotelID = hotelID,
                modifier = Modifier,
                saveData = saveData
            )
        }

        else ->{
            ReviewFinalPackageSelected(
                modifier = Modifier,
                navController = navController,
//                hotelDetail = payment,
                tabletPortrait = "true",
                hotelID = hotelID,
                saveData = saveData
            )
        }
    }
}