package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "trip_package",
//    foreignKeys = [
//        ForeignKey(
//            entity = HotelTable::class,
//            parentColumns = ["id"],
//            childColumns = ["hotelID"]
//        )
//    ],
//    indices = [Index(value = ["hotelID"])]
)
data class TripPackage(
    @PrimaryKey val id: String,
    val name: String,
    val price: Double,
    val location: String,
    val description: String,
    val slots: Int,
    val imageUrl: String,
    val hotelID: String,
)
