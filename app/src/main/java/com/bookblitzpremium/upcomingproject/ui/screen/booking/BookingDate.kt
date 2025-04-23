package com.bookblitzpremium.upcomingproject.ui.screen.booking

import android.util.Log
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.components.TextHeader
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.net.URLEncoder
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.TextStyle
import java.util.Locale

//@Preview(showBackground = true , widthDp = 500 , heightDp = 1000)
//@Composable
//fun PreviewDate(){
//    BookingDatePage(modifier = Modifier)
//}

@Composable
fun BookingDatePage(
    modifier: Modifier,
    navController: NavController,
    hotelID : String,
    hotelPrice : String,
) {

    var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }
    val isValidResult = false

    CalendarView(
        navController = navController,
        startDate = selectedStartDate,
        endDate = selectedEndDate,
        onDateRangeSelected = { start, end ->
            selectedStartDate = start
            selectedEndDate = end
        },
        hotelID = hotelID,
        hotelPrice = hotelPrice
    )
}


@Preview(showBackground = true , widthDp = 500 , heightDp = 1000)
@Composable
fun PreviewShowDate(){
    ShowDate(date = "27-03-2025", date2 = "29-6-2025", modifier = Modifier.padding(vertical = 16.dp,))
}

@Composable
fun ShowDate(
    date: String,
    date2: String,
    modifier: Modifier = Modifier,
//    navController: NavController
) {

    Column(
        modifier = Modifier
            .padding(bottom = 16.dp, top = 0.dp)
            .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween // ✅ Ensures equal spacing
        ) {
            Column(
                modifier = Modifier.weight(1f), // ✅ Equal width
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Calendar",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )

                Text(
                    text = if (date.isNotEmpty()) date else "Not Selected", // ✅ Dynamic text
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow Forward",
                modifier = Modifier.size(42.dp)
            )

            Column(
                modifier = Modifier.weight(1f), // ✅ Equal width
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Calendar",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )

                Text(
                    text = if (date2.isNotEmpty()) date2 else "Not Selected", // ✅ Dynamic text
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }


    }
}

//Spacer(modifier = Modifier.weight(1f))
//


@Composable
fun RangeBetweenDate(
    currentMonth: YearMonth,
    onMonthChange: (YearMonth) -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp), // ✅ Add spacing
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowCircleLeft,
            contentDescription = "ArrowCircleLeft",
            tint = Color.Black,
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
            tint = Color.Black,
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
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
    )
}

@Composable
fun CalendarView(
    navController: NavController,
    startDate: LocalDate?,
    endDate: LocalDate?,
    onDateRangeSelected: (LocalDate?, LocalDate?) -> Unit,
    hotelID: String,
    hotelPrice: String
) {

    var currentMonth by rememberSaveable { mutableStateOf(YearMonth.now()) }
    var tempStartDate by rememberSaveable { mutableStateOf<LocalDate?>(startDate) }
    var tempEndDate by rememberSaveable { mutableStateOf<LocalDate?>(endDate) }

    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(currentMonth.year, currentMonth.month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7

    // Define availability map
    val availability = (1..daysInMonth).associateWith { day ->
        "Available" // Every day is now available
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
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
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
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
                        availability[day] == "Available" // ❌ Prevent "Not Available" in range

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                isStartDate || isEndDate -> Color(0xFFFF5733) // Start & end
                                isInRange -> Color(0xFFFFC107).copy(alpha = 0.5f) // Range highlight
                                else -> Color.Transparent
                            }
                        )
                        .clickable(
                            enabled = status == "Available",
                            onClick = {
                                when {
                                    tempStartDate == null -> tempStartDate = date
                                    tempEndDate == null && date > tempStartDate -> {
                                        // Check if all dates in range are available
                                        val allDatesAvailable = (tempStartDate!!.dayOfMonth..day)
                                            .all { d ->
                                                availability[d] == "Available"
                                            }

                                        if (allDatesAvailable) {
                                            tempEndDate = date
                                            onDateRangeSelected(tempStartDate, tempEndDate)
                                        } else {
                                            // Reset if range contains NotAvailable
                                            tempStartDate = date
                                            tempEndDate = null
                                        }
                                    }
                                    else -> { // Reset selection
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
                            color = if (status == "Available") Color.Black else Color.Gray,
                            textAlign = TextAlign.Center
                        )
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(
                                    when {
                                        isStartDate || isEndDate -> Color(0xFFFF5733)
                                        isInRange -> Color(0xFFFFC107)
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
            LegendItem(color = Color.Black, label = "Available")
//            LegendItem(color = Color.Gray, label = "Not Available")
            LegendItem(color = Color(0xFFFF5733), label = "Start/End Date")
        }

        Spacer(modifier = Modifier.height(40.dp))

        Text(
            text = "Selected Date",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        ShowDate(
            date = tempStartDate?.toString() ?: "Not selected",
            date2 = tempEndDate?.toString() ?: "Not selected",
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {

                val hotelID = URLEncoder.encode(hotelID, "UTF-8")
                val hotelPrice = URLEncoder.encode(hotelPrice, "UTF-8")
                val startDate = URLEncoder.encode(tempStartDate.toString(), "UTF-8")
                val endDate = URLEncoder.encode(tempEndDate.toString(), "UTF-8")
                navController.navigate(
                    "${AppScreen.BookingPeople.route}/$hotelID/$hotelPrice/$startDate/$endDate"
                )
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = "Next")
        }

        LaunchedEffect(key1 = tempStartDate ==null && tempEndDate == null) {
            if (tempStartDate ==null && tempEndDate ==null) {
                Log.d("HotelBookingForm", "Both start and end dates are empty")
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
        Text(text = label, fontSize = 12.sp)
    }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 600)
@Composable
fun PreviewCalendarView() {
    var currentMonth by remember { mutableStateOf(YearMonth.of(2025, 3)) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

    RangeBetweenDate(currentMonth, onMonthChange = { newMonth ->
        currentMonth = newMonth // 🔥 State updates correctly
    }) {
        DateFormat(currentMonth) // ✅ Correctly passes the updated state
    }


//    CalendarView(
//        selectedDate = selectedDate,
//        onDateSelected = { newDate ->
//            selectedDate = newDate // 🔥 Update selected date
//        },
//        navController =
//    )
}