package com.bookblitzpremium.upcomingproject.common.enums

enum class BookingStatus(val title: String) {
    Cancelled(title = "Cancelled"),
    Confirmed(title = "Confirmed"),
    Completed(title = "Completed"),
    InProgress(title = "In Progress"),
    Expired(title = "Expired"),
    Refunded(title = "Refunded"),
    Pending(title = "Pending"),
}