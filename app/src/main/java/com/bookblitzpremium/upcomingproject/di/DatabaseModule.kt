package com.bookblitzpremium.upcomingproject.di

import android.content.Context
import androidx.room.Room
import com.bookblitzpremium.upcomingproject.data.database.local.AppDatabase
import com.bookblitzpremium.upcomingproject.data.database.local.dao.FlightDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelBookingDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.PaymentDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.RatingDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.RecentSearchDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.ScheduleDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TPBookingDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.UserDao
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTripPackageDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "app_database.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFlightDao(appDatabase: AppDatabase): FlightDao {
        return appDatabase.flightDao()
    }

    @Provides
    @Singleton
    fun provideHotelBookingDao(appDatabase: AppDatabase): HotelBookingDao {
        return appDatabase.hotelBookingDao()
    }

    @Provides
    @Singleton
    fun provideHotelDao(appDatabase: AppDatabase): HotelDao {
        return appDatabase.hotelDao()
    }

    @Provides
    @Singleton
    fun providePaymentDao(appDatabase: AppDatabase): PaymentDao {
        return appDatabase.paymentDao()
    }

    @Provides
    @Singleton
    fun provideRatingDao(appDatabase: AppDatabase): RatingDao {
        return appDatabase.ratingDao()
    }

    @Provides
    @Singleton
    fun provideRecentSearch(appDatabase: AppDatabase): RecentSearchDao {
        return appDatabase.recentSearchDao()
    }

    @Provides
    @Singleton
    fun provideScheduleDao(appDatabase: AppDatabase): ScheduleDao {
        return appDatabase.scheduleDao()
    }

    @Provides
    @Singleton
    fun provideTPBooking(appDatabase: AppDatabase): TPBookingDao {
        return appDatabase.tpBookingDao()
    }

    @Provides
    @Singleton
    fun provideTripPackageDao(appDatabase: AppDatabase): TripPackageDao {
        return appDatabase.tripPackageDao()
    }

    @Provides
    @Singleton
    fun provideUserDao(appDatabase: AppDatabase): UserDao {
        return appDatabase.userDao()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}
