package com.bookblitzpremium.upcomingproject.common.enums

import java.io.Serializable

enum class Rating(val rate: Double): Serializable {
    Rate1(rate = 1.0),
    Rate2(rate = 2.0),
    Rate3(rate = 3.0),
    Rate4(rate = 4.0),
    Rate5(rate = 5.0),
}