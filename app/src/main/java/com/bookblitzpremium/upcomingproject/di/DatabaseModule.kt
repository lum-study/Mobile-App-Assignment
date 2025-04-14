package com.bookblitzpremium.upcomingproject.di

import android.content.Context
import androidx.room.Room
import com.bookblitzpremium.upcomingproject.data.database.local.AppDatabase
import com.bookblitzpremium.upcomingproject.data.database.local.dao.FlightDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.RatingDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.ScheduleDao
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
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
    fun provideHotelDao(appDatabase: AppDatabase): HotelDao {
        return appDatabase.hotelDao()
    }

    @Provides
    @Singleton
    fun provideRatingDao(appDatabase: AppDatabase): RatingDao {
        return appDatabase.ratingDao()
    }

    @Provides
    @Singleton
    fun provideScheduleDao(appDatabase: AppDatabase): ScheduleDao {
        return appDatabase.scheduleDao()
    }

    @Provides
    @Singleton
    fun provideTripPackageDao(appDatabase: AppDatabase): TripPackageDao {
        return appDatabase.tripPackageDao()
    }

    @Provides
    @Singleton
    fun provideFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()
}
