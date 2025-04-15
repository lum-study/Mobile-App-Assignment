package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.Response
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotels
import kotlinx.coroutines.flow.Flow

typealias HotelListResponse = Response<List<Hotels>>
typealias AddHotelResponse = Response<Unit>
typealias UpdateHotelResponse = Response<Unit>
typealias DeleteHotelResponse = Response<Unit>

interface HotelListRepository {
    fun getHotelList(): Flow<HotelListResponse>

}