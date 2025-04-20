package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "tp_booking",
    foreignKeys = [
        ForeignKey(
            entity = Payment::class,
            parentColumns = ["id"],
            childColumns = ["paymentID"]
        ),
        ForeignKey(
            entity = TripPackage::class,
            parentColumns = ["id"],
            childColumns = ["tripPackageID"]
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["id"],
            childColumns = ["userID"]
        )
    ],
    indices = [Index(value = ["paymentID"]), Index(value = ["tripPackageID"]), Index(value = ["userID"])]
)
data class TPBooking(
    @PrimaryKey val id: String = "",
    val purchaseCount: Int = 1,
    val paymentID: String = "",
    val tripPackageID: String = "",
    val userID: String = "",
    val status: String = "",
)
