package com.bookblitzpremium.upcomingproject.common.enums

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Accessible
import androidx.compose.material.icons.filled.AcUnit
import androidx.compose.material.icons.filled.Accessible
import androidx.compose.material.icons.filled.BreakfastDining
import androidx.compose.material.icons.filled.ChildFriendly
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.LocalParking
import androidx.compose.material.icons.filled.Pets
import androidx.compose.material.icons.filled.Pool
import androidx.compose.material.icons.filled.SmokeFree
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.ui.graphics.vector.ImageVector
import java.io.Serializable


enum class Feature(val title: String, val icon: ImageVector): Serializable{

    FreeWifi(
        title = "Free Wi-Fi",
        icon = Icons.Default.Wifi
    ),
    FreeBreakfast(
        title = "Free breakfast",
        icon = Icons.Default.BreakfastDining
    ),
    FreeParking(
        title = "Free Parking",
        icon = Icons.Default.LocalParking
    ),
    Accessible(
        title = "Accessible",
        icon = Icons.Default.Accessible
    ),
    Pool(
        title = "Pool",
        icon = Icons.Default.Pool
    ),
    AirConditioned(
        title = "Air-conditioned",
        icon = Icons.Default.AcUnit
    ),
    PetFriendly(
        title = "Pet-friendly",
        icon = Icons.Default.Pets
    ),
    KidFriendly(
        title = "Kid-friendly",
        icon = Icons.Default.ChildFriendly
    ),
    SmokeFree(
        title = "Smoke-free",
        icon = Icons.Default.SmokeFree
    ),
    FitnessCenter(
        title = "Fitness Center",
        icon = Icons.Default.FitnessCenter
    ),
    Rating(
        title = "Rating",
        icon = Icons.Default.Star
    ),
}