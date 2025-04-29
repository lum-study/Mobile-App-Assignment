package com.bookblitzpremium.upcomingproject.data.model

import java.time.LocalDate

data class Calendar(
    val hotelID: String = "",
    val hotelPrice: String = "",
    val minDate: LocalDate? = LocalDate.now(),
    val maxDate: LocalDate? = LocalDate.now().plusYears(1),
    val showNext: Boolean = false
)
