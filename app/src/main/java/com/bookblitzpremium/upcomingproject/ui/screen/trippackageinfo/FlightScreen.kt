package com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo

import android.graphics.Bitmap
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AirplanemodeActive
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalFlightViewModel
import com.bookblitzpremium.upcomingproject.data.model.FlightInformation
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.components.SkeletonLoader
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder
import java.io.ByteArrayOutputStream
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Composable
fun FlightScreen(flightID: String = "", bookingID: String = "") {
    val localFlightViewModel: LocalFlightViewModel = hiltViewModel()
    var flight by remember { mutableStateOf<FlightInformation?>(null) }
    LaunchedEffect(Unit) {
        flight = localFlightViewModel.getFlightInformationByID(flightID)
    }

    val barcodeBitmap = remember { generateBarcodeBase64(bookingID) }

    AppTheme {
        Column(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxHeight(.3f)
                        .fillMaxWidth(),
                    contentAlignment = Alignment.TopCenter
                ) {
                    if (flight != null) {
                        val image = checkImage(flight!!.name)

                        Image(
                            painterResource(image[0]),
                            contentDescription = stringResource(R.string.flight_image),
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxHeight(.85f)
                        )
                        Image(
                            painterResource(image[1]),
                            contentDescription = stringResource(R.string.flight_icon),
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .height(70.dp),
                        )
                    } else {
                        SkeletonLoader(modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.85f))
                        SkeletonLoader(
                            modifier = Modifier
                                .align(Alignment.BottomCenter)
                                .height(70.dp)
                        )
                    }
                }
                if (flight != null) {
                    Text(
                        text = flight!!.name,
                        style = AppTheme.typography.largeBold,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }

            if (flight != null) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    val dateTime = LocalDateTime.of(
                        LocalDate.parse(flight!!.endDate),
                        LocalTime.parse(flight!!.endTime, DateTimeFormatter.ofPattern("H:mm"))
                    )
                    val startDateTime = dateTime.minusMinutes(flight!!.travelTime.toLong())

                    SmallTab("Flight", flight!!.id.substring(0, 5))
                    SmallTab("Gate", flight!!.gate)
                    SmallTab(
                        "Departure",
                        startDateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))
                    )
                    SmallTab(
                        "Travel Time",
                        "${flight!!.travelTime.toInt() / 60}hours ${flight!!.travelTime.toInt() % 60}min"
                    )
                }
                HorizontalDivider()
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    Location(
                        place = flight!!.departState,
                        desc = stringResource(
                            R.string.airport_name,
                            flight!!.departState,
                            flight!!.departCode
                        ),
                        time = flight!!.endTime,
                        date = flight!!.endDate,
                    )
                    VerticalDivider(
                        modifier = Modifier
                            .height(32.dp)
                            .padding(start = 12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.AirplanemodeActive,
                            contentDescription = stringResource(R.string.location)
                        )
                        Text(
                            text = stringResource(
                                R.string.airport_name,
                                flight!!.name,
                                flight!!.id.substring(0, 5)
                            ),
                            style = AppTheme.typography.mediumSemiBold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(8.dp)
                        )
                    }

                    VerticalDivider(
                        modifier = Modifier
                            .height(32.dp)
                            .padding(start = 12.dp)
                    )
                    Location(
                        place = flight!!.arrivalState,
                        desc = stringResource(
                            R.string.airport_name,
                            flight!!.arrivalState,
                            flight!!.arrivalCode
                        ),
                        time = flight!!.endTime,
                        date = flight!!.endDate,
                    )
                }
                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                barcodeBitmap?.let {
                    Base64Image(
                        base64String = it,
                        modifier = Modifier
                            .height(80.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            } else {
                SkeletonLoader(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
fun SmallTab(title: String, desc: String) {
    Column {
        Text(
            text = title,
            style = AppTheme.typography.mediumSemiBold,
            color = Color.Black,
            textAlign = TextAlign.Center
        )
        Text(
            text = desc,
            style = AppTheme.typography.mediumNormal,
            color = Color.Gray,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun Location(place: String, desc: String, time: String, date: String) {

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Outlined.LocationOn,
            contentDescription = stringResource(R.string.location)
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(start = 8.dp)
        ) {
            Text(
                text = place,
                style = AppTheme.typography.mediumSemiBold,
                color = Color.Black,
                textAlign = TextAlign.Start,
            )
            Text(
                text = desc,
                style = AppTheme.typography.smallRegular,
                color = Color.Gray,
                textAlign = TextAlign.Start
            )
        }
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = time,
                style = AppTheme.typography.mediumSemiBold,
                color = Color.Black,
                textAlign = TextAlign.End
            )
            Text(
                text = date,
                style = AppTheme.typography.smallRegular,
                color = Color.Gray,
                textAlign = TextAlign.End
            )
        }
    }
}

fun generateBarcodeBase64(content: String): String? {
    return try {
        val barcodeEncoder = BarcodeEncoder()
        val bitmap = barcodeEncoder.encodeBitmap(content, BarcodeFormat.CODE_128, 600, 150)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        val byteArray = outputStream.toByteArray()
        Base64.encodeToString(byteArray, Base64.DEFAULT)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun checkImage(flightName: String): List<Int> {
    return when (flightName) {
        "AirAsia" -> {
            listOf(R.drawable.airasia_bg, R.drawable.airasia_icon)
        }

        "Firefly" -> {
            listOf(R.drawable.airasia_bg, R.drawable.airasia_icon)
        }

        "Batik Air" -> {
            listOf(R.drawable.airasia_bg, R.drawable.airasia_icon)
        }

        else -> {
            listOf(R.drawable.airasia_bg, R.drawable.airasia_icon)
        }
    }
}