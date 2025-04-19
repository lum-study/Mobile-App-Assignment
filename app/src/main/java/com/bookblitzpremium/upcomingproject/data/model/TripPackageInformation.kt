package com.bookblitzpremium.upcomingproject.data.model

data class TripPackageInformation(
    val id: String,
    val name: String,
    val price: Double,
    val location: String,
    val description: String,
    val slots: Int,
    val imageUrl: String,
    val hotelID: String,
    val flightID: String,
    val startDate: String,
    val hotelName: String,
    val flightDepart: String,
    val flightArrival: String,
    val scheduleDay: Int
)