package com.bookblitzpremium.upcomingproject.data.model


sealed class VerifyEmail {
    object Idle : VerifyEmail()
    object Loading : VerifyEmail()
    data class Error(val message: String) : VerifyEmail()
    object Verified : VerifyEmail() // ✅ add this for successful email existence
}