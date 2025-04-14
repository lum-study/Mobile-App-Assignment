package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "schedule",
    foreignKeys = [
        ForeignKey(
            entity = TripPackage::class,
            parentColumns = ["id"],
            childColumns = ["tripPackageID"]
        )
    ],
    indices = [Index(value = ["tripPackageID"])]
)
data class Schedule(
    @PrimaryKey val id: String = "",
    val day: Int = 1,
    val time: String = "",
    val activity: String = "",
    val tripPackageID: String = ""
)