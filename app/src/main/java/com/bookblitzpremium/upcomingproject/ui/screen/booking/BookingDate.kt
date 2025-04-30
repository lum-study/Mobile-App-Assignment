package com.bookblitzpremium.upcomingproject.ui.screen.booking

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.model.Calendar
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.net.URLEncoder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale


@Preview(showBackground = true)
@Composable
fun PreviewDate(){
    val navController = rememberNavController()
    BookingDatePage(
        modifier = Modifier,
        navController = navController,
        hotelID = "",
        hotelPrice = ""
    )
}

@Composable
fun BookingDatePage(
    modifier: Modifier,
    navController: NavController,
    hotelID : String,
    hotelPrice : String,
) {
    var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }

    val calendarParameter = Calendar(
        hotelID = hotelID,
        hotelPrice = hotelPrice,
        showNext = true
    )

    CalendarView(
        navController = navController,
        startDate = selectedStartDate,
        endDate = selectedEndDate,
        onDateRangeSelected = { start, end ->
            selectedStartDate = start
            selectedEndDate = end
        },
        optionalParameter = calendarParameter
    )
}


@Composable
fun ShowDate(
    date: String,
    date2: String,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(bottom = 16.dp, top = 0.dp)
            .border(
                width = 2.dp,
                color = AppTheme.colorScheme.onPrimary,
                shape = RoundedCornerShape(16.dp)
            )
            .background(AppTheme.colorScheme.surface) // Use surface for background
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Calendar",
                    tint = AppTheme.colorScheme.primary, // Use primary for icons
                    modifier = Modifier.size(28.dp)
                )

                Text(
                    text = if (date.isNotEmpty()) date else "Not Selected",
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface, // Text on surface
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow Forward",
                tint = AppTheme.colorScheme.secondary, // Use secondary for arrow
                modifier = Modifier.size(42.dp)
            )

            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Calendar",
                    tint = AppTheme.colorScheme.primary, // Use primary for icons
                    modifier = Modifier.size(28.dp)
                )

                Text(
                    text = if (date2.isNotEmpty()) date2 else "Not Selected",
                    style = AppTheme.typography.mediumBold,
                    color = AppTheme.colorScheme.onSurface, // Text on surface
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}

@Composable
fun RangeBetweenDate(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowCircleLeft,
            contentDescription = "ArrowCircleLeft",
            tint = AppTheme.colorScheme.primary, // Use primary for navigation icons
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onMonthChange(currentMonth.minusMonths(1))
                }
        )

        content()

        Icon(
            imageVector = Icons.Filled.ArrowCircleRight,
            contentDescription = "ArrowCircleRight",
            tint = AppTheme.colorScheme.primary, // Use primary for navigation icons
            modifier = Modifier
                .size(32.dp)
                .clickable {
                    onMonthChange(currentMonth.plusMonths(1))
                }
        )
    }
}

@Composable
fun DateFormat(currentMonth: YearMonth) {
    Text(
        text = "${currentMonth.month.getDisplayName(TextStyle.FULL, Locale.getDefault())} ${currentMonth.year}",
        style = AppTheme.typography.dateBold,
        color = AppTheme.colorScheme.onSurface, // Text on surface
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun CalendarView(
    navController: NavController,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateRangeSelected: (LocalDate?, LocalDate?) -> Unit,
    optionalParameter: Calendar,
) {
    val hotelID = optionalParameter.hotelID
    val hotelPrice = optionalParameter.hotelPrice
    var showNext = optionalParameter.showNext

    var currentMonth by rememberSaveable { mutableStateOf(YearMonth.now()) }
    var tempStartDate by rememberSaveable { mutableStateOf<LocalDate?>(startDate) }
    var tempEndDate by rememberSaveable { mutableStateOf<LocalDate?>(endDate) }

    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(currentMonth.year, currentMonth.month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7


    // Define availability map
    val availability = (1..daysInMonth).associateWith { "Available" }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(AppTheme.colorScheme.background) // Use background for entire view
    ) {
        RangeBetweenDate(currentMonth, onMonthChange = { newMonth ->
            currentMonth = newMonth
        }) {
            DateFormat(currentMonth)
        }

        Spacer(modifier = Modifier.height(20.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    style = AppTheme.typography.labelMedium,
                    color = AppTheme.colorScheme.onSurface, // Text on surface
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.fillMaxWidth()
        ) {
            items(firstDayOfWeek) { Box(modifier = Modifier.size(40.dp)) }

            items(daysInMonth) { dayIndex ->
                val day = dayIndex + 1
                val date = LocalDate.of(currentMonth.year, currentMonth.month, day)
                val status = availability[day] ?: "NotAvailable"

                val isStartDate = tempStartDate == date
                val isEndDate = tempEndDate == date
                val isInRange = tempStartDate != null && tempEndDate != null &&
                        date in (tempStartDate!!..tempEndDate!!) &&
                        availability[day] == "Available"

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isStartDate || isEndDate -> AppTheme.colorScheme.primary // Use primary for start/end
                                isInRange -> AppTheme.colorScheme.secondary.copy(alpha = 0.5f) // Use secondary for range
                                else -> Color.Transparent
                            }
                        )
                        .clickable(
                            enabled = status == "Available",
                            onClick = {
                                when {
                                    tempStartDate == null -> tempStartDate = date
                                    tempEndDate == null && date > tempStartDate -> {
                                        val allDatesAvailable = (tempStartDate!!.dayOfMonth..day)
                                            .all { d -> availability[d] == "Available" }
                                        if (allDatesAvailable) {
                                            tempEndDate = date
                                            onDateRangeSelected(tempStartDate, tempEndDate)
                                        } else {
                                            tempStartDate = date
                                            tempEndDate = null
                                        }
                                    }
                                    else -> {
                                        tempStartDate = date
                                        tempEndDate = null
                                    }
                                }
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = day.toString(),
                            fontSize = 16.sp,
                            textAlign = TextAlign.Center,
                            color = if (status == "Available") AppTheme.colorScheme.onSurface else AppTheme.colorScheme.onSecondary,
                        )
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isStartDate || isEndDate -> AppTheme.colorScheme.primary
                                        isInRange -> AppTheme.colorScheme.secondary
                                        else -> Color.Transparent
                                    }
                                )
                        )
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LegendItem(color = AppTheme.colorScheme.onSurface, label = "Available")
            LegendItem(color = AppTheme.colorScheme.primary, label = "Start/End Date")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Selected Date",
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colorScheme.onSurface // Text on surface
        )

        Spacer(modifier = Modifier.height(20.dp))

        ShowDate(
            date = tempStartDate?.toString() ?: "Not selected",
            date2 = tempEndDate?.toString() ?: "Not selected",
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        )


        val context = LocalContext.current

        Spacer(modifier = Modifier.weight(1f))

        if(showNext){
            Button(
                onClick = {
                    val hotelID = URLEncoder.encode(hotelID, "UTF-8")
                    val hotelPrice = URLEncoder.encode(hotelPrice, "UTF-8")
                    val startDate = URLEncoder.encode(tempStartDate.toString(), "UTF-8")
                    val endDate = URLEncoder.encode(tempEndDate.toString(), "UTF-8")


                    if(tempStartDate.toString().isNotEmpty() && tempEndDate.toString().isNotEmpty()){
                        navController.navigate(
                            "${AppScreen.BookingPeople.route}/$hotelID/$hotelPrice/$startDate/$endDate"
                        )
                    }else{
                        Toast.makeText(context, "Please select both start and end dates", Toast.LENGTH_SHORT).show()
                    }

                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colorScheme.primary, // Use primary for button
                    contentColor = AppTheme.colorScheme.onPrimary // Text/icon on primary
                ),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = "Next",
                    color = AppTheme.colorScheme.onPrimary, // Text on primary
                    modifier = Modifier
                )
            }
        }
    }
}

@Composable
fun LegendItem(color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = label,
            style = AppTheme.typography.smallSemiBold,
            color = AppTheme.colorScheme.onSurface // Text on surface
        )
    }
}
