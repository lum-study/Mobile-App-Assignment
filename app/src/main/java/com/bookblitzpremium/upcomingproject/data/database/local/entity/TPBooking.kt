package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "tp_booking",
)
data class TPBooking(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val purchaseCount: Int = 1,
    val paymentID: String = "",
    val userID: String = "",
    val tripPackageID: String = "",
)
