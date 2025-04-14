package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.HotelDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalHotelRepository @Inject constructor(private val hotelDao: HotelDao) {
    val allHotels: Flow<List<Hotel>> = hotelDao.getAllHotels()

    suspend fun addOrUpdateHotel(hotel: Hotel) = hotelDao.upsertHotel(hotel)
    suspend fun deleteHotel(hotel: Hotel) = hotelDao.deleteHotel(hotel)
    suspend fun getHotelById(id: String) = hotelDao.getHotelById(id)
}