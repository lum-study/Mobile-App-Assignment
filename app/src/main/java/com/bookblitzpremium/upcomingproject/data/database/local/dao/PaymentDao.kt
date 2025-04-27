package com.bookblitzpremium.upcomingproject.data.database.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment

@Dao
interface PaymentDao {
    @Upsert
    suspend fun upsertPayment(payment: Payment)

    @Delete
    suspend fun deletePayment(payment: Payment)

    @Query("SELECT * from payment WHERE id = :paymentID")
    suspend fun getPaymentByID(paymentID: String): Payment?
}