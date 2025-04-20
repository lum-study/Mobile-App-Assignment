package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.TPBooking
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteTPBookingRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val tpBookingRef = firestore.collection("tp_booking")

    suspend fun getAllTripPackageBookingByUserID(userID: String): List<TPBooking> = try {
        tpBookingRef.whereEqualTo("userID", userID).get().await().toObjects(TPBooking::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }

    suspend fun addTripPackageBooking(tpBooking: TPBooking): String {
        val docRef = tpBookingRef.document()
        val newTripPackageBooking = tpBooking.copy(id = docRef.id)
        docRef.set(newTripPackageBooking).await()
        return docRef.id
    }

    suspend fun deleteTripPackageBooking(id: String) {
        require(id.isNotEmpty()) { "Trip Package Booking ID cannot be empty" }
        tpBookingRef.document(id).delete().await()
    }

    suspend fun updateTripPackageBooking(tpBooking: TPBooking) {
        require(tpBooking.id.isNotEmpty()) { "Trip Package Booking ID cannot be empty" }
        tpBookingRef.document(tpBooking.id).set(tpBooking).await()
    }

}