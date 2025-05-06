package com.bookblitzpremium.upcomingproject

import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.Booking.PaymentDetails
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ReviewFinalPackageSelected
import com.bookblitzpremium.upcomingproject.ui.screen.booking.stringToPaymentMethod
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

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

    when (deviceType) {
        DeviceType.MobilePortrait -> {
            val payment = HotelDetails(
                totalPrice = totalPrice,
                startDate = startDate,
                endDate = endDate,
                numberOFClient = totalPerson,
                numberOfRoom = roomBooked,
                paymentID = paymentId,
                paymentMethodString = paymentMethod,
                cardNumber = cardNumber
            )
            println(payment.paymentMethodString)

            // Update saveData with the new payment details
            saveData.updatePaymentMethod(payment.paymentMethodString)
            saveData.updateAdultCount(payment.numberOFClient)
            saveData.updatePaymentId(payment.paymentID)
            saveData.updateRoomCount(payment.numberOfRoom)
            saveData.updateEndDateDetails(payment.endDate)
            saveData.updateStartDateDetails(payment.startDate)
            saveData.updateTotalPrice(payment.totalPrice)
            saveData.updateCardNumber(payment.cardNumber)

            ReviewFinalPackageSelected(
                hotelID = hotelID,
                modifier = Modifier,
                navController = navController,
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