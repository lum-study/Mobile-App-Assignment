package com.bookblitzpremium.upcomingproject.ui.screen.search

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
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
import com.bookblitzpremium.upcomingproject.data.businessviewmodel.FilterViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.RecentSearch
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRecentSearchViewModel
import com.bookblitzpremium.upcomingproject.ui.components.CustomDatePickerDialog
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@Composable
fun FilterScreen(
    navController: NavController,
    keyword: String,
    minPrice: String,
    maxPrice: String,
    isMobile: Boolean = true,
    onApplyClick: () -> Unit = {},
    filterViewModel: FilterViewModel = hiltViewModel(),
) {
    val context = LocalContext.current
    val filterState by filterViewModel.filterState.collectAsState()
    val localRecentSearchViewModel: LocalRecentSearchViewModel = hiltViewModel()
    val selectedOption = filterState.selectedOption
    val hotelPriceRange = filterState.hotelStartPrice..filterState.hotelEndPrice
    val tpPriceRange = filterState.tpStartPrice..filterState.tpEndPrice
    //hotel
    val selectedRating = filterState.selectedRating
    val selectedFeature = filterState.selectedFeature
    //trip package
    val selectedDeparture = filterState.selectedDeparture
    val selectedArrival = filterState.selectedArrival
    val startDate = filterState.selectedStartDate
    val endDate = filterState.selectedEndDate
    val showStartDatePicker = filterState.showStartDatePicker
    val showEndDatePicker = filterState.showEndDatePicker
    val isUpdated = filterState.isUpdated
    val dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy")

    if (isMobile && !isUpdated) {
        filterViewModel.updateHotelStartPrice(minPrice.toFloat())
        filterViewModel.updateHotelEndPrice(maxPrice.toFloat())
        filterViewModel.updateState(true)
    }
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            //Trip Package Or Hotel
            FilterType(
                selectedOption = selectedOption,
                onOptionChange = {
                    filterViewModel.updateOption(it)
                    if (it == BookingType.Hotel) {
                        filterViewModel.updateHotelStartPrice(minPrice.toFloat())
                        filterViewModel.updateHotelEndPrice(maxPrice.toFloat())
                    } else {
                        filterViewModel.updateTPStartPrice(600f)
                        filterViewModel.updateTPEndPrice(2500f)
                    }
                }
            )
            HorizontalDivider()
            //Price Range
            PriceRangeSlider(
                selectedPriceRange = if (selectedOption == BookingType.Hotel) hotelPriceRange else tpPriceRange,
                onValueChange = {
                    if (selectedOption == BookingType.Hotel) {
                        filterViewModel.updateHotelStartPrice(it.start.roundToInt().toFloat())
                        filterViewModel.updateHotelEndPrice(it.endInclusive.roundToInt().toFloat())
                    } else {
                        filterViewModel.updateTPStartPrice(it.start.roundToInt().toFloat())
                        filterViewModel.updateTPEndPrice(it.endInclusive.roundToInt().toFloat())
                    }
                },
                valueRange = if (selectedOption == BookingType.Hotel) minPrice.toFloat()..maxPrice.toFloat() else 300f..2800f
            )
            HorizontalDivider()

            if (selectedOption == BookingType.Hotel) {
                //Rating
                RatingOption(
                    selectedRating = selectedRating,
                    onOptionChange = { filterViewModel.updateRating(it) }
                )
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
                        onOptionChange = { filterViewModel.updateDeparture(it) },
                        title = stringResource(R.string.filter_departure),
                    )
                    FilterDropDownMenu(
                        selectedStation = selectedArrival,
                        onOptionChange = { filterViewModel.updateArrival(it) },
                        title = stringResource(R.string.filter_arrival),
                    )
                }
                HorizontalDivider()

                if (isMobile) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(32.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(R.string.filter_start_date),
                                style = AppTheme.typography.mediumSemiBold
                            )
                            OutlinedButton(
                                onClick = { filterViewModel.updateStartDatePicker(true) },
                            ) {
                                Text(
                                    text = startDate.format(dateFormatter),
                                    style = AppTheme.typography.mediumNormal
                                )
                            }
                        }
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = stringResource(R.string.filter_end_date),
                                style = AppTheme.typography.mediumSemiBold
                            )
                            OutlinedButton(
                                onClick = { filterViewModel.updateEndDatePicker(true) }
                            ) {
                                Text(
                                    text = endDate.format(dateFormatter),
                                    style = AppTheme.typography.mediumNormal
                                )
                            }
                        }
                    }
                } else {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.filter_start_date),
                                style = AppTheme.typography.mediumSemiBold,
                                modifier = Modifier.weight(.75f),
                            )
                            OutlinedButton(
                                onClick = { filterViewModel.updateStartDatePicker(true) },
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(4.dp)
                            ) {
                                Text(
                                    text = startDate.format(dateFormatter),
                                    style = AppTheme.typography.mediumNormal
                                )
                            }
                        }
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.filter_end_date),
                                style = AppTheme.typography.mediumSemiBold,
                                modifier = Modifier.weight(.75f),
                            )
                            OutlinedButton(
                                onClick = { filterViewModel.updateEndDatePicker(true) },
                                modifier = Modifier.weight(1f),
                                contentPadding = PaddingValues(4.dp)
                            ) {
                                Text(
                                    text = endDate.format(dateFormatter),
                                    style = AppTheme.typography.mediumNormal
                                )
                            }
                        }
                    }
                }
                if (showStartDatePicker) {
                    CustomDatePickerDialog(
                        onDateChange = {
                            filterViewModel.updateStartDate(it)
                            filterViewModel.updateStartDatePicker(false)
                        },
                    )
                }
                if (showEndDatePicker) {
                    CustomDatePickerDialog(
                        onDateChange = {
                            filterViewModel.updateEndDate(if (it.isAfter(startDate)) it else endDate)
                            filterViewModel.updateEndDatePicker(false)

                            if (it.isBefore(startDate))
                                Toast.makeText(
                                    context,
                                    "End date must be after the start date",
                                    Toast.LENGTH_SHORT
                                ).show()
                        },
                    )
                }
            }
            HorizontalDivider()
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = {
                    if (selectedOption == BookingType.Hotel) {
                        val recentSearch = RecentSearch(
                            keyword = keyword,
                            option = selectedOption.title,
                            startPrice = hotelPriceRange.start.toDouble(),
                            endPrice = hotelPriceRange.endInclusive.toDouble(),
                            rating = selectedRating.rate,
                            feature = selectedFeature.joinToString { feature -> feature.title })
                        localRecentSearchViewModel.addOrUpdateRecentSearch(recentSearch)
                    } else {
                        val recentSearch = RecentSearch(
                            keyword = keyword,
                            option = selectedOption.title,
                            startPrice = tpPriceRange.start.toDouble(),
                            endPrice = tpPriceRange.endInclusive.toDouble(),
                            departureStation = selectedDeparture.title,
                            arrivalStation = selectedArrival.title,
                            startDate = startDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                            endDate = endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")),
                        )
                        localRecentSearchViewModel.addOrUpdateRecentSearch(recentSearch)
                    }
                    if (isMobile) {
                        navController.navigate(AppScreen.Result.route) {
                            popUpTo(
                                AppScreen.Search.route
                            )
                        }
                    } else {
                        onApplyClick()
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
    valueRange: ClosedFloatingPointRange<Float>,
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
            steps = (valueRange.endInclusive - valueRange.start).toInt(),
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
                text = stringResource(R.string.price, selectedPriceRange.start),
                style = AppTheme.typography.smallRegular,
            )
            Text(
                text = stringResource(R.string.price, selectedPriceRange.endInclusive),
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
    modifier: Modifier = Modifier,
) {
    var expanded by rememberSaveable { mutableStateOf(false) }
    val options = FlightStation.entries

    Column(
        modifier = modifier,
    ) {
        Text(
            text = title,
            style = AppTheme.typography.mediumSemiBold
        )
        Box(modifier = Modifier.fillMaxWidth()) {
            OutlinedButton(
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
    onOptionChange: (BookingType) -> Unit,
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