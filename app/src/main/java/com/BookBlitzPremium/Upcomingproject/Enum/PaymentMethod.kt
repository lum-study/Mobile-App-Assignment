package com.BookBlitzPremium.Upcomingproject.Enum

import androidx.compose.ui.res.stringResource
import com.BookBlitzPremium.Upcomingproject.R

enum class PaymentMethod(val title: String) {
    DebitCard(title = "Debit Card"),
    CreditCard(title = "Credit Card"),
    EWallet(title = "eWallet"),
}