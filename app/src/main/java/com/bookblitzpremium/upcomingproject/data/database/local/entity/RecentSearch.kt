package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bookblitzpremium.upcomingproject.common.enums.BookingType
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "recent_search",
)
data class RecentSearch(
    @PrimaryKey (autoGenerate = true) val id: Int = 0,
    val keyword: String,
    val option: String = BookingType.Hotel.title,
    val startPrice: Double = 0.0,
    val endPrice: Double = 0.0,
    val rating: Double = 0.0,
    val feature: String = "",
    val departureStation: String = "",
    val arrivalStation: String = "",
    val startDate: String = "",
    val endDate: String = "",
)
