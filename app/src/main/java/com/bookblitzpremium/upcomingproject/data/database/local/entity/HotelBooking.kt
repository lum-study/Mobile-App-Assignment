package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(tableName = "hotel_booking")
data class HotelBooking(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val startDate: String = "",
    val endDate: String = "",
    val numberOFClient: Int = 0,
    val numberOfRoom: Int = 0,
    val hotelID: String = "",
    val userid: String = "",
    val paymentID: String = ""
)