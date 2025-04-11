package com.bookblitzpremium.upcomingproject.di

import android.content.Context
import androidx.room.Room
import com.bookblitzpremium.upcomingproject.data.database.local.TripPackageDatabase
import com.bookblitzpremium.upcomingproject.data.database.local.dao.TripPackageDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)  // Application-wide singleton
object DatabaseModule {
    @Provides
    @Singleton
    fun provideTripPackageDatabase(@ApplicationContext context: Context): TripPackageDatabase {
        return Room.databaseBuilder(
            context,
            TripPackageDatabase::class.java,
            "trip_package_database.db"
        ).build()
    }


    @Provides
    @Singleton
    fun provideTripPackageDao(tripPackageDatabase: TripPackageDatabase): TripPackageDao {
        return tripPackageDatabase.tripPackageDao()
    }
}
