package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteTripPackageRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val tripPackageRef = firestore.collection("trip_package")

    suspend fun getAllTripPackages(): List<TripPackage> = try {
        tripPackageRef.get().await().toObjects(TripPackage::class.java)
    } catch (e: Exception) {
        throw Exception()
    }

    suspend fun addTripPackage(pkg: TripPackage): String {
        val docRef = tripPackageRef.document()
        val newPackage = pkg.copy(id = docRef.id)
        docRef.set(newPackage).await()
        return docRef.id
    }

    suspend fun updateTripPackage(pkg: TripPackage) {
        require(pkg.id.isNotEmpty()) { "Package ID cannot be empty" }
        tripPackageRef.document(pkg.id).set(pkg).await()
    }

    suspend fun deleteTripPackage(id: String) {
        require(id.isNotEmpty()) { "Package ID cannot be empty" }
        tripPackageRef.document(id).delete().await()
    }

    suspend fun getTripPackageById(packageId: String): TripPackage? {
        require(packageId.isNotEmpty()) { "Package ID cannot be empty" }
        return tripPackageRef.document(packageId).get().await().toObject(TripPackage::class.java)
    }

}