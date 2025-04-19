package com.bookblitzpremium.upcomingproject.model

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector

data class TripPackage(
    val imageResource: Int,
    val packageTitle: String,
    val packageDesc: String,
)

data class TripPackagePrice(
    val packages: Int = 0,
    val hotel: Int = 0,
    val transport: Int = 0,
    val extras: Int = 0,
    val discount: Float = 1f,
)

data class TripPackageTabs(
    val title: String,
    val icon: ImageVector,
    val screen: @Composable () -> Unit,
)
