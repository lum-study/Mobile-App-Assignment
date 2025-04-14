package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "flight",
)
data class Flight(
    @PrimaryKey val id: String = "",
    val gate: String = "",
    val travelTime: String = "",
    val departState: String = "",
    val arrivalState: String = "",
    val departCode: String = "",
    val arrivalCode: String = "",
    val name: String = "",
)