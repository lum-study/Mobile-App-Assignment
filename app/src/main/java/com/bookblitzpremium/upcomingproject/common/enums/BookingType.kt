package com.bookblitzpremium.upcomingproject.common.enums

import java.io.Serializable

enum class BookingType(val title: String): Serializable{
    TripPackage(title = "Trip Package"),
    Hotel(title = "Hotel"),
}