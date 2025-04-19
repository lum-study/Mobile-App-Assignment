package com.bookblitzpremium.upcomingproject.data.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bookblitzpremium.upcomingproject.data.database.local.dao.FlightDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.RatingDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.RecentSearchDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.ScheduleDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.database.local.entity.RecentSearch
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage

@Database(
    entities = [
        Flight::class,
        Hotel::class,
        Rating::class,
        TripPackage::class,
        Schedule::class,
        RecentSearch::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flightDao(): FlightDao
    abstract fun hotelDao(): HotelDao
    abstract fun ratingDao(): RatingDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun tripPackageDao(): TripPackageDao
    abstract fun recentSearchDao(): RecentSearchDao
}