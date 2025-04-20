package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.firebase.firestore.IgnoreExtraProperties

@IgnoreExtraProperties
@Entity(
    tableName = "user",
)
data class User(
    @PrimaryKey val id: String = "",
    val name: String = "",
    val email: String = "",
)