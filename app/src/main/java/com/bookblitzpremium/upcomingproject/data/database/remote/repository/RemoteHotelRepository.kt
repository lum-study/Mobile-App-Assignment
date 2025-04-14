package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteHotelRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val hotelRef = firestore.collection("hotel")

    suspend fun getAllHotel(): List<Hotel> = try {
        hotelRef.get().await().toObjects(Hotel::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }

    suspend fun addHotel(hotel: Hotel): String {
        val docRef = hotelRef.document()
        val newHotel = hotel.copy(id = docRef.id)
        docRef.set(newHotel).await()
        return docRef.id
    }

    suspend fun updateHotel(hotel: Hotel) {
        require(hotel.id.isNotEmpty()) { "Hotel ID cannot be empty" }
        hotelRef.document(hotel.id).set(hotel).await()
    }

    suspend fun deleteHotel(id: String) {
        require(id.isNotEmpty()) { "Hotel ID cannot be empty" }
        hotelRef.document(id).delete().await()
    }

    suspend fun getHotelByID(hotelID: String): Hotel? {
        require(hotelID.isNotEmpty()) { "Hotel ID cannot be empty" }
        return hotelRef.document(hotelID).get().await().toObject(Hotel::class.java)
    }

}