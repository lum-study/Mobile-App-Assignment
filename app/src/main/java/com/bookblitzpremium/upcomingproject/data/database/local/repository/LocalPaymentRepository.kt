package com.bookblitzpremium.upcomingproject.data.database.local.repository

import com.bookblitzpremium.upcomingproject.data.database.local.dao.PaymentDao
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Payment
import javax.inject.Inject

class LocalPaymentRepository @Inject constructor(private val paymentDao: PaymentDao) {
    suspend fun getPaymentByID(paymentID: String) = paymentDao.getPaymentByID(paymentID)
    suspend fun deletePayment(payment: Payment) = paymentDao.deletePayment(payment)
    suspend fun addOrUpdatePayment(payment: Payment) = paymentDao.upsertPayment(payment)
}