package com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTripPackageViewModel
import com.bookblitzpremium.upcomingproject.data.model.TripPackageInformation
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.components.SkeletonLoader
import com.bookblitzpremium.upcomingproject.ui.screen.travel.TravelHeaderTable
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun TripPackageScreen(navController: NavController, tripPackageID: String) {
    val localTripPackageViewModel: LocalTripPackageViewModel = hiltViewModel()
    var selectedTripPackage: TripPackageInformation? by remember { mutableStateOf(null) }

    LaunchedEffect(Unit) {
        selectedTripPackage = localTripPackageViewModel.getTripPackageInformation(tripPackageID)
    }
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    AppTheme {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (selectedTripPackage != null) {
                        Base64Image(
                            base64String = selectedTripPackage!!.imageUrl,
                            modifier = Modifier
                                .height(200.dp)
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillBounds,
                        )
                        Text(
                            text = selectedTripPackage!!.name,
                            style = AppTheme.typography.largeSemiBold,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Text(
                            text = stringResource(R.string.price, selectedTripPackage!!.price),
                            style = AppTheme.typography.mediumSemiBold,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Text(
                            text = selectedTripPackage!!.description,
                            style = AppTheme.typography.smallRegular,
                            color = Color.Gray,
                            modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                        )
                        Text(
                            text = stringResource(
                                R.string.available_slot,
                                selectedTripPackage!!.slots
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

                        val startDate = LocalDate.parse(selectedTripPackage!!.startDate)
                        val endDate =
                            startDate.plusDays(selectedTripPackage!!.scheduleDay.toLong() - 1)
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
                                        selectedTripPackage!!.id,
                                        selectedTripPackage!!.startDate
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
                                selectedTripPackage!!.flightDepart,
                                selectedTripPackage!!.flightArrival
                            ),
                            rotateDeg = 45f,
                            onRowClick = { navController.navigate(AppScreen.Flight.passData(selectedTripPackage!!.flightID, "")) }
                        )
                        Divider(
                            color = Color.Gray,
                            thickness = 1.dp,
                        )
                        InformationData(
                            imageVector = Icons.Outlined.HomeWork,
                            title = stringResource(R.string.hotel),
                            description = selectedTripPackage!!.hotelName,
                            onRowClick = { navController.navigate(AppScreen.Hotel.passData(selectedTripPackage!!.hotelID, selectedTripPackage!!.id)) }
                        )

                        Button(
                            onClick = {},
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = stringResource(R.string.apply_button)
                            )
                        }
                    }
                    else{
                        SkeletonLoader(modifier = Modifier.height(400.dp).fillMaxWidth().padding(horizontal = 8.dp).clip(
                            RoundedCornerShape(24.dp)
                        ))
                    }
                }
            }
        } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED) {
            val configuration = LocalConfiguration.current
            val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp
            TravelHeaderTable()
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