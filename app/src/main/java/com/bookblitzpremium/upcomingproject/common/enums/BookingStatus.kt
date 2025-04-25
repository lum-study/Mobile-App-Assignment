package com.bookblitzpremium.upcomingproject.common.enums

enum class BookingStatus(val title: String) {
    Cancelled(title = "Cancelled"),
    Confirmed(title = "Confirmed"),
    ToReview(title = "Review"),
    Completed(title = "Completed"),
}