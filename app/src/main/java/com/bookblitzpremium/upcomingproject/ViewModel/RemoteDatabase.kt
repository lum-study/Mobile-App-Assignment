package com.bookblitzpremium.upcomingproject.ViewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


data class User2(
    val name: String? = null,
    val course: String? = null,
    val age: Int? = 0
)


class RemoteDatabase :ViewModel() {

    val ref = FirebaseDatabase.getInstance().getReference("teacher")

    fun readUsersFromDatabase(onDataChange: (List<User2>) -> Unit) {
        val database = FirebaseDatabase.getInstance()
        val ref = database.getReference("teacher")

        ref.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = mutableListOf<User2>()
                for (child in snapshot.children) {
                    val user = child.getValue(User2::class.java)
                    user?.let { users.add(it) }
                }
                onDataChange(users)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("Firebase", "Error Code: ${error.code}, Details: ${error.details}, Message: ${error.message}")
            }

        })
    }

}

