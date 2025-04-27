// data/database/local/entity/Profile.kt
package com.bookblitzpremium.upcomingproject.data.database.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile")
data class Profile(
    @PrimaryKey
    val id: String = "",
    val userName: String = "",
    val email: String = "",
    val phone: String= "",
    val dob: String= "",
    val address: String= "",
    val profileImagePath: String? = ""
)