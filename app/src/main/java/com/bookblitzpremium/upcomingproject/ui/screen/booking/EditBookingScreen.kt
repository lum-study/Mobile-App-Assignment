package com.bookblitzpremium.upcomingproject.ui.screen.booking


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.Log
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.BoxMaps
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.HotelBooking
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.ToastUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.time.format.TextStyle
import java.time.temporal.ChronoUnit
import java.util.Locale

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
        LocalDate.now()
    }

    val options = mutableListOf<Pair<LocalDate, LocalDate>>()
    require(maxRange >= 1) { "maxRange must be at least 1 to allow a 1-day range" }
    val safeMaxRange = maxRange.coerceAtMost(365)
    val today = LocalDate.now()
    val endDateLimit = startDateLocalDate
    val daysDifference = ChronoUnit.DAYS.between(today, endDateLimit).toInt().coerceAtLeast(0)
    val range = (0..daysDifference)

    if(safeMaxRange == 1 && daysDifference == 1 ){
        range.forEach { offset ->
            val startOption = today.plusDays(offset.toLong())
            val endOption1 = startOption.plusDays(1)
            if (!endOption1.isAfter(endDateLimit)) { // Ensure the end date doesn't exceed the limit
                options.add(Pair(startOption, endOption1))
            }
        }
    }else{
        range.forEach { offset ->
            val startOption = today.plusDays(offset.toLong())
            val endOptionMax = startOption.plusDays((safeMaxRange - 1).toLong())
            if (endOptionMax.isAfter(startOption) && safeMaxRange > 1) {
                options.add(Pair(startOption, endOptionMax))
            }
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

//@Composable
//fun MyScreen() {
//    val currentMonth = YearMonth.of(2025, 5) // May 2025
//    val availability = (1..31).associateWith { "Available" } // Example: all days available
//    val startDate = LocalDate.of(2025, 5, 9) // 2025-05-09
//    val endDate = LocalDate.of(2025, 5, 17)  // 2025-05-17
//
//    CalendarView(
//        currentMonth = currentMonth,
//        availability = availability,
//        onDateRangeSelected = { start, end ->
//            // Handle the selected range (e.g., update view model or state)
//            println("Selected range: $start to $end")
//        },
//        startDate = startDate,
//        endDate = endDate
//    )
//}

@Composable
fun CalendarView(
    initialStartMonth: YearMonth,
    numMonths: Int = 3,
    availability: Map<LocalDate, String>,
    onDateRangeSelected: (LocalDate?, LocalDate?) -> Unit,
    startDate: LocalDate?,
    endDate: LocalDate?,
    customRangeDays: Int
) {
    val context = LocalContext.current
    var startMonth by remember { mutableStateOf(initialStartMonth) }
    var tempStartDate by remember { mutableStateOf<LocalDate?>(startDate) }
    var tempEndDate by remember { mutableStateOf<LocalDate?>(endDate) }

    // Update temp dates when startDate or endDate change
    LaunchedEffect(startDate, endDate) {
        if (startDate != null && endDate != null && !startDate.isAfter(endDate)) {
            tempStartDate = startDate
            tempEndDate = endDate
            onDateRangeSelected(tempStartDate, tempEndDate)
        }
    }

    val months = List(numMonths) { offset -> startMonth.plusMonths(offset.toLong()) }

    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { startMonth = startMonth.minusMonths(numMonths.toLong()) }) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Previous Months"
                )
            }
            Text(
                text = "${months.first().month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} ${months.first().year} - " +
                        "${months.last().month.getDisplayName(TextStyle.SHORT, Locale.getDefault())} ${months.last().year}",
                style = AppTheme.typography.mediumBold
            )
            IconButton(onClick = { startMonth = startMonth.plusMonths(numMonths.toLong()) }) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Next Months"
                )
            }
        }

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            modifier = Modifier.padding(8.dp)
        ) {
            val today = LocalDate.now()

            item(span = { GridItemSpan(7) }) {
                Row {
                    listOf("Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat").forEach { day ->
                        Text(
                            text = day,
                            modifier = Modifier
                                .weight(1f)
                                .padding(4.dp),
                            textAlign = TextAlign.Center,
                            style = AppTheme.typography.mediumNormal
                        )
                    }
                }
            }

            months.forEach { month ->
                item(span = { GridItemSpan(7) }) {
                    Text(
                        text = month.month.getDisplayName(TextStyle.FULL, Locale.getDefault()) + " ${month.year}",
                        style = AppTheme.typography.smallRegular,
                        modifier = Modifier.padding(8.dp)
                    )
                }

                val daysInMonth = month.lengthOfMonth()
                val firstDayOfWeek = month.atDay(1).dayOfWeek.value % 7

                items(firstDayOfWeek) {
                    Box(modifier = Modifier.size(40.dp))
                }

                items(daysInMonth) { dayIndex ->
                    val day = dayIndex + 1
                    val date = LocalDate.of(month.year, month.month, day)
                    val status = if (date.isBefore(today)) "NotAvailable" else availability[date] ?: "NotAvailable"

                    val expectedEndDate = tempStartDate?.plusDays((customRangeDays - 1).toLong())

                    val isStartDate = tempStartDate == date
                    val isEndDate = expectedEndDate == date
                    val isInRange = tempStartDate != null && expectedEndDate != null &&
                            date in (tempStartDate!!..expectedEndDate) &&
                            availability[date] == "Available"

                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(
                                when {
                                    isStartDate || isEndDate -> AppTheme.colorScheme.selectDate
                                    isInRange -> AppTheme.colorScheme.inRangeBackground
                                    else -> Color.Transparent
                                }
                            )
                            .clickable(
                                enabled = true,
                                onClick = {
                                    if (status == "Available") {
                                        tempStartDate = date
                                        val calculatedEndDate = date.plusDays((customRangeDays - 1).toLong())

                                        val allDatesAvailable = (0L until customRangeDays.toLong()).all { offset ->
                                            val checkDate = date.plusDays(offset)
                                            (if (checkDate.isBefore(today)) "NotAvailable" else availability[checkDate] ?: "NotAvailable") == "Available"
                                        }

                                        if (allDatesAvailable) {
                                            tempEndDate = calculatedEndDate
                                            onDateRangeSelected(tempStartDate, tempEndDate)
                                            ToastUtils.showSingleToast(context, "Selected range: $date to $calculatedEndDate")
                                        } else {
                                            tempStartDate = null
                                            tempEndDate = null
                                            ToastUtils.showSingleToast(context, "Some dates in the range are not available")
                                        }
                                    } else {
                                        ToastUtils.showSingleToast(context, "Date is not available")
                                    }
                                }
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = day.toString(),
                                style = AppTheme.typography.mediumNormal,
                                textAlign = TextAlign.Center,
                                color = if (status == "Available") {
                                    AppTheme.colorScheme.onSurface
                                } else {
                                    AppTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                                }
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
        }
    }
}

@Composable
fun CalendarDialog(
    initialStartMonth: YearMonth,
    startDate: LocalDate?,
    endDate: LocalDate?,
    customRangeDays: Int,
    onDismiss: () -> Unit,
    onDateRangeSelected: (LocalDate, LocalDate) -> Unit
) {
    val context = LocalContext.current
    var selectedStartDate by remember { mutableStateOf<LocalDate?>(startDate) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(endDate) }

    // Generate availability for multiple months
    val months = List(5) { offset -> initialStartMonth.plusMonths(offset.toLong()) }
    val availability: Map<LocalDate, String> = months.flatMap { month ->
        (1..month.lengthOfMonth()).map { day ->
            LocalDate.of(month.year, month.month, day) to "Available"
        }
    }.toMap()

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = AppTheme.colorScheme.background,
            tonalElevation = 4.dp,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
                    .fillMaxHeight()
            ) {

                Text(
                    text = "Select Booking Dates",
                    style = AppTheme.typography.titleLarge,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Button(
                    onClick = {
                        if (selectedStartDate != null && selectedEndDate != null) {
                            onDateRangeSelected(selectedStartDate!!, selectedEndDate!!)
                            onDismiss()
                        } else {
                            ToastUtils.showSingleToast(context, "Please select a valid date range")
                        }
                    },
                    enabled = selectedStartDate != null && selectedEndDate != null,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.primary,
                        contentColor = AppTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Confirm")
                }


                CalendarView(
                    initialStartMonth = initialStartMonth,
                    numMonths = 5,
                    availability = availability,
                    onDateRangeSelected = { start, end ->
                        selectedStartDate = start
                        selectedEndDate = end
                    },
                    startDate = selectedStartDate,
                    endDate = selectedEndDate,
                    customRangeDays = customRangeDays
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (selectedStartDate != null && selectedEndDate != null) {
                    Text(
                        text = "Selected: $selectedStartDate to $selectedEndDate",
                        style = AppTheme.typography.mediumNormal,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(
                        onClick = onDismiss,
                        colors = ButtonDefaults.textButtonColors(
                            contentColor = AppTheme.colorScheme.onSurface
                        )
                    ) {
                        Text("Cancel")
                    }

                    Spacer(modifier = Modifier.width(8.dp))


                }
            }
        }
    }
}

@Composable
fun ModifyHotelBooking(
    navController: NavController,
    modifier: Modifier = Modifier,
    bookingId: String,
) {
    val bookingViewModel: LocalHotelBookingViewModel = hiltViewModel()
    val hotelViewModel: LocalHotelViewModel = hiltViewModel()
    val remoteHotelBooking: RemoteHotelBookingViewModel = hiltViewModel()

    bookingViewModel.fetchHotelBookingsById(bookingId)

    val bookingData by bookingViewModel.hotelBookingHistory.collectAsState()
    val booking = bookingData.firstOrNull()

    val hotelData by hotelViewModel.selectedHotel.collectAsState()
    val success by remoteHotelBooking.success.collectAsState()
    val error by remoteHotelBooking.error.collectAsState()
    val isBookingLoading by remoteHotelBooking.loading.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(success) {
        if (success) {
            navController.popBackStack()
            ToastUtils.showSingleToast(context, "Successfully changed the date")
            remoteHotelBooking.clearSuccess()
        }
    }

    booking?.let {
        hotelViewModel.getHotelByID(booking.hotelID.toString())

        if (hotelData != null) {
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            val initialStart = LocalDate.parse(booking.startDate, formatter)
            val initialEnd = LocalDate.parse(booking.endDate, formatter)
            val maxRange = ChronoUnit.DAYS.between(initialStart, initialEnd).toInt() + 1

            var selectedStartDate by rememberSaveable { mutableStateOf<LocalDate?>(initialStart) }
            var selectedEndDate by rememberSaveable { mutableStateOf<LocalDate?>(initialEnd) }
            var selectedRange by rememberSaveable { mutableStateOf<Pair<LocalDate, LocalDate>?>(Pair(initialStart, initialEnd)) }
            var showClick by remember { mutableStateOf(false) }

            // Debug state changes
            LaunchedEffect(selectedStartDate, selectedEndDate) {
                println("Selected Start Date: $selectedStartDate")
                println("Selected End Date: $selectedEndDate")
            }

            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(8.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                StyledImage(hotelData!!.imageUrl.toString(), tabletPortrait = "true")

                Spacer(modifier = Modifier.height(12.dp))

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                ) {
                    Box(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = hotelData!!.name.toString(),
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.align(Alignment.CenterStart)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Row {
                            repeat(hotelData!!.rating?.toInt() ?: 0) {
                                Text(text = "⭐", fontSize = 20.sp)
                            }
                        }

                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Filled.LocationOn,
                                contentDescription = null,
                                tint = AppTheme.colorScheme.onPrimary,
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Malaysia, ${extractMalaysianState(hotelData!!.address.toString())}",
                                style = AppTheme.typography.mediumBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(AppTheme.colorScheme.surface)
                            .border(1.dp, AppTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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

                        Divider(
                            color = AppTheme.colorScheme.primary,
                            modifier = Modifier
                                .width(1.dp)
                                .fillMaxHeight()
                        )

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

                    Button(
                        onClick = { showClick = true },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.onBackground,
                            contentColor = AppTheme.colorScheme.background
                        )
                    ) {
                        Text("Select Date Range")
                    }

                    if (showClick) {
                        CalendarDialog(
                            initialStartMonth = YearMonth.now(),
                            startDate = selectedStartDate,
                            endDate = selectedEndDate,
                            customRangeDays = maxRange,
                            onDismiss = { showClick = false },
                            onDateRangeSelected = { start, end ->
                                selectedStartDate = start
                                selectedEndDate = end
                                selectedRange = start to end
                                println("Updated Start Date in Dialog: $start")
                                println("Updated End Date in Dialog: $end")
                            }
                        )
                    }

                    DetailsSection(
                        roomBooked = booking.numberOfRoom.toString(),
                        totalPerson = booking.numberOFClient.toString(),
                        totalPrice = "0",
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {
                            if (selectedStartDate != null && selectedEndDate != null) {
                                val updatedBooking = HotelBooking(
                                    numberOFClient = booking.numberOFClient,
                                    numberOfRoom = booking.numberOfRoom,
                                    hotelID = booking.hotelID,
                                    userid = booking.userid,
                                    paymentID = booking.paymentID,
                                    status = booking.status,
                                    id = booking.id,
                                    startDate = selectedStartDate.toString(),
                                    endDate = selectedEndDate.toString()
                                )
                                remoteHotelBooking.updateHotelBooking(updatedBooking)
                            } else {
                                ToastUtils.showSingleToast(context, "Please select a valid date range")
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.onBackground,
                            contentColor = AppTheme.colorScheme.background
                        ),
                        enabled = selectedStartDate != null && selectedEndDate != null
                    ) {
                        Text("Change Date")
                    }
                }
            }
        } else {
            CheckStatusLoading()
        }
    }

    if (isBookingLoading) {
        CheckStatusLoading()
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
