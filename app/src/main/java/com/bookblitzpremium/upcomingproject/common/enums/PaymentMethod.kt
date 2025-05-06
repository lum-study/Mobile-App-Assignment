package com.bookblitzpremium.upcomingproject.common.enums

import com.bookblitzpremium.upcomingproject.R

enum class PaymentMethod(val title: String, val icon: Int) {
    DebitCard(title = "Debit Card", icon = R.drawable.mastercard),
    CreditCard(title = "Credit Card", icon = R.drawable.mastercard),
    EWallet(title = "Touch 'n Go eWallet", icon = R.drawable.tng_icon),
    NotSelected(title = "NotSelected", icon = 0),
}