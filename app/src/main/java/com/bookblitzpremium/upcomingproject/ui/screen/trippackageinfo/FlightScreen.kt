package com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo

import android.graphics.Bitmap
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AirplanemodeActive
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.model.AirportInfo
import com.bookblitzpremium.upcomingproject.model.FlightSchedule
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun FlightPreview(){
    FlightScreen(rememberNavController())
}

@Composable
fun FlightScreen(navController: NavHostController) {
    val barcodeBitmap = remember { generateBarcode("flight ticket") }
    val flightInfo = FlightSchedule(
        companyName = "Air Asia",
        flightID = "LH348",
        gateID = "17A",
        travelTime = "12hours 23min",
        departure = AirportInfo(
            city = "Kuala Lumpur",
            code = "KLIA",
            date = "Jul 17",
            time = "7:00"
        ),
        arrival = AirportInfo(
            city = "Sabah",
            code = "KKIA",
            date = "Jul 17",
            time = "13:00"
        ),
    )
    AppTheme {
        Column(
            modifier = Modifier
                .padding(end = 16.dp, bottom = 8.dp, start = 16.dp)
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
                    Image(
                        painterResource(R.drawable.firefly_bg),
                        contentDescription = stringResource(R.string.flight_image),
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(.85f)
                    )
                    Image(
                        painterResource(R.drawable.airasia_icon),
                        contentDescription = stringResource(R.string.flight_icon),
                        modifier = Modifier.align(Alignment.BottomCenter).height(70.dp),
                    )
                }
                Text(
                    text = flightInfo.companyName,
                    style = AppTheme.typography.largeBold,
                    color = Color.Black,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SmallTab("Flight", flightInfo.flightID)
                SmallTab("Gate", flightInfo.gateID)
                SmallTab("Departure", flightInfo.departure.time)
                SmallTab("Travel Time", flightInfo.travelTime)
            }
            HorizontalDivider()
            Column {
                Location(
                    place = flightInfo.departure.city,
                    desc = stringResource(
                        R.string.airport_name,
                        flightInfo.departure.city,
                        flightInfo.departure.code
                    ),
                    time = flightInfo.departure.time,
                    date = flightInfo.departure.date
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
                        text = stringResource(R.string.airport_name, flightInfo.companyName, flightInfo.flightID),
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
                    place = flightInfo.arrival.city,
                    desc = stringResource(
                        R.string.airport_name,
                        flightInfo.arrival.city,
                        flightInfo.arrival.code
                    ),
                    time = flightInfo.arrival.time,
                    date = flightInfo.arrival.date
                )
            }
            HorizontalDivider()
            barcodeBitmap?.let {
                Image(
                    bitmap = it.asImageBitmap(),
                    contentDescription = stringResource(R.string.barcode),
                    modifier = Modifier.fillMaxWidth()
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
        Column (
            horizontalAlignment = Alignment.End
        ){
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

fun generateBarcode(content: String): Bitmap? {
    return try {
        val barcodeEncoder = BarcodeEncoder()
        barcodeEncoder.encodeBitmap(content, BarcodeFormat.CODE_128, 600, 150)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}