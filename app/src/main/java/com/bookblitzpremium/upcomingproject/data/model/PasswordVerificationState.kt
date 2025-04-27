package com.bookblitzpremium.upcomingproject.data.model

sealed class PasswordVerificationState {
    object Idle : PasswordVerificationState()
    object Correct : PasswordVerificationState()
    object Incorrect : PasswordVerificationState()
}
