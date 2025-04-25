package com.bookblitzpremium.upcomingproject.data.model

data class TPBookingInformation(
    val id: String,
    val purchaseCount: Int = 1,
    val paymentID: String = "",
    val tripPackageID: String = "",
    val userID: String = "",
    val status: String = "",
    val tripPackageName: String = "",
    val tripPackageStartDate: String = "",
    val tripPackageImageUrl: String = "",
    val paymentAmount: Double = 0.0,
    val purchaseDate: String = "",
)