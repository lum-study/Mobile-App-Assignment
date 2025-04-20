package com.bookblitzpremium.upcomingproject.data.database.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bookblitzpremium.upcomingproject.data.database.local.dao.FlightDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.PaymentDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.RatingDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.RecentSearchDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.ScheduleDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TPBookingDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.UserDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.database.local.entity.RecentSearch
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TPBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User

@Database(
    entities = [
        Flight::class,
        Hotel::class,
        Payment::class,
        Rating::class,
        RecentSearch::class,
        Schedule::class,
        TPBooking::class,
        TripPackage::class,
        User::class,
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flightDao(): FlightDao
    abstract fun hotelDao(): HotelDao
    abstract fun paymentDao(): PaymentDao
    abstract fun ratingDao(): RatingDao
    abstract fun recentSearchDao(): RecentSearchDao
    abstract fun scheduleDao(): ScheduleDao
    abstract fun tpBookingDao(): TPBookingDao
    abstract fun tripPackageDao(): TripPackageDao
    abstract fun userDao(): UserDao
}