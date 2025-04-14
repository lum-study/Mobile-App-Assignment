package com.bookblitzpremium.upcomingproject.data.database.remote.repository

import com.bookblitzpremium.upcomingproject.data.database.local.entity.Schedule
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteScheduleRepository @Inject constructor(private val firestore: FirebaseFirestore) {
    private val scheduleRef = firestore.collection("schedule")

    suspend fun getAllSchedule(): List<Schedule> = try {
        scheduleRef.get().await().toObjects(Schedule::class.java)
    } catch (e: Exception) {
        throw Exception(e)
    }

    suspend fun addSchedule(schedule: Schedule): String {
        val docRef = scheduleRef.document()
        val newSchedule = schedule.copy(id = docRef.id)
        docRef.set(newSchedule).await()
        return docRef.id
    }

    suspend fun updateSchedule(schedule: Schedule) {
        require(schedule.id.isNotEmpty()) { "Schedule ID cannot be empty" }
        scheduleRef.document(schedule.id).set(schedule).await()
    }

    suspend fun deleteSchedule(id: String) {
        require(id.isNotEmpty()) { "Schedule ID cannot be empty" }
        scheduleRef.document(id).delete().await()
    }

    suspend fun getScheduleByID(scheduleID: String): Schedule? {
        require(scheduleID.isNotEmpty()) { "Schedule ID cannot be empty" }
        return scheduleRef.document(scheduleID).get().await().toObject(Schedule::class.java)
    }

}