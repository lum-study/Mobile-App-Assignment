package com.bookblitzpremium.upcomingproject.ui.screen.booking

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.ArrowCircleLeft
import androidx.compose.material.icons.filled.ArrowCircleRight
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
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
    navController: NavController
) {
    var currentMonth by remember { mutableStateOf(YearMonth.of(2025, 3)) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }

        // ✅ Pass `currentMonth` so the calendar updates correctly
        CalendarView(
            selectedDate = selectedDate,
            onDateSelected = { newDate ->
                selectedDate = newDate
            },
            navController = navController
        )

}


//@Preview(showBackground = true , widthDp = 500 , heightDp = 1000)
//@Composable
//fun PreviewShowDate(){
//    ShowDate(date = "27-03-2025", date2 = "29-6-2025", modifier = Modifier.padding(vertical = 16.dp,),nav)
//}

@Composable
fun ShowDate(
    date : String,
    date2 : String,
    modifier: Modifier,
    navController: NavController
){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 16.dp, top = 16.dp)
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
            Text(
                text = "Selected Date"
                , fontStyle = FontStyle.Normal
                , fontWeight = FontWeight.Bold
                , fontSize = 24.sp
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically // ✅ Aligns everything correctly
        ) {
            Column(
                modifier = Modifier.width(200.dp)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Calendar",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )

                Text(
                    text = "Not Selected",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }

            Icon(
                imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                contentDescription = "Arrow Forward",
                modifier = Modifier
                    .size(42.dp) // ✅ Increase size
                    .offset(x = -50.dp)
            )

            Column(
                modifier = Modifier.width(100.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.CalendarToday,
                    contentDescription = "Calendar",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )

                Text(
                    text = "Not Selected",
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                navController.navigate(AppScreen.BookingPeople.route)
            },
            colors = ButtonDefaults.buttonColors( // ✅ Correct way to set colors
                containerColor = Color.Black,
                contentColor = Color.White
            ),
            contentPadding = PaddingValues(8.dp),
            modifier = Modifier
                .fillMaxWidth()

        ){
            Text(
                text = "Next"
            )
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
    selectedDate: LocalDate?,
    onDateSelected: (LocalDate) -> Unit, // Callback to update the selected date
) {

    var currentMonth by remember { mutableStateOf(YearMonth.now()) }

    val daysOfWeek = listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat")
    val daysInMonth = currentMonth.lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(currentMonth.year, currentMonth.month, 1)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value % 7 // Adjust for Sunday start (0 = Sun)

    val availability = (1..daysInMonth).associateWith { day ->
        val date = LocalDate.of(currentMonth.year, currentMonth.month, day)
        when {
            date.isBefore(LocalDate.now()) -> "NotAvailable"
            day % 3 == 0 -> "NotAvailable"
            else -> "Available"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {

        RangeBetweenDate(currentMonth, onMonthChange = { newMonth ->
            currentMonth = newMonth
        }) {
            DateFormat(currentMonth) // ✅ Correctly displays the month
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
                val isSelected = selectedDate == date
                val status = availability[day] ?: "NotAvailable"

                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(if (isSelected) Color(0xFFFF5733) else Color.Transparent)
                        .clickable(
                            enabled = status == "Available",
                            onClick = { onDateSelected(date) } // 🔥 Update the selected date
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
                                        isSelected -> Color(0xFFFF5733)
                                        status == "Available" -> Color.Black
                                        else -> Color.Gray
                                    }
                                )
                        )
                    }
                }
            }
        }

        // Legend
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            LegendItem(color = Color.Black, label = "Available")
            LegendItem(color = Color.Gray, label = "Not Available")
            LegendItem(color = Color(0xFFFF5733), label = "Selected")
        }

        Spacer(modifier = Modifier.height(20.dp))

        ShowDate(date = "27-03-2025", date2 = "29-6-2025", modifier = Modifier.padding(top = 16.dp , bottom = 8.dp), navController)
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