package com.bookblitzpremium.upcomingproject.data.model

sealed class AuthState{
    object Authenticated : AuthState()
    object Unauthenticated : AuthState()
    object Loading : AuthState()
    object Triggerable: AuthState()
    data class Error(val message : String) : AuthState()
}