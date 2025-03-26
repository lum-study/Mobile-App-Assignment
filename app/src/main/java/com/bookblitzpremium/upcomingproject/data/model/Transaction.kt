package com.bookblitzpremium.upcomingproject.model

import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod

data class Transaction(
    val transactionID: String,
    val paymentMethod: PaymentMethod,
    val cardNumber: String,
    val currency: String,
    val reference: String,
    val transactionDate: String,
)
