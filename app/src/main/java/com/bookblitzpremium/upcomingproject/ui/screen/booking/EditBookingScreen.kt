package com.bookblitzpremium.upcomingproject.ui.screen.booking


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.TurnedInNot
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@Preview(showBackground = true)
@Composable
fun PreviewIt(){
    val navController = rememberNavController()
//    ModifyHotelBooking(navController, modifier = Modifier)
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookingDaySelector(
    modifier: Modifier,
    startDate: String,
    maxRange: Int, // Maximum number of days for booking (e.g., 30 days)
    onBookingRangeSelected: (LocalDate, LocalDate) -> Unit // Callback to return the selected dates
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf("Select Booking Range") }
    // Define the pattern of the date string
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd") // Adjust the pattern if needed
    val startDateLocalDate = LocalDate.parse(startDate, formatter)

    // Generate booking options with start and end dates
    val options = mutableListOf<Pair<LocalDate, LocalDate>>()
    val range = (0 until maxRange) // The number of options you want (first, second, third, fourth)

    range.forEach { offset ->
        val startOption = startDateLocalDate.minusDays(7 - offset.toLong())
        val endOption = startOption.plusDays((maxRange - 1).toLong())
        options.add(Pair(startOption, endOption))
    }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
    ) {
        TextField(
            value = selectedText,
            onValueChange = {},
            readOnly = true,
            label = { Text("Booking Duration") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            modifier = Modifier.menuAnchor()
                .fillMaxWidth(),
            colors = TextFieldDefaults.textFieldColors(
                focusedIndicatorColor = Color.Black,
                unfocusedIndicatorColor = Color.Black,
                focusedLabelColor = Color.Black,
                unfocusedLabelColor = Color.Black,
                cursorColor = Color.Black
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { (start, end) ->
                DropdownMenuItem(
                    text = {
                        Text("From ${start.toString()} to ${end.toString()}")
                    },
                    onClick = {
                        selectedText = "From ${start} to ${end}"
                        onBookingRangeSelected(start, end)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Composable
fun ModifyHotelBooking(
    navController: NavController,
    modifier: Modifier,
    booking : String
) {


    val bookingViewModel : LocalHotelBookingViewModel = hiltViewModel()
    val hotelViewModel : LocalHotelViewModel = hiltViewModel()

    var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }

    bookingViewModel.fetchHotelBookingsById(booking)
    val bookingData by bookingViewModel.hotelBookingHistory.collectAsState()
    val booking = bookingData.firstOrNull()

    hotelViewModel.getHotelByID(booking?.hotelID.toString())
    val hotelData by hotelViewModel.selectedHotel.collectAsState()

    booking?.let {
        if(hotelData !=null){
            // The start date and maximum range
            val startDate = booking.startDate
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

            // Parse strings to LocalDate
            val start = LocalDate.parse(booking.startDate, formatter)
            val end = LocalDate.parse(booking.endDate, formatter)

            // Calculate days between
            val maxRange = ChronoUnit.DAYS.between(start, end).toInt()

            // Callback to handle the selected booking range
            val onBookingRangeSelected: (LocalDate, LocalDate) -> Unit = { start, end ->
                selectedStartDate = start
                selectedEndDate = end
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                StyledImage(hotelData?.imageUrl.toString())

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                ) {
                    // — HEADER: Title centered —
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        Text(
                            text = hotelData?.name.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }

                    // — RATING & LOCATION: Stars on the left, location on the right —
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        // Stars
                        Row {
                            repeat(hotelData?.rating?.toInt() ?: 0) {
                                Text(text = "⭐", fontSize = 20.sp)
                            }
                        }

                        // Location
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Malaysia, ${extractMalaysianState(hotelData?.address.toString())}",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // — LEGEND ROW: spaced evenly across width —
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        LegendItem1(
                            icon = Icons.Filled.CalendarToday,
                            iconDescription = "Check In",
                            label = "Check‑In",
                            date = selectedStartDate?.toString() ?: booking.startDate
                        )
                        LegendItem1(
                            icon = Icons.Filled.CalendarToday,
                            iconDescription = "Check Out",
                            label = "Check‑Out",
                            date = selectedEndDate?.toString() ?: booking.endDate
                        )
                        LegendItem1(
                            icon = Icons.Filled.TurnedInNot,
                            iconDescription = "Ticket",
                            label = "Ticket",
                            date = ""
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // — BOOKING RANGE DROPDOWN: full width —
                    BookingDaySelector(
                        startDate = booking.startDate.toString(),
                        maxRange = maxRange,
                        onBookingRangeSelected = onBookingRangeSelected,
                        modifier = Modifier.fillMaxWidth()
                    )

                    // — DETAILS SECTION —
                    DetailsSection(
                        roomBooked = booking.numberOfRoom.toString(),
                        totalPerson = booking.numberOFClient.toString(),
                        totalPrice = 0.toString(),  //find by payment ID
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    // — NEXT BUTTON: fixed at bottom, centered horizontally —
                    Button(
                        onClick = {
                            val booking = HotelBooking(
                                startDate = selectedStartDate?.toString() ?: booking.startDate,
                                endDate = selectedEndDate?.toString() ?: booking.endDate,
                                numberOFClient = booking.numberOFClient,
                                numberOfRoom = booking.numberOfRoom,
                                hotelID = booking.hotelID.toString(),
                                userid = "user999",
                                paymentID = "payment123"
                            )
                            bookingViewModel.updateHotelBooking(booking)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        )
                    ) {
                        Text("Next")
                    }
                }
            }
        }
    }
}

fun extractMalaysianState(address: String): String? {
    val states = listOf(
        "Johor", "Kedah", "Kelantan", "Melaka", "Negeri Sembilan",
        "Pahang", "Penang", "Perak", "Perlis", "Sabah",
        "Sarawak", "Selangor", "Terengganu", "Kuala Lumpur",
        "Labuan", "Putrajaya" ,"Pulau Pinang"
    )

    // Convert to lowercase for case-insensitive match
    val addressLower = address.lowercase()

    return states.firstOrNull { state ->
        addressLower.contains(state.lowercase())
    }
}

