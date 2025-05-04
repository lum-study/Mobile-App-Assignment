package com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalScheduleViewModel
import com.bookblitzpremium.upcomingproject.ui.components.SkeletonLoader
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun SchedulePreview() {
    ScheduleScreen()
}

@Composable
fun ScheduleScreen(tripPackageID: String = "", startDate: String = "", isTablet: Boolean = false) {
    val localScheduleViewModel: LocalScheduleViewModel = hiltViewModel()
    val scheduleList = remember(tripPackageID) {
        localScheduleViewModel.getScheduleByTripPackage(tripPackageID)
    }.collectAsLazyPagingItems()

    val groupedSchedules by remember(scheduleList.itemSnapshotList.items) {
        mutableStateOf(
            scheduleList.itemSnapshotList.items
                .groupBy { it.day }
                .toSortedMap()
        )
    }

    val tabTitles = groupedSchedules.keys.toList()
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp, start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                if (tabTitles.isNotEmpty()) {
                    if (isTablet) {
                        TabRow(
                            selectedTabIndex = selectedTabIndex,
                        ) {
                            tabTitles.forEachIndexed { index, day ->
                                Tab(
                                    selected = selectedTabIndex == index,
                                    onClick = { selectedTabIndex = index },
                                    text = {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            Text(
                                                text = stringResource(R.string.day_count, day),
                                                style = AppTheme.typography.mediumSemiBold,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = LocalDate.parse(startDate)
                                                    .plusDays(index.toLong()).format(
                                                        DateTimeFormatter.ofPattern(
                                                            "d MMM yyyy",
                                                            Locale.ENGLISH
                                                        )
                                                    ),
                                                style = AppTheme.typography.smallRegular,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    } else {
                        ScrollableTabRow(
                            selectedTabIndex = selectedTabIndex,
                        ) {
                            tabTitles.forEachIndexed { index, day ->
                                Tab(
                                    selected = selectedTabIndex == index,
                                    onClick = { selectedTabIndex = index },
                                    text = {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                        ) {
                                            Text(
                                                text = stringResource(R.string.day_count, day),
                                                style = AppTheme.typography.mediumSemiBold,
                                                color = Color.Black
                                            )
                                            Text(
                                                text = LocalDate.parse(startDate)
                                                    .plusDays(index.toLong()).format(
                                                        DateTimeFormatter.ofPattern(
                                                            "d MMM yyyy",
                                                            Locale.ENGLISH
                                                        )
                                                    ),
                                                style = AppTheme.typography.smallRegular,
                                                color = Color.Gray
                                            )
                                        }
                                    }
                                )
                            }
                        }
                    }

                    val currentDay = tabTitles[selectedTabIndex]
                    val activitiesForSelectedDay =
                        groupedSchedules[currentDay]?.sortedBy {
                            LocalTime.parse(
                                it.time,
                                DateTimeFormatter.ofPattern("H:mm")
                            )
                        }
                    if (activitiesForSelectedDay != null) {
                        if (activitiesForSelectedDay.isNotEmpty()) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                modifier = Modifier.padding(bottom = 70.dp)
                            ) {
                                items(activitiesForSelectedDay ?: emptyList()) { schedule ->
                                    TimeAndDestinationRow(
                                        time = schedule.time,
                                        activity = schedule.activity
                                    )
                                }
                            }
                        } else {
                            Box(
                                contentAlignment = Alignment.Center,
                                modifier = Modifier
                                    .fillMaxHeight(.5f)
                                    .fillMaxWidth()
                            ) {
                                Text(
                                    text = stringResource(R.string.no_record),
                                    style = AppTheme.typography.largeSemiBold,
                                    color = Color.DarkGray,
                                )
                            }
                        }
                    }
                } else {
                    SkeletonLoader(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 12.dp, vertical = 24.dp)
                            .clip(
                                RoundedCornerShape(20.dp)
                            )
                    )
                }
            }
        }
    }
}

@Composable
fun TimeAndDestinationRow(
    time: String,
    activity: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .padding(start = 8.dp, end = 4.dp),
    ) {
        Card(
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier
                .padding(4.dp)
                .fillMaxHeight(),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 12.dp, vertical = 8.dp)
                    .width(70.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.AccessTime,
                    contentDescription = stringResource(R.string.clock_icon),
                    tint = Color.Gray
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = time,
                    style = AppTheme.typography.smallSemiBold,
                    color = Color.DarkGray,
                    letterSpacing = 1.sp
                )
            }
        }
        Card(
            shape = RoundedCornerShape(50),
            border = BorderStroke(1.dp, Color.Gray),
            modifier = Modifier
                .padding(start = 4.dp, top = 4.dp, bottom = 4.dp)
                .fillMaxHeight()
                .weight(1f),
            colors = CardDefaults.cardColors(Color.Transparent)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = activity,
                    style = AppTheme.typography.smallSemiBold,
                    color = Color.DarkGray,
                    modifier = Modifier.weight(1f),
                    overflow = TextOverflow.Ellipsis,
                    softWrap = false
                )
            }
        }
    }
}
