package com.bookblitzpremium.upcomingproject.ui.screen.booking


import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.LocationOn
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.Log
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.ToastUtils
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
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
    modifier: Modifier = Modifier,
    startDate: String,
    maxRange: Int,
    onBookingRangeSelected: (LocalDate, LocalDate) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by rememberSaveable { mutableStateOf("Select Booking Range") }

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val startDateLocalDate = try {
        LocalDate.parse(startDate, formatter)
    } catch (e: DateTimeParseException) {
        Log.e("BookingDaySelector", "Invalid startDate format: $startDate", e)
        LocalDate.now()
    }

    val options = mutableListOf<Pair<LocalDate, LocalDate>>()
    require(maxRange >= 1) { "maxRange must be at least 1 to allow a 1-day range" }
    val safeMaxRange = maxRange.coerceAtMost(365)
    val today = LocalDate.now()
    val endDateLimit = startDateLocalDate
    val daysDifference = ChronoUnit.DAYS.between(today, endDateLimit).toInt().coerceAtLeast(0)
    val range = (0..daysDifference)

    range.forEach { offset ->
        val startOption = today.plusDays(offset.toLong())
        // Add 1-day range option
        val endOption1 = startOption.plusDays(1)
        if (endOption1.isAfter(startOption)) {
            options.add(Pair(startOption, endOption1))
        }
        // Add maxRange option (if different)
        val endOptionMax = startOption.plusDays((safeMaxRange - 1).toLong())
        if (endOptionMax.isAfter(startOption) && safeMaxRange > 1) {
            options.add(Pair(startOption, endOptionMax))
        }
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
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = AppTheme.colorScheme?.primary ?: Color.Black,
                unfocusedIndicatorColor = AppTheme.colorScheme?.secondary ?: Color.Gray,
                focusedLabelColor = AppTheme.colorScheme?.primary ?: Color.Black,
                unfocusedLabelColor = AppTheme.colorScheme?.onSurface ?: Color.DarkGray,
                cursorColor = AppTheme.colorScheme?.primary ?: Color.Black,
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            if (options.isEmpty()) {
                DropdownMenuItem(
                    text = { Text("No options available") },
                    onClick = { expanded = false },
                    enabled = false
                )
            } else {
                options.forEach { (start, end) ->
                    DropdownMenuItem(
                        text = {
                            Text("From $start to $end (up to $safeMaxRange days)")
                        },
                        onClick = {
                            selectedText = "From $start to $end (up to $safeMaxRange days)"
                            onBookingRangeSelected(start, end)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ModifyHotelBooking(
    navController: NavController,
    modifier: Modifier = Modifier,
    booking : String
) {

    val bookingViewModel: LocalHotelBookingViewModel = hiltViewModel()
    val hotelViewModel: LocalHotelViewModel = hiltViewModel()
    val remoteHotelBooking: RemoteHotelBookingViewModel = hiltViewModel()

    var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }

    bookingViewModel.fetchHotelBookingsById(booking)

    val bookingData by bookingViewModel.hotelBookingHistory.collectAsState()
    val booking = bookingData.firstOrNull()

    hotelViewModel.getHotelByID(booking?.hotelID.toString())

    val hotelData by hotelViewModel.selectedHotel.collectAsState()

    val success by remoteHotelBooking.success.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(success) {
        if (success) {
            navController.popBackStack()
            ToastUtils.showSingleToast(context, "Successfully change the date")
            remoteHotelBooking.clearSuccess() // Reset success after navigation
        }
    }

    booking?.let {
        if (hotelData != null) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val start = LocalDate.parse(booking.startDate, formatter)
            val end = LocalDate.parse(booking.endDate, formatter)
            val maxRange = ChronoUnit.DAYS.between(start, end).toInt()

            val onBookingRangeSelected: (LocalDate, LocalDate) -> Unit = { start, end ->
                selectedStartDate = start
                selectedEndDate = end
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                StyledImage(hotelData?.imageUrl.toString(), tabletPortrait = "true")

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
                                tint = AppTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Malaysia, ${extractMalaysianState(hotelData?.address.toString())}",
                                style = AppTheme.typography.mediumBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))


//                    val locationName = hotelData!!.name
//                    BoxMaps(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .height(200.dp)
//                            .clip(shape = RoundedCornerShape(32.dp)),
//                        addressInput = locationName,
//                        onClick = {
//                            navController.navigate("${AppScreen.Maps.route}/${locationName}")
//                        },
//                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height( 80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AppTheme.colorScheme.surface) // Use surface for background
                            .border(1.dp, AppTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // Check-In half
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LegendItem1(
                                icon = Icons.Filled.CalendarToday,
                                iconDescription = "Check-In Icon",
                                label = "Check-In",
                                date = selectedStartDate?.toString() ?: booking.startDate,
                                modifier = Modifier
                            )
                        }

                        // Vertical divider
                        Divider(
                            color = AppTheme.colorScheme.primary, // Use primary for divider
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                        )

                        // Check-Out half
                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LegendItem1(
                                icon = Icons.Filled.CalendarToday,
                                iconDescription = "Check-Out Icon",
                                label = "Check-Out",
                                date = selectedEndDate?.toString() ?: booking.endDate,
                                modifier = Modifier
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // — BOOKING RANGE DROPDOWN: full width —
                    BookingDaySelector(
                        startDate = booking.startDate.toString(),
                        maxRange = maxRange,
                        onBookingRangeSelected = onBookingRangeSelected,
                        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // — DETAILS SECTION —
                    DetailsSection(
                        roomBooked = booking.numberOfRoom.toString(),
                        totalPerson = booking.numberOFClient.toString(),
                        totalPrice = 0.toString(),  //find by payment ID
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    val error by remoteHotelBooking.error.collectAsState()

                    val context = LocalContext.current
                    LaunchedEffect(error) {
                        error?.let { error ->
                            ToastUtils.showSingleToast(context, error)
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // — NEXT BUTTON: fixed at bottom, centered horizontally —
                    Button(
                        onClick = {
                            val booking = HotelBooking(
                                numberOFClient = booking.numberOFClient,
                                numberOfRoom = booking.numberOfRoom,
                                hotelID = booking.hotelID,
                                userid = booking.userid,
                                paymentID = booking.paymentID,
                                status = booking.status,
                                id = booking.id,
                                startDate = selectedStartDate?.toString() ?: booking.startDate,
                                endDate = selectedEndDate?.toString() ?: booking.endDate,
                            )
                            remoteHotelBooking.updateHotelBooking(booking)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.onBackground,
                            contentColor = AppTheme.colorScheme.background
                        )
                    ) {
                        Text("Change Date")
                    }
                }
            }

        }else{
            CheckStatusLoading()
        }

        val isBookingLoading by remoteHotelBooking.loading.collectAsState()

        if(isBookingLoading){
            CheckStatusLoading()
        }
    }
}

@Composable
fun MapsButton(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFFE0E0E0),
            contentColor = Color.Black
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = "Maps",
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
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
