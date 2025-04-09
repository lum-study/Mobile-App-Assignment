package com.bookblitzpremium.upcomingproject.ui.screen.travel

import android.graphics.Color.rgb
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.model.Hotel
import com.bookblitzpremium.upcomingproject.model.TripPackageTabs
import com.bookblitzpremium.upcomingproject.ui.screen.home.HotelCard
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.DynamicHotelDetails
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.MobileLayout

import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.FlightScreen
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.ScheduleScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme


@Preview(showBackground = true, widthDp = 1440, heightDp = 900)
@Composable
fun TravelHeaderTable() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    // List of tab items
    val tabs: List<TripPackageTabs> = listOf(
        TripPackageTabs("Schedule", Icons.Outlined.Task, {ScheduleScreen(navController = rememberNavController())}),
        TripPackageTabs("Flight", Icons.Outlined.Flight, { FlightScreen(navController = rememberNavController()) }),
        TripPackageTabs("Hotel", Icons.Outlined.HomeWork, { MobileLayout(2, 300.dp, 500.dp) }),
    )

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .background(Color.White),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hotel_images),
                contentDescription = stringResource(R.string.trip_package_image),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f),
            )
            Text(
                text = "Description",
                style = AppTheme.typography.largeSemiBold,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp),
            )

        }


        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .background(Color.White, RoundedCornerShape(32.dp))
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex,
                containerColor = Color(rgb(233, 233, 233)), // Background color
                contentColor = Color.Black, // Text/icon color
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                tabs.forEachIndexed { index, tab ->
                    Tab(
                        selected = selectedTabIndex == index,
                        onClick = { selectedTabIndex = index },
                        text = {
                            Text(
                                text = tab.title,
                                style = AppTheme.typography.mediumSemiBold,
                                color = Color.Black
                            )
                        },
                        icon = {
                            Icon(
                                imageVector = tab.icon,
                                contentDescription = tab.title,
                            )
                        },
                    )
                }
            }
            Box(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp).clip(RoundedCornerShape(32.dp)),
            ){
                tabs[selectedTabIndex].screen()
            }
        }
    }
}


@Composable
fun TabContent(text: String) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        Text(
            text = text, fontSize = 20.sp, color = Color.Black
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InlineDateRangePicker(onDateRangeSelected: (Pair<Long?, Long?>) -> Unit) {
    val dateRangePickerState = rememberDateRangePickerState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Center like a dialog
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Select date range", style = MaterialTheme.typography.titleMedium
                )

                DateRangePicker(
                    state = dateRangePickerState,
                    showModeToggle = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onDateRangeSelected(
                            Pair(
                                dateRangePickerState.selectedStartDateMillis,
                                dateRangePickerState.selectedEndDateMillis
                            )
                        )
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}