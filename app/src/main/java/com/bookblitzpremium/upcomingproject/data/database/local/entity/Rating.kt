package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "rating",
    foreignKeys = [
        ForeignKey(
            entity = Hotel::class,
            parentColumns = ["id"],
            childColumns = ["hotelID"]
        )
    ],
    indices = [Index(value = ["hotelID"])]
)
data class Rating(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val description: String = "",
    val rating: Int = 0,
    val icon: String = "",
    val hotelID: String = "",
    val userID: String = "",
)