package com.bookblitzpremium.upcomingproject.ui.screen.booking

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ViewModelDate {

    private val ui_Start_Date = MutableStateFlow("")
    val uiStartDate : StateFlow<String> = ui_Start_Date.asStateFlow()

    private val ui_End_Date = MutableStateFlow("")
    val uiEndDate : StateFlow<String> = ui_End_Date.asStateFlow()

    fun passStartDate(newDate: String) {
        ui_Start_Date.value = newDate
    }

    fun passEndDate(newDate: String) {
        ui_End_Date.value = newDate
    }


    //send the notication when the order history is deleted

    //catch the user state like the amount of the previous order then asking for delete or not

    //when the hotel is full return the dialog to user it is full

    //send the notication when the booking is done

    // send the notication when the booking is deleted


    //send the notication when the booking is created


    //Check-In/Check-Out Reminder – Notify users before their booking starts/ends.

    //Smart Price Alerts – Notify users when prices drop.


}