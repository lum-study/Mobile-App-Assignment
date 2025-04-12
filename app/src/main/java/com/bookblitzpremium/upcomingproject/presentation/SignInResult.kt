package com.bookblitzpremium.upcomingproject.presentation

data class SignInResult(
    val data: com.bookblitzpremium.upcomingproject.presentation.UserData?,
    val errorMessage:String?

)


data class UserData(
    val userId:String,
    val username:String?,
    val profilePicture:String?
)