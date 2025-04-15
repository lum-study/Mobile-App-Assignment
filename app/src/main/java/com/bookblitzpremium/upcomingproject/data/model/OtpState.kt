package com.bookblitzpremium.upcomingproject.data.model

data class OtpState(
    val code: List<Int?> = List(4) { null },
    val focusedIndex: Int? = 0,
    val isValid: Boolean? = null,
    val generatedOtp: String = "",
    val otpGeneratedTime: Long = 0L,
    val isExpired: Boolean = false
)
