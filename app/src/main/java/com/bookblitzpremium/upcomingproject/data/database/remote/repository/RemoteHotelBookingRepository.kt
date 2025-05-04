package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteHotelBookingRepository @Inject constructor(
    private val firestore: FirebaseFirestore,
) {
    private val hotelbookingRef = firestore.collection("booking_hotel")

    suspend fun addHotelBooking(hotelBooking: HotelBooking): String {
//        delay(7000L)
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

    suspend fun updateHotelBooking(hotelBooking: HotelBooking) {
        try {
            val snapshot = hotelbookingRef.whereEqualTo("id", hotelBooking.id).get().await()
            if (snapshot.isEmpty) {
                throw Exception("No hotel booking found with id: ${hotelBooking.id}")
            }
            // Update the first matching document (assuming id is unique)
            val document = snapshot.documents.first()
            hotelbookingRef
                .document(document.id)
                .set(hotelBooking)
                .await()
        } catch (e: Exception) {
            throw e
        }
    }

    suspend fun getAllHotelBookingByUserID(userID: String): List<HotelBooking> = try {
        hotelbookingRef.whereEqualTo("userid", userID).get().await()
            .toObjects(HotelBooking::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }

    suspend fun getHotelBookingByID(id: String): List<HotelBooking> = try {
        hotelbookingRef.whereEqualTo("id", id).get().await()
            .toObjects(HotelBooking::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }
}