package com.bookblitzpremium.upcomingproject.data.model

data class FlightInformation(
    val id: String,
    val gate: String,
    val travelTime: String,
    val departState: String,
    val arrivalState: String,
    val departCode: String,
    val arrivalCode: String,
    val name: String,
    val endDate: String,
    val endTime: String,
)