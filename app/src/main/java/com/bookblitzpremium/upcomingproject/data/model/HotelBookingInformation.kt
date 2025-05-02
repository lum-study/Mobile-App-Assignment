package com.bookblitzpremium.upcomingproject.data.model

data class HotelBookingInformation(
    val id: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val numberOFClient: Int = 0,
    val numberOfRoom: Int = 0,
    val hotelID: String = "",
    val userid: String = "",
    val paymentID: String = "",
    val status: String = "",
    val hotelName: String,
    val hotelImageUrl: String,
    val purchaseDate: String,
    val totalAmount: Double,
)
