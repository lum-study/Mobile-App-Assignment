package com.bookblitzpremium.upcomingproject.ui.components

import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomDatePickerDialog(
    onDateChange: (LocalDate) -> Unit,
) {
    DatePickerDialog(
        onDismissRequest = {
            onDateChange(LocalDate.now())
        },
        confirmButton = {
        },
    ) {
        val datePickerState = rememberDatePickerState()
        DatePicker(state = datePickerState)
        LaunchedEffect(datePickerState.selectedDateMillis) {
            datePickerState.selectedDateMillis?.let {
                onDateChange(Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate())
            }
        }
    }
}