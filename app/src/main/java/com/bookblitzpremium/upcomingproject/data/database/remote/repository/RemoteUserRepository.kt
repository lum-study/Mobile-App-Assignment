package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteUserRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val userRef = firestore.collection("user")

    suspend fun getUserByID(userID: String): User? {
        require(userID.isNotEmpty()) { "User ID cannot be empty" }
        return userRef.document(userID).get().await().toObject(User::class.java)
    }

    suspend fun addUser(user: User): String {
        val docRef = userRef.document()
        val newUser = user.copy(id = docRef.id)
        docRef.set(newUser).await()
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

}