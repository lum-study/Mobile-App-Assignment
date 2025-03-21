package com.BookBlitzPremium.Upcomingproject.Model

import com.BookBlitzPremium.Upcomingproject.Enum.PaymentMethod

data class Transaction(
    val transactionID: String,
    val paymentMethod: PaymentMethod,
    val cardNumber: String,
    val currency: String,
    val reference: String,
    val transactionDate: String,
)
