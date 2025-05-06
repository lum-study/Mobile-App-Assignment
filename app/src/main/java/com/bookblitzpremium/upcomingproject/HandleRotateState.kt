package com.bookblitzpremium.upcomingproject

import androidx.lifecycle.ViewModel
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject


data class HotelDetails(
    val hotel: Hotel = Hotel(),
    val totalPrice: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val numberOFClient: String = "",
    val numberOfRoom: String = "",
    val paymentID: String = "",
    val paymentMethodString: String = "",
    val paymentMethod: PaymentMethod = PaymentMethod.NotSelected,
    val cardNumber: String = ""
)

@HiltViewModel
class HandleRotateState @Inject constructor() : ViewModel() {

    private val _hotelDetails = MutableStateFlow(HotelDetails())
    val hotelDetails: StateFlow<HotelDetails> = _hotelDetails.asStateFlow()

    init {
        println("Hello")
    }

    fun updateRoomCount(roomCount: String) {
        _hotelDetails.value = _hotelDetails.value.copy(numberOfRoom = roomCount)
    }

    fun updateAdultCount(adultCount: String) {
        _hotelDetails.value = _hotelDetails.value.copy(numberOFClient = adultCount)
    }

    fun updateEndDateDetails(endDate: String) {
        _hotelDetails.value = _hotelDetails.value.copy(endDate = endDate)
    }

    fun updateStartDateDetails(startDate: String) {
        _hotelDetails.value = _hotelDetails.value.copy(startDate = startDate)
    }

    fun updatePaymentMethod(paymentMethod:String){
        _hotelDetails.value = _hotelDetails.value.copy(paymentMethodString = paymentMethod)
    }

    fun updateCardNumber(cardNumber: String){
        _hotelDetails.value = _hotelDetails.value.copy(cardNumber = cardNumber)
    }

    fun updateTotalPrice(price: String){
        _hotelDetails.value = _hotelDetails.value.copy(totalPrice = price)
    }

    fun updatePaymentMethodEnum(paymentMethod: PaymentMethod){
        _hotelDetails.value = _hotelDetails.value.copy(paymentMethod = paymentMethod)
    }

    fun updatePaymentId(paymentID: String){
        _hotelDetails.value = _hotelDetails.value.copy(paymentID = paymentID)
    }

    fun clearHotelDetails() {
        _hotelDetails.value = HotelDetails()
    }
}

