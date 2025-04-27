package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemotePaymentRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val paymentRef = firestore.collection("payment")

    suspend fun getAllPaymentByUserID(userID: String): List<Payment> = try {
        paymentRef.whereEqualTo("userID", userID).get().await().toObjects(Payment::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }

    suspend fun addPayment(payment: Payment): String {
        val docRef = paymentRef.document()
        val newPayment = payment.copy(id = docRef.id)
        docRef.set(newPayment).await()
        return docRef.id
    }

    suspend fun getAllPayment(): List<Payment> = try {
        paymentRef.get().await().toObjects(Payment::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }


    suspend fun deletePayment(id: String) {
        require(id.isNotEmpty()) { "Payment ID cannot be empty" }
        paymentRef.document(id).delete().await()
    }

    suspend fun updatePayment(payment: Payment) {
        require(payment.id.isNotEmpty()) { "Payment ID cannot be empty" }
        paymentRef.document(payment.id).set(payment).await()
    }

}