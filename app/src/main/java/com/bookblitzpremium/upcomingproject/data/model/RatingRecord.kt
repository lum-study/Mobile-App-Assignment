package com.bookblitzpremium.upcomingproject.data.model

data class RatingRecord(
    val id: String = "",
    val title: String = "",
    val rating: Float = 0.0f,
    val review: String = "",
    val date: String = "",
    val imageBase64: String? // resource of the image
)