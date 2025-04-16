package com.bookblitzpremium.upcomingproject.di

import com.bookblitzpremium.upcomingproject.data.database.local.RespositoryIml.FakeHotelListRepository
import com.bookblitzpremium.upcomingproject.data.database.local.repository.HotelListRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HotelModule {
    @Provides
    @Singleton
    fun provideHotelListRepository(): HotelListRepository {
        return FakeHotelListRepository()
    }
}
