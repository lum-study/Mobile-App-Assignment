package com.bookblitzpremium.upcomingproject.ui.screen.search

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.constants.DataSource

import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.model.Country
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import kotlin.math.roundToInt

//@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun FilterScreen(navController: NavController) {
    val countryList: List<Country> = DataSource().loadCountry()
    var hotelTypeSelected by remember { mutableStateOf("") }
    var isNavigating by remember { mutableStateOf(false) }

    LaunchedEffect(isNavigating) {
        if (isNavigating) {
            kotlinx.coroutines.delay(500)
            isNavigating = false
        }
    }

    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            PriceRangeSlider(minValue = 500f, maxValue = 1500f)
            FilterDropDownMenu(
                title = stringResource(R.string.filter_region_title),
                options = countryList.map { it.name })
            FilterDropDownMenu(
                title = stringResource(R.string.filter_state_title),
                options = countryList.map { it.name })

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = stringResource(R.string.filter_type),
                    style = AppTheme.typography.mediumSemiBold
                )
                hotelTypeSelected = filterRadioButton()
            }

            Button(
                onClick = {
                    if (!isNavigating) {
                        isNavigating = true
                        navController.navigate(AppScreen.Result.route)
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = AppTheme.colorScheme.primary,
                    contentColor = AppTheme.colorScheme.surface,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = AppTheme.colorScheme.surface,
                ),
                enabled = !isNavigating
            ) {
                Text(
                    text = stringResource(R.string.apply_button)
                )
            }
        }
    }
}

@Composable
fun PriceRangeSlider(minValue: Float, maxValue: Float) {
    var priceValue by remember { mutableStateOf(minValue..maxValue) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.filter_price_title),
            style = AppTheme.typography.mediumSemiBold
        )
        RangeSlider(
            value = priceValue,
            steps = 30,
            onValueChange = { range ->
                priceValue =
                    range.start.roundToInt().toFloat()..range.endInclusive.roundToInt()
                        .toFloat()
            },
            valueRange = 500f..3000f,
            onValueChangeFinished = {
                Log.d("MyTag", "This is a debug message")
            },
            colors = SliderColors(
                thumbColor = Color.Blue,
                activeTrackColor = Color.Gray,
                activeTickColor = Color.Transparent,
                inactiveTrackColor = Color.LightGray,
                inactiveTickColor = Color.Transparent,
                disabledThumbColor = Color.Black,
                disabledActiveTrackColor = Color.Black,
                disabledActiveTickColor = Color.Black,
                disabledInactiveTrackColor = Color.Black,
                disabledInactiveTickColor = Color.Black,
            ),
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$ ${priceValue.start.toInt()}",
                style = AppTheme.typography.smallRegular,
            )
            Text(
                text = "$ ${priceValue.endInclusive.toInt()}",
                style = AppTheme.typography.smallRegular,
            )
        }
    }
}

@Composable
fun FilterDropDownMenu(title: String, options: List<String>) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(options.first()) }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = title,
            style = AppTheme.typography.mediumSemiBold
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            Button(
                onClick = { expanded = true },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(selectedOption)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(.9f),
                offset = DpOffset(x = 0.dp, y = -2.dp)
            ) {

                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            selectedOption = option
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun filterRadioButton(): String {
    val options = listOf("All", "Hotel", "Package")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(options[0]) }

    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEach { option ->
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = option,
                    style = AppTheme.typography.smallRegular
                )
                RadioButton(
                    selected = (option == selectedOption),
                    onClick = {
                        onOptionSelected(option)
                    }
                )
            }
        }
    }
    return selectedOption
}