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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.model.Activity
import com.bookblitzpremium.upcomingproject.model.Schedule
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.time.LocalDate
import java.time.LocalTime

@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun SchedulePreview() {
    ScheduleScreen(rememberNavController())
}

@Composable
fun ScheduleScreen(navController: NavHostController) {
    var isEdit by remember { mutableStateOf(true) }
    var selectedTabIndex by remember { mutableStateOf(0) }
    var scheduleList: List<Schedule> by remember {
        mutableStateOf(
            listOf(
                Schedule(
                    date = LocalDate.now().month.toString().lowercase()
                        .replaceFirstChar { it.uppercase() } + " " + LocalDate.now().dayOfMonth.toString(),
                    activity = listOf(
                        Activity(
                            "21:23",
                            "Swimming"
                        ),
                        Activity(
                            "18:23",
                            "Snooker"
                        ),
                        Activity(
                            "21:53",
                            "Ping Pong"
                        ),
                        Activity(
                            "10:54",
                            "Rest"
                        ),
                    )
                ),
                Schedule(
                    date = LocalDate.now().month.toString().lowercase()
                        .replaceFirstChar { it.uppercase() } + " " + LocalDate.now().dayOfMonth.toString(),
                    activity = listOf(
                        Activity(
                            LocalTime.now().toString(),
                            "String"
                        )
                    )
                ),
                Schedule(
                    date = LocalDate.now().month.toString().lowercase()
                        .replaceFirstChar { it.uppercase() } + " " + LocalDate.now().dayOfMonth.toString(),
                    activity = listOf(
                        Activity(
                            LocalTime.now().toString(),
                            "String"
                        )
                    )
                ),
                Schedule(
                    date = LocalDate.now().month.toString().lowercase()
                        .replaceFirstChar { it.uppercase() } + " " + LocalDate.now().dayOfMonth.toString(),
                    activity = listOf(
                        Activity(
                            LocalTime.now().toString(),
                            "String"
                        )
                    )
                )
            )
        )
    }
    val selectedScheduleActivity = scheduleList.getOrNull(selectedTabIndex)?.activity
    fun removeActivity(activityToRemove: Activity) {
        scheduleList = scheduleList.map { schedule ->
            schedule.copy(activity = schedule.activity.filter { it != activityToRemove })
        }
    }

    fun addActivity(time: String, activity: String) {
        scheduleList = scheduleList.map { schedule ->
            schedule.copy(activity = schedule.activity + Activity(time, activity))
        }
    }
    AppTheme {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier.padding(end = 16.dp, bottom = 8.dp, start = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ) {
                if (scheduleList.size <= 3) {
                    TabRow(
                        selectedTabIndex = selectedTabIndex,

                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        scheduleList.forEachIndexed { index, schedule ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Text(
                                            text = stringResource(R.string.day_count, index + 1),
                                            style = AppTheme.typography.mediumSemiBold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = schedule.date,
                                            style = AppTheme.typography.smallRegular,
                                            color = Color.Gray
                                        )
                                    }
                                },
                            )
                        }
                    }
                } else {
                    ScrollableTabRow(
                        selectedTabIndex = selectedTabIndex,
                        edgePadding = 8.dp,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        scheduleList.forEachIndexed { index, schedule ->
                            Tab(
                                selected = selectedTabIndex == index,
                                onClick = { selectedTabIndex = index },
                                text = {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                    ) {
                                        Text(
                                            text = stringResource(R.string.day_count, index + 1),
                                            style = AppTheme.typography.mediumSemiBold,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = schedule.date,
                                            style = AppTheme.typography.smallRegular,
                                            color = Color.Gray
                                        )
                                    }
                                },
                            )
                        }
                    }
                }
                if (selectedScheduleActivity != null) {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(bottom = 70.dp)
                    ) {
                        items(selectedScheduleActivity.sortedBy { it.time }) { activity ->
                            TimeAndDestinationRow(
                                time = activity.time,
                                activity = activity.description,
                                onDelete = { removeActivity(activity) })
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

            if (isEdit) {
                FloatingActionButton(
                    onClick = { addActivity("16:00", "ssdd") },
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 16.dp),
                    shape = CircleShape,

                    ) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = stringResource(R.string.add_button),
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
    onDelete: () -> Unit
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
                    .padding(horizontal = 12.dp, vertical = 8.dp),
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
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}
