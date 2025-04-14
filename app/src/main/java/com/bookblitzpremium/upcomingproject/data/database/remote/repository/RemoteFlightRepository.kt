package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.Flight
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteFlightRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val flightRef = firestore.collection("flight")

    suspend fun getAllFlight(): List<Flight> = try {
        flightRef.get().await().toObjects(Flight::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }

    suspend fun addFlight(flight: Flight): String {
        val docRef = flightRef.document()
        val newFlight = flight.copy(id = docRef.id)
        docRef.set(newFlight).await()
        return docRef.id
    }

    suspend fun updateFlight(flight: Flight) {
        require(flight.id.isNotEmpty()) { "Flight ID cannot be empty" }
        flightRef.document(flight.id).set(flight).await()
    }

    suspend fun deleteFlight(id: String) {
        require(id.isNotEmpty()) { "Flight ID cannot be empty" }
        flightRef.document(id).delete().await()
    }

    suspend fun getFlightById(flightID: String): Flight? {
        require(flightID.isNotEmpty()) { "Flight ID cannot be empty" }
        return flightRef.document(flightID).get().await().toObject(Flight::class.java)
    }

}