package com.BookBlitzPremium.Upcomingproject.Data

import com.BookBlitzPremium.Upcomingproject.Model.Country

class DataSource {
    fun loadCountry(): List<Country> {
       return listOf(
           Country(name = "Malaysia", state = loadMalaysiaRegion())
       )
    }

    private fun loadMalaysiaRegion(): List<String> {
        return listOf(
            "Kuala Lumpur",
            "Perlis",
            "Kedah",
            "Penang",
            "Perak",
            "Selangor",
            "Putrajaya",
            "Negeri Sembilan",
            "Malacca",
            "Johor",
            "Kelantan",
            "Terengganu",
            "Pahang",
            "Sabah",
            "Sarawak",
            "Labuan",
        )
    }
}