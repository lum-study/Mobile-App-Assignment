package com.bookblitzpremium.upcomingproject.ui.screen.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingType
import com.bookblitzpremium.upcomingproject.common.enums.Feature
import com.bookblitzpremium.upcomingproject.common.enums.FlightStation
import com.bookblitzpremium.upcomingproject.common.enums.Rating
import com.bookblitzpremium.upcomingproject.data.database.local.entity.RecentSearch
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRecentSearchViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.booking.ShowDate
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import kotlin.math.roundToInt

@Composable
fun FilterScreen(navController: NavController, keyword: String) {
    val localRecentSearchViewModel: LocalRecentSearchViewModel = hiltViewModel()

    var selectedOption by remember { mutableStateOf(BookingType.Hotel) }
    var selectedPriceRange by remember { mutableStateOf(0f..1500f) }

    //hotel
    var selectedRating by remember { mutableStateOf(Rating.Rate1) }
    val selectedFeature = remember { mutableStateListOf<Feature>() }

    //trip package
    var selectedDeparture by remember { mutableStateOf(FlightStation.KualaLumpur) }
    var selectedArrival by remember { mutableStateOf(FlightStation.Sabah) }

    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 8.dp, start = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            //Trip Package Or Hotel
            FilterType(
                selectedOption = selectedOption,
                onOptionChange = { selectedOption = it }
            )
            HorizontalDivider()
            //Price Range
            PriceRangeSlider(selectedPriceRange = selectedPriceRange, onValueChange = {
                selectedPriceRange =
                    it.start.roundToInt().toFloat()..it.endInclusive.roundToInt().toFloat()
            })
            HorizontalDivider()

            if (selectedOption == BookingType.Hotel) {
                //Rating
                RatingOption(
                    selectedRating = selectedRating,
                    onOptionChange = { selectedRating = it })
                HorizontalDivider()
                //Feature
                FeatureOption(selectedFeature = selectedFeature)
            } else if (
                selectedOption == BookingType.TripPackage
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilterDropDownMenu(
                        selectedStation = selectedDeparture,
                        onOptionChange = { selectedDeparture = it },
                        title = stringResource(R.string.filter_departure),
                    )
                    FilterDropDownMenu(
                        selectedStation = selectedArrival,
                        onOptionChange = { selectedArrival = it },
                        title = stringResource(R.string.filter_arrival),
                    )
                }
                HorizontalDivider()
                ShowDate(date = "2022-01-02", date2 = "2022-01-05")
            }
            Button(
                onClick = {
                    if (selectedOption == BookingType.Hotel) {
                        val recentSearch = RecentSearch(
                            keyword = keyword,
                            option = selectedOption.title,
                            startPrice = selectedPriceRange.start.toDouble(),
                            endPrice = selectedPriceRange.endInclusive.toDouble(),
                            rating = selectedRating.rate,
                            feature = selectedFeature.joinToString { feature -> feature.title })
                        localRecentSearchViewModel.addOrUpdateRecentSearch(recentSearch)
                    } else {
                        val recentSearch = RecentSearch(
                            keyword = keyword,
                            option = selectedOption.title,
                            startPrice = selectedPriceRange.start.toDouble(),
                            endPrice = selectedPriceRange.endInclusive.toDouble(),
                            departureStation = selectedDeparture.title,
                            arrivalStation = selectedArrival.title
                        )
                        localRecentSearchViewModel.addOrUpdateRecentSearch(recentSearch)
                    }
                    navController.navigate(AppScreen.Result.route) {
                        popUpTo(
                            AppScreen.Search.route
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonColors(
                    containerColor = AppTheme.colorScheme.primary,
                    contentColor = AppTheme.colorScheme.surface,
                    disabledContainerColor = Color.Gray,
                    disabledContentColor = AppTheme.colorScheme.surface,
                ),
            ) {
                Text(
                    text = stringResource(R.string.apply_button)
                )
            }
        }
    }
}

@Composable
fun PriceRangeSlider(
    selectedPriceRange: ClosedFloatingPointRange<Float>,
    onValueChange: (ClosedFloatingPointRange<Float>) -> Unit,
    valueRange: ClosedFloatingPointRange<Float> = 0f..3000f
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.filter_price_title),
            style = AppTheme.typography.mediumSemiBold
        )
        RangeSlider(
            value = selectedPriceRange,
            steps = 30,
            onValueChange = { range ->
                onValueChange(range)
            },
            valueRange = valueRange,
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
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .height(24.dp)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "$ ${selectedPriceRange.start.toInt()}",
                style = AppTheme.typography.smallRegular,
            )
            Text(
                text = "$ ${selectedPriceRange.endInclusive.toInt()}",
                style = AppTheme.typography.smallRegular,
            )
        }
    }
}

@Composable
fun FilterDropDownMenu(
    selectedStation: FlightStation,
    onOptionChange: (FlightStation) -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    val options = FlightStation.entries

    Column(
        modifier = modifier,
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
                Text(selectedStation.title)
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(.9f),
                offset = DpOffset(x = 0.dp, y = -2.dp)
            ) {

                options.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option.title) },
                        onClick = {
                            onOptionChange(option)
                            expanded = false
                        },
                    )
                }
            }
        }
    }
}

@Composable
fun FilterType(
    selectedOption: BookingType,
    onOptionChange: (BookingType) -> Unit
) {
    val options = listOf(BookingType.Hotel, BookingType.TripPackage)
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.filter_type),
            style = AppTheme.typography.mediumSemiBold
        )
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            options.forEach { option ->
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onOptionChange(option) }
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    Text(
                        text = option.title,
                        style = AppTheme.typography.smallRegular,
                        modifier = Modifier.padding(start = 12.dp)
                    )
                    RadioButton(
                        selected = (option == selectedOption),
                        onClick = {
                            onOptionChange(option)
                        },
                        modifier = Modifier.padding(end = 8.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun RatingOption(selectedRating: Rating, onOptionChange: (Rating) -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.filter_rating_title),
            style = AppTheme.typography.mediumSemiBold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            val rating = Rating.entries
            rating.forEach { index ->
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = stringResource(
                        R.string.filter_rating,
                        index.rate
                    ),
                    tint = if (index.rate <= selectedRating.rate) Color(0xFFFFC71E) else Color.Gray,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable(
                            onClick = { onOptionChange(index) },
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                        )
                )
            }
        }
    }
}

@Composable
fun FeatureOption(selectedFeature: SnapshotStateList<Feature>) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val options = Feature.entries
        Text(
            text = stringResource(R.string.filter_feature),
            style = AppTheme.typography.mediumSemiBold
        )
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier
                .height(250.dp)
                .padding(horizontal = 12.dp)
        ) {
            items(options.size) { index ->
                val feature = options[index]
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clickable {
                            if (feature in selectedFeature) {
                                selectedFeature.remove(feature)
                            } else if (selectedFeature.size < 2) {
                                selectedFeature.add(feature)
                            }
                        }
                        .clip(RoundedCornerShape(24.dp))
                ) {
                    Text(
                        text = feature.title,
                        style = AppTheme.typography.smallRegular,
                    )
                    Checkbox(
                        checked = feature in selectedFeature,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                if (selectedFeature.size < 2) {
                                    selectedFeature.add(feature)
                                }
                            } else {
                                selectedFeature.remove(feature)
                            }
                        },
                    )
                }
            }
        }
    }
}