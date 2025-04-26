package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.google.firebase.firestore.FirebaseFirestore
import jakarta.inject.Inject
import kotlinx.coroutines.tasks.await

class RemoteUserRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val userRef = firestore.collection("user")

    suspend fun getUserByID(userID: String): User? {
        require(userID.isNotEmpty()) { "User ID cannot be empty" }
        return userRef.document(userID).get().await().toObject(User::class.java)
    }

    suspend fun addUser(user: User): String {
        val docRef = userRef.document(user.uid)
        docRef.set(user).await()
        return docRef.id
    }

    suspend fun selectAllUsers(): List<User> {
        return userRef.get().await().documents.mapNotNull { snapshot ->
            snapshot.toObject(User::class.java)
        }
    }


    suspend fun deleteUser(id: String) {
        require(id.isNotEmpty()) { "User ID cannot be empty" }
        userRef.document(id).delete().await()
    }

    suspend fun updateUser(user: User) {
        require(user.uid.isNotEmpty()) { "User ID cannot be empty" }
        userRef.document(user.uid).set(user).await()
    }
}
