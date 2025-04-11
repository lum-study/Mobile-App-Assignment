package com.bookblitzpremium.upcomingproject.data.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage

@Database(
    entities = [TripPackage::class],
    version = 1,
)
abstract class TripPackageDatabase : RoomDatabase() {
    abstract fun tripPackageDao(): TripPackageDao
}
