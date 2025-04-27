package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import kotlinx.coroutines.flow.Flow

@Dao
interface PaymentDao {
    @Upsert
    suspend fun upsertPayment(payment: Payment)

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("SELECT * from payment WHERE id = :paymentID")
    suspend fun getPaymentByID(paymentID: String): Payment?

    @Query("SELECT * from payment")
    fun selectAll():  Flow<List<Payment>>

    @Insert
    suspend fun insert(payment: Payment)

    @Query("SELECT * FROM payment")
    suspend fun getAllRooms(): List<Payment>


}