package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.text.get

class RemoteUserRepository @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    private val userRef = firestore.collection("user")

    suspend fun getUserByID(userID: String): User? {
        require(userID.isNotEmpty()) { "User ID cannot be empty" }
        return userRef.document(userID).get().await().toObject(User::class.java)
    }

    suspend fun addUser(user: User): String {
        val docRef = userRef.document(user.id)
        docRef.set(user).await()
        return docRef.id
    }

    suspend fun deleteUser(id: String) {
        require(id.isNotEmpty()) { "User ID cannot be empty" }
        userRef.document(id).delete().await()
    }

    suspend fun updateUser(user: User) {
        require(user.id.isNotEmpty()) { "User ID cannot be empty" }
        userRef.document(user.id).set(user).await()
    }

    suspend fun checkEmails(email: String): String {
        try {
            val querySnapshot = userRef.whereEqualTo("email", email).get().await()
            if (!querySnapshot.isEmpty) {
                return querySnapshot.documents.first().id // Return document ID if email exists
            }
            return "" // Email not found
        } catch (e: Exception) {
            throw Exception("Failed to check email in database: ${e.localizedMessage}")
        }
    }

    suspend fun updateUserGender(id: String, gender: String): User? {
        require(id.isNotEmpty()) { "ID cannot be empty" }
        require(gender in listOf("Male", "Female")) { "Gender must be Male or Female" }
        try {
            // Retrieve the document by ID
            val documentSnapshot = userRef.document(id).get().await()
            if (documentSnapshot.exists()) {
                // Update the gender field
                userRef.document(id).update("gender", gender).await()
                // Retrieve the updated document
                val updatedSnapshot = userRef.document(id).get().await()
                return updatedSnapshot.toObject(User::class.java)
            } else {
                return null
            }
        } catch (e: Exception) {
            throw Exception("Failed to update user gender: ${e.localizedMessage}")
        }
    }

    suspend fun validateEmail(id: String, user: User): String {
        // Query Firestore to check if the email is already in use
        val querySnapshot = userRef
            .whereEqualTo("email", user.email)
            .get()
            .await()

        // If email is already in use, throw an exception
        if (!querySnapshot.isEmpty) {
            throw IllegalStateException("Email is already in use")
        }

        // Use the provided ID for the document
        val docRef = userRef.document(id)
        val userWithId = user.copy(id = id)

        // Save the user to Firestore
        docRef.set(userWithId).await()
        return id // Return the provided ID
    }
}