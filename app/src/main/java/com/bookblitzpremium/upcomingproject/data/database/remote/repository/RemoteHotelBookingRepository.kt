package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteHotelBookingRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val hotelbookingRef = firestore.collection("booking_hotel")

    suspend fun addHotelBooking(hotelBooking: HotelBooking): String {
        val docRef = hotelbookingRef.document()
        val newHotelBooking = hotelBooking.copy(id = docRef.id)
        docRef.set(newHotelBooking).await()
        return docRef.id
    }

//    suspend fun getHotelBookingsByUserId(userId: String): List<HotelBooking> {
//        return hotelbookingRef
//            .whereEqualTo("userid", userId)
//            .get()
//            .await()
//            .toObjects(HotelBooking::class.java)
//            .mapIndexed { index, booking ->
//                val docId = hotelbookingRef.get().await().documents[index].id
//                booking.copy(id = docId)
//            }
//    }
}