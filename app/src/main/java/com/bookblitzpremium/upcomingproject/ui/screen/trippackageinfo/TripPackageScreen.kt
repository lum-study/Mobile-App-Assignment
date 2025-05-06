package com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.bookblitzpremium.upcomingproject.HotelBookingScreenLayout
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTripPackageViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteTripPackageViewModel
import com.bookblitzpremium.upcomingproject.data.model.TripPackageInformation
import com.bookblitzpremium.upcomingproject.model.TripPackageTabs
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.components.SkeletonLoader
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TripPackageScreen(navController: NavHostController, tripPackageID: String, bookingID: String) {
    val localTripPackageViewModel: LocalTripPackageViewModel = hiltViewModel()
    val remoteTripPackageViewModel: RemoteTripPackageViewModel = hiltViewModel()

    var selectedTripPackage: TripPackageInformation? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        val tripPackage = remoteTripPackageViewModel.getPackageByID(tripPackageID)
        if (tripPackage != null) {
            localTripPackageViewModel.addOrUpdateTrip(tripPackage)
            selectedTripPackage = localTripPackageViewModel.getTripPackageInformation(tripPackageID)
        }
    }
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    AppTheme {
        when (deviceType) {
            DeviceType.MobilePortrait -> {
                TripPackageInformationPhoneLayout(
                    navController = navController,
                    selectedTripPackage = selectedTripPackage,
                    bookingID = bookingID
                )
            }

            DeviceType.TabletLandscape -> {
                TripPackageInformationTabletLayout(
                    navController = navController,
                    selectedTripPackage = selectedTripPackage,
                    bookingID = bookingID,
                    isPortrait = false
                )
            }

            else -> {
                TripPackageInformationTabletLayout(
                    navController = navController,
                    selectedTripPackage = selectedTripPackage,
                    bookingID = bookingID,
                    isPortrait = true
                )
            }
        }
    }
}

@Composable
fun TripPackageInformationPhoneLayout(
    navController: NavHostController,
    selectedTripPackage: TripPackageInformation?,
    bookingID: String,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (selectedTripPackage != null) {
                Base64Image(
                    base64String = selectedTripPackage.imageUrl,
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth(),
                    contentScale = ContentScale.FillBounds,
                )
                Text(
                    text = selectedTripPackage.name,
                    style = AppTheme.typography.largeSemiBold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Text(
                    text = stringResource(R.string.price, selectedTripPackage.price),
                    style = AppTheme.typography.mediumSemiBold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Text(
                    text = selectedTripPackage.description,
                    style = AppTheme.typography.smallRegular,
                    color = Color.Gray,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
                Text(
                    text = stringResource(
                        R.string.available_slot,
                        selectedTripPackage.slots
                    ),
                    style = AppTheme.typography.mediumSemiBold,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                )
            } else {
                SkeletonLoader(
                    modifier = Modifier
                        .height(200.dp)
                        .fillMaxWidth()
                )
                SkeletonLoader(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .height(20.dp)
                )
                SkeletonLoader(
                    modifier = Modifier
                        .padding(start = 16.dp, end = 16.dp)
                        .fillMaxWidth()
                        .height(20.dp)
                )
            }
        }
        Column(
            modifier = Modifier
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            if (selectedTripPackage != null) {
                Text(
                    text = stringResource(R.string.information),
                    style = AppTheme.typography.mediumSemiBold,
                )

                val startDate = LocalDate.parse(selectedTripPackage.startDate)
                val endDate =
                    startDate.plusDays(selectedTripPackage.scheduleDay.toLong() - 1)
                val formatter = DateTimeFormatter.ofPattern("d MMM yyyy", Locale.ENGLISH)

                InformationData(
                    imageVector = Icons.Outlined.Task,
                    title = stringResource(R.string.schedule),
                    description = stringResource(
                        R.string.from_to,
                        startDate.format(formatter).toString(),
                        endDate.format(formatter).toString()
                    ),
                    onRowClick = {
                        navController.navigate(
                            AppScreen.Schedule.passData(
                                selectedTripPackage.id,
                                selectedTripPackage.startDate
                            )
                        )
                    }
                )
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                InformationData(
                    imageVector = Icons.Outlined.Flight,
                    title = stringResource(R.string.flight),
                    description = stringResource(
                        R.string.from_to,
                        selectedTripPackage.flightDepart,
                        selectedTripPackage.flightArrival
                    ),
                    rotateDeg = 45f,
                    onRowClick = {
                        navController.navigate(
                            AppScreen.Flight.passData(
                                selectedTripPackage.flightID,
                                bookingID,
                            )
                        )
                    }
                )
                Divider(
                    color = Color.Gray,
                    thickness = 1.dp,
                )
                InformationData(
                    imageVector = Icons.Outlined.HomeWork,
                    title = stringResource(R.string.hotel),
                    description = selectedTripPackage.hotelName,
                    onRowClick = {
                        navController.navigate(
                            AppScreen.Hotel.passData(
                                selectedTripPackage.hotelID,
                                selectedTripPackage.id
                            )
                        )
                    }
                )

                if (bookingID == "") {
                    Button(
                        onClick = {
                            navController.navigate(
                                AppScreen.TripPackageBooking.passData(
                                    selectedTripPackage.id,
                                    selectedTripPackage.name,
                                    selectedTripPackage.price.toString(),
                                    selectedTripPackage.slots.toString(),
                                )
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.booking_button),
                            style = AppTheme.typography.mediumBold
                        )
                    }
                }
            } else {
                SkeletonLoader(
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .clip(
                            RoundedCornerShape(24.dp)
                        )
                )
            }
        }
    }
}

@Composable
fun TripPackageInformationTabletLayout(
    navController: NavHostController,
    selectedTripPackage: TripPackageInformation?,
    bookingID: String,
    isPortrait: Boolean,
) {
    if (selectedTripPackage != null) {
        var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

        // List of tab items
        val tabs: List<TripPackageTabs> = listOf(
            TripPackageTabs(
                title = "Schedule",
                icon = Icons.Outlined.Task,
                screen = {
                    ScheduleScreen(
                        tripPackageID = selectedTripPackage.id,
                        startDate = selectedTripPackage.startDate,
                        isTablet = true,
                    )
                }
            ),
            TripPackageTabs(
                title = "Flight",
                icon = Icons.Outlined.Flight,
                screen = {
                    FlightScreen(
                        flightID = selectedTripPackage.flightID,
                        bookingID = bookingID
                    )
                }
            ),
            TripPackageTabs(
                title = "Hotel",
                icon = Icons.Outlined.HomeWork,
                screen = {
//                    HotelBookingScreenLayout(
//                        navController = navController,
//                        hotelID = selectedTripPackage.hotelID,
//                        tripPackageID = selectedTripPackage.id,
//                    )
                }
            ),
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 8.dp, horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(if (isPortrait) 32.dp else 8.dp),
        ) {
            if (!isPortrait) {
                Row(
                    modifier = Modifier.weight(1f),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(0.5f)
                            .background(Color.White),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Base64Image(
                            base64String = selectedTripPackage.imageUrl,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .fillMaxWidth(.8f)
                                .clip(RoundedCornerShape(12.dp))
                                .height(400.dp),
                        )
                        Column(
                            modifier = Modifier.fillMaxWidth(.8f),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Text(
                                text = selectedTripPackage.name,
                                style = AppTheme.typography.largeSemiBold,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                            Text(
                                text = stringResource(R.string.price, selectedTripPackage.price),
                                style = AppTheme.typography.mediumSemiBold,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                            Text(
                                text = selectedTripPackage.description,
                                style = AppTheme.typography.smallRegular,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                            Text(
                                text = stringResource(
                                    R.string.available_slot,
                                    selectedTripPackage.slots
                                ),
                                style = AppTheme.typography.mediumSemiBold,
                                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                            )
                        }
                    }

                    VerticalDivider()

                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .background(Color.White, RoundedCornerShape(32.dp)),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp),
                            horizontalArrangement = Arrangement.spacedBy(24.dp)
                        ) {
                            tabs.forEachIndexed { index, tab ->
                                OutlinedButton(
                                    onClick = { selectedTabIndex = index },
                                    enabled = index != selectedTabIndex,
                                    modifier = Modifier
                                        .weight(1f)
                                        .height(48.dp),
                                    contentPadding = PaddingValues(8.dp),
                                    colors = ButtonDefaults.outlinedButtonColors(
                                        disabledContainerColor = Color.LightGray
                                    )
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = tab.title,
                                            style = AppTheme.typography.mediumSemiBold,
                                            color = Color.Black,
                                            modifier = Modifier.padding(end = 12.dp)
                                        )
                                        Icon(
                                            imageVector = tab.icon,
                                            contentDescription = tab.title,
                                        )
                                    }
                                }
                            }
                        }
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .clip(RoundedCornerShape(32.dp)),
                        ) {
                            tabs[selectedTabIndex].screen()
                        }
                    }
                }
            } else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Base64Image(
                        base64String = selectedTripPackage.imageUrl,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(400.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .height(300.dp),
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(.8f),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = selectedTripPackage.name,
                            style = AppTheme.typography.largeSemiBold,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Text(
                            text = stringResource(R.string.price, selectedTripPackage.price),
                            style = AppTheme.typography.mediumSemiBold,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Text(
                            text = selectedTripPackage.description,
                            style = AppTheme.typography.smallRegular,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Text(
                            text = stringResource(
                                R.string.available_slot,
                                selectedTripPackage.slots
                            ),
                            style = AppTheme.typography.mediumSemiBold,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                    }
                }
                HorizontalDivider()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    tabs.forEachIndexed { index, tab ->
                        OutlinedButton(
                            onClick = { selectedTabIndex = index },
                            enabled = index != selectedTabIndex,
                            modifier = Modifier
                                .weight(1f)
                                .height(48.dp),
                            contentPadding = PaddingValues(8.dp),
                            colors = ButtonDefaults.outlinedButtonColors(
                                disabledContainerColor = Color.LightGray
                            )
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Center,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = tab.title,
                                    style = AppTheme.typography.mediumSemiBold,
                                    color = Color.Black,
                                    modifier = Modifier.padding(end = 12.dp)
                                )
                                Icon(
                                    imageVector = tab.icon,
                                    contentDescription = tab.title,
                                )
                            }
                        }
                    }
                }
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(.8f)
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .clip(RoundedCornerShape(32.dp)),
                ) {
                    tabs[selectedTabIndex].screen()
                }
            }
            if (bookingID == "") {
                Button(
                    shape = RoundedCornerShape(12.dp),
                    onClick = {
                        navController.navigate(
                            AppScreen.TripPackageBooking.passData(
                                selectedTripPackage.id,
                                selectedTripPackage.name,
                                selectedTripPackage.price.toString(),
                                selectedTripPackage.slots.toString(),
                            )
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    contentPadding = PaddingValues(),
                ) {
                    Text(
                        text = stringResource(R.string.booking_button),
                        style = AppTheme.typography.mediumBold
                    )
                }
            }
        }
    }
}

@Composable
fun InformationData(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    title: String,
    description: String = "",
    rotateDeg: Float = 0f,
    onRowClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onRowClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White)
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(R.string.back_button),
                modifier = Modifier
                    .rotate(rotateDeg)
                    .size(32.dp),
                tint = Color.Black
            )
        }
        if (description.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.mediumSemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description,
                    style = AppTheme.typography.smallRegular,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Text(
                text = title,
                style = AppTheme.typography.mediumNormal,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = stringResource(R.string.next_button),
            tint = Color.LightGray,
        )
    }
}

@Composable
fun TripPackageHotelTabScreen(
    hotelID: String,
    localHotelViewModel: LocalHotelViewModel = hiltViewModel(),
) {
    LaunchedEffect(hotelID) {
        localHotelViewModel.getHotelByID(hotelID)
    }
    val hotel by localHotelViewModel.selectedHotel.collectAsState()
    if (hotel != null) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            UrlImage(
                imageUrl = hotel!!.imageUrl, modifier = Modifier
                    .width(400.dp)
                    .height(300.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    hotel!!.name,
                    style = AppTheme.typography.largeBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Gray,
                    )
                    Text(
                        hotel!!.address,
                        style = AppTheme.typography.mediumNormal
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Rating",
                        tint = Color(0xFFFFD700)
                    )
                    Text(
                        hotel!!.rating.toString(),
                        style = AppTheme.typography.mediumNormal
                    )
                }
            }
        }
    }
}