package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteHotelBookingRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private val hotelbookingRef = firestore.collection("booking_hotel")

    suspend fun addHotelBooking(hotelBooking: HotelBooking): String {
        val docRef = hotelbookingRef.document()
        val newHotelBooking = hotelBooking.copy(id = docRef.id)
        docRef.set(newHotelBooking).await()
        return docRef.id
    }

    suspend fun getAllHotelBooking(): List<HotelBooking> = try {
        hotelbookingRef.get().await().toObjects(HotelBooking::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }

    suspend fun updatePayment(hotelBooking: HotelBooking) {
        try {
            hotelbookingRef
                .document(hotelBooking.id)
                .set(hotelBooking) // Overwrites the entire document
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAllHotelBookingByUserID(userID: String): List<HotelBooking> = try {
        hotelbookingRef.whereEqualTo("userID", userID).get().await()
            .toObjects(HotelBooking::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }
}