package com.BookBlitzPremium.Upcomingproject.Model

data class FlightSchedule(
    val companyName: String,
    val flightID: String,
    val gateID: String,
    val travelTime: String,
    val departure: AirportInfo,
    val arrival: AirportInfo,
)

data class AirportInfo(
    val city: String,
    val code: String,
    val date: String,
    val time: String,
)