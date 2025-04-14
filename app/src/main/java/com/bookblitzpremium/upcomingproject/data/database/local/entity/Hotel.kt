package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "hotel",
)
data class Hotel(
    @PrimaryKey val id: String = "",
    val imageUrl: String = "",
    val name: String = "",
    val address: String = "",
    val rating: Double = 0.0,
    val price: Double = 0.0,
    val feature: String = "",
)

