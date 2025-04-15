package com.bookblitzpremium.upcomingproject.data.database.local.RespositoryIml

import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotels
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Response
import com.bookblitzpremium.upcomingproject.data.database.local.repository.HotelListRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FakeHotelListRepository @Inject constructor() : HotelListRepository {
    override fun getHotelList(): Flow<Response<List<Hotels>>> = flow {
        emit(Response.Loading)
        delay(1000) // simulate loading
        val dummyHotels = listOf(
            Hotels(
                imageUrl = "https://via.placeholder.com/150",
                hotelName = "Hotel California",
                description = "A lovely place"
            )
        )
        emit(Response.Success(dummyHotels))
    }
}
