package com.bookblitzpremium.upcomingproject.ui.screen.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingType
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.common.enums.Gender
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTripPackageViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.components.SkeletonLoader
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeScreen(navController: NavHostController) {
    val today = LocalDate.now()

    val localHotelViewModel: LocalHotelViewModel = hiltViewModel()
    val hotelList =
        remember { localHotelViewModel.getAllHotelsPagingFlow() }.collectAsLazyPagingItems()
    val localTripPackageViewModel: LocalTripPackageViewModel = hiltViewModel()
    val tripPackageList =
        remember { localTripPackageViewModel.getAllTripPackagesPagingFlow() }.collectAsLazyPagingItems().itemSnapshotList.items
            .filter { trip ->
                val startDate = LocalDate.parse(trip.startDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                startDate.isAfter(today) && trip.slots > 0
            }

    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    when (deviceType) {
        DeviceType.MobilePortrait -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GreetingProfile()
                HorizontalDivider()
                TripPackageSection(
                    tripPackageList = tripPackageList,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp),
                    isMobile = true,
                    isPortrait = true,
                    navController = navController,
                )
                HotelSection(
                    hotelList = hotelList,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp),
                    navController = navController,
                )
            }
        }

        DeviceType.TabletLandscape -> {
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    TripPackageSection(
                        tripPackageList = tripPackageList,
                        modifier = Modifier.weight(1f),
                        isMobile = false,
                        isPortrait = false,
                        navController = navController
                    )
                    HorizontalDivider()
                    HotelSection(
                        hotelList = hotelList,
                        modifier = Modifier.weight(1f),
                        isMobile = false,
                        isPortrait = false,
                        navController = navController,
                    )
                }
            }
        }

        else -> {
            val bookingType = BookingType.entries
            var selectedTabIndex by rememberSaveable { mutableStateOf(0) }
            Row {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    GreetingProfile()
                    HorizontalDivider()
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(32.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        bookingType.forEachIndexed { index, tab ->
                            OutlinedButton(
                                onClick = { selectedTabIndex = index },
                                enabled = index != selectedTabIndex,
                                modifier = Modifier.width(200.dp),
                                contentPadding = PaddingValues(8.dp),
                                colors = ButtonDefaults.outlinedButtonColors(disabledContainerColor = Color.LightGray)
                            ) {
                                Text(
                                    text = tab.title,
                                    style = AppTheme.typography.mediumSemiBold,
                                    color = Color.Black,
                                )
                            }
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                    if (selectedTabIndex == 0) {
                        TripPackageSection(
                            tripPackageList = tripPackageList,
                            modifier = Modifier.weight(1f),
                            isMobile = false,
                            isPortrait = true,
                            navController = navController
                        )
                    } else {
                        HotelSection(
                            hotelList = hotelList,
                            modifier = Modifier.weight(1f),
                            isMobile = false,
                            isPortrait = true,
                            navController = navController,
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun GreetingProfile(selectedIndex: Int = 0) {
    val userID = FirebaseAuth.getInstance().currentUser?.uid
    val localUserViewModel: LocalUserViewModel = hiltViewModel()
    var userInfo by remember { mutableStateOf<User?>(null) }
    LaunchedEffect(userID, selectedIndex) {
        if (userID != null) {
            userInfo = localUserViewModel.getUserByID(userID)
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = stringResource(id = R.string.home_title, userInfo?.name ?: ""),
                style = AppTheme.typography.largeBold,
            )
            Text(
                text = stringResource(id = R.string.home_description),
                style = AppTheme.typography.smallRegular,
                color = Color.Gray,
            )
        }
        if (userInfo?.iconImage != "") {
            Base64Image(
                base64String = userInfo?.iconImage ?: "",
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )
        } else {
            Image(
                painter = painterResource(if (userInfo!!.gender == Gender.Male.title) R.drawable.male else R.drawable.female),
                contentDescription = stringResource(R.string.profile_image),
                modifier = Modifier
                    .size(80.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun TripPackageSection(
    tripPackageList: List<TripPackage>,
    modifier: Modifier = Modifier,
    isMobile: Boolean,
    isPortrait: Boolean,
    navController: NavHostController,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.fillMaxWidth()
    ) {
        if (isMobile) {
            Text(
                text = stringResource(R.string.home_package_title),
                style = AppTheme.typography.mediumSemiBold
            )
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(tripPackageList.size) { index ->
                    val tripPackage = tripPackageList[index]
                    TripPackageCard(
                        tripPackage = tripPackage,
                        modifier = Modifier.width(250.dp),
                        onClick = {
                            navController.navigate(
                                AppScreen.TripPackage.passData(
                                    tripPackage.id,
                                    ""
                                )
                            )
                        }
                    )
                }
            }
        } else if (!isPortrait) {
            Text(
                text = stringResource(R.string.home_package_title),
                style = AppTheme.typography.mediumSemiBold
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tripPackageList.size) { index ->
                    val tripPackage = tripPackageList[index]
                    TripPackageCard(
                        tripPackage = tripPackage,
                        isMobile = isMobile,
                        modifier = Modifier.width(350.dp),
                        onClick = {
                            navController.navigate(
                                AppScreen.TripPackage.passData(
                                    tripPackage.id,
                                    ""
                                )
                            )
                        }
                    )
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tripPackageList.size) { index ->
                    val tripPackage = tripPackageList[index]
                    TripPackageCard(
                        tripPackage = tripPackage,
                        isMobile = isMobile,
                        modifier = Modifier.height(300.dp),
                        onClick = {
                            navController.navigate(
                                AppScreen.TripPackage.passData(
                                    tripPackage.id,
                                    ""
                                )
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun TripPackageCard(
    modifier: Modifier = Modifier,
    tripPackage: TripPackage?,
    isMobile: Boolean = true,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.BottomCenter,
    ) {
        if (tripPackage != null) {
            Base64Image(
                base64String = tripPackage.imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
            )
        } else {
            SkeletonLoader(modifier = Modifier.fillMaxSize())
        }

        Card(
            modifier = Modifier
                .fillMaxWidth(if (isMobile) 1f else .9f)
                .padding(8.dp),
            colors = CardDefaults.cardColors(Color(0xB3FFFFFF)),
        ) {
            if (tripPackage != null) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(.6f)
                        .padding(horizontal = 12.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = tripPackage.name,
                        style = AppTheme.typography.mediumBold,
                        color = Color.Black,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Default.AttachMoney,
                                contentDescription = stringResource(R.string.price_icon),
                                tint = Color.Gray
                            )
                            Text(
                                text = stringResource(R.string.price, tripPackage.price),
                                style = AppTheme.typography.mediumNormal,
                                color = Color.Gray,
                                textAlign = TextAlign.Start,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.LocationOn,
                                contentDescription = stringResource(R.string.location_icon),
                                tint = Color.Gray
                            )
                            Text(
                                text = tripPackage.location,
                                style = AppTheme.typography.mediumNormal,
                                color = Color.Gray,
                                textAlign = TextAlign.End,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(40.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(Color(0xFFFFC71E), Color(0xFFFF9800))
                                )
                            )
                    ) {
                        Button(
                            onClick = {
                                onClick()
                            },
                            shape = RoundedCornerShape(12.dp),
                            colors = ButtonDefaults.buttonColors(Color.Transparent),
                            modifier = Modifier.matchParentSize(),
                            contentPadding = PaddingValues()
                        ) {
                            Text(
                                text = "More Detail",
                                style = AppTheme.typography.mediumBold,
                                color = Color.Black
                            )
                        }
                    }
                }
            } else {
                SkeletonLoader(modifier = Modifier.fillMaxSize())
            }
        }
    }
}

@Composable
fun HotelSection(
    hotelList: LazyPagingItems<Hotel>,
    modifier: Modifier = Modifier,
    isMobile: Boolean = true,
    isPortrait: Boolean = false,
    isOrder : String = "false",
    navController: NavHostController,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.fillMaxWidth()
    ) {
        if (!isPortrait) {
            Text(
                text = stringResource(R.string.home_hotel_name),
                style = AppTheme.typography.mediumSemiBold
            )
        }
        if (isMobile) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(hotelList.itemCount) { index ->
                    val hotel = hotelList[index]
                    HotelCard(
                        hotel = hotel,
                        modifier = Modifier.width(200.dp),
                        onClick = {
                            navController.navigate(AppScreen.Hotel.passData(hotel!!.id, "",  isOrder))
                        })
                }
            }
        } else if (!isPortrait) {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(1),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(hotelList.itemCount) { index ->
                    val hotel = hotelList[index]
                    HotelCard(
                        hotel = hotel,
                        modifier = Modifier
                            .width(250.dp)
                            .height(150.dp),
                        onClick = {
                            navController.navigate(
                                AppScreen.Hotel.passData(
                                    hotel!!.id,
                                    "",
                                    isOrder
                                )
                            )
                        })
                }
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(36.dp),
                verticalArrangement = Arrangement.spacedBy(36.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(hotelList.itemCount) { index ->
                    val hotel = hotelList[index]
                    HotelCard(
                        hotel = hotel,
                        modifier = Modifier.height(300.dp),
                        onClick = {
                            navController.navigate(AppScreen.Hotel.passData(hotel!!.id, "", isOrder))
                        })
                }
            }
        }
    }
}

@Composable
fun DrawerLabel(imageVector: ImageVector, contentDesc: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(start = 8.dp)
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDesc,
        )
        Text(
            text = contentDesc,
            style = AppTheme.typography.mediumNormal,
            modifier = Modifier.padding(start = 16.dp)
        )
    }
}

@Composable
fun HotelCard(
    hotel: Hotel?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier
            .wrapContentSize(Alignment.TopStart)
            .clickable { onClick() },
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.White,
            disabledContentColor = Color.White
        ),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8E8E8))
        ) {
            if (hotel != null) {
                Box(
                    modifier = Modifier.weight(1f)
                ) {
                    UrlImage(imageUrl = hotel.imageUrl, modifier = Modifier.fillMaxSize())
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 12.dp, top = 4.dp, start = 24.dp, end = 24.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = hotel.name,
                        modifier = Modifier.fillMaxWidth(),
                        style = AppTheme.typography.mediumBold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = stringResource(R.string.price, hotel.price),
                        modifier = Modifier.fillMaxWidth(),
                        style = AppTheme.typography.smallRegular,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            } else {
                SkeletonLoader(modifier = Modifier.fillMaxSize())
            }
        }
    }
}