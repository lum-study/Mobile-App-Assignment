package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteRatingRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val ratingRef = firestore.collection("rating")

    suspend fun getAllRating(): List<Rating> = try {
        ratingRef.get().await().toObjects(Rating::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }

    suspend fun addRating(rating: Rating): String {
        val docRef = ratingRef.document()
        val newRating = rating.copy(id = docRef.id)
        docRef.set(newRating).await()
        return docRef.id
    }

    suspend fun updateRating(rating: Rating) {
        require(rating.id.isNotEmpty()) { "Rating ID cannot be empty" }
        ratingRef.document(rating.id).set(rating).await()
    }

    suspend fun deleteRating(id: String) {
        require(id.isNotEmpty()) { "Rating ID cannot be empty" }
        ratingRef.document(id).delete().await()
    }

    suspend fun getRatingsByHotelID(hotelId: String): List<Rating> = try {
        ratingRef.whereEqualTo("hotelId", hotelId).get().await().toObjects(Rating::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }


    suspend fun getRatingByHotelID(hotelID: String): List<Rating> {
        require(hotelID.isNotEmpty()) { "Hotel ID cannot be empty" }
        return ratingRef.whereEqualTo("hotelID", hotelID).get().await()
            .toObjects(Rating::class.java)
    }
}