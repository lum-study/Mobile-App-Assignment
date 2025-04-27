package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "payment",
//    foreignKeys = [
//        ForeignKey(
//            entity = User::class,
//            parentColumns = ["id"],
//            childColumns = ["userID"]
//        )
//    ],
//    indices = [Index(value = ["userID"])]
)
data class Payment(
    @PrimaryKey val id: String = "",
    val createDate: String = "",
    val totalAmount: Double = 0.0,
    val paymentMethod: String = "",
    val cardNumber: String = "",
    val currency: String = "",
    val userID: String = "",
)