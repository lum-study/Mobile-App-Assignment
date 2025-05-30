package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties


@IgnoreExtraProperties
@Entity(
    tableName = "trip_package",
    foreignKeys = [
        ForeignKey(
            entity = Hotel::class,
            parentColumns = ["id"],
            childColumns = ["hotelID"]
        ),
        ForeignKey(
            entity = Flight::class,
            parentColumns = ["id"],
            childColumns = ["flightID"]
        )
    ],
    indices = [Index(value = ["hotelID"]), Index(value = ["flightID"])]
)
data class TripPackage(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val location: String = "",
    val description: String = "",
    val slots: Int = 0,
    val imageUrl: String = "",
    val hotelID: String = "",
    val flightID: String = "",
    val startDate: String = "",
)
