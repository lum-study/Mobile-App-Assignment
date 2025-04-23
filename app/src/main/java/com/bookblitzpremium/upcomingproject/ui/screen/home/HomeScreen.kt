package com.bookblitzpremium.upcomingproject.ui.screen.home

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TripPackage
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTripPackageViewModel
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.components.SkeletonLoader
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Composable
fun HomeScreen(navController: NavHostController) {
    val localHotelViewModel: LocalHotelViewModel = hiltViewModel()
    val hotelList = remember { localHotelViewModel.getAllHotelsPagingFlow() }.collectAsLazyPagingItems()
    val localTripPackageViewModel: LocalTripPackageViewModel = hiltViewModel()
    val tripPackageList = remember { localTripPackageViewModel.getAllTripPackagesPagingFlow() }.collectAsLazyPagingItems()

    val username = "Abu Bakar"
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    AppTheme {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                GreetingProfile(username)
                HorizontalDivider()
                TripPackageSection(
                    tripPackageList = tripPackageList,
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp),
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
        } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM) {
            val configuration = LocalConfiguration.current
            val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp
            Row(
                modifier = Modifier.background(Color.LightGray)
            ) {
                PermanentDrawerSheet(
                    drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                    drawerContainerColor = Color(0xFFE0E0E0),
                    modifier = Modifier.fillMaxWidth(if (isPortrait) .35f else .25f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        GreetingProfile(username, Color.Black)

                        HorizontalDivider()

                        NavigationDrawerItem(
                            label = { DrawerLabel(Icons.Default.Home, "Home") },
                            selected = false,
                            onClick = { /*TODO*/ },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.LightGray,
                                unselectedContainerColor = AppTheme.colorScheme.onPrimary,
                                selectedTextColor = AppTheme.colorScheme.primary,
                                unselectedTextColor = AppTheme.colorScheme.primary
                            )
                        )
                        NavigationDrawerItem(label = {
                            DrawerLabel(
                                Icons.Outlined.Search,
                                "Search"
                            )
                        },

                            selected = false, onClick = { /*TODO*/ })
                        NavigationDrawerItem(label = {
                            DrawerLabel(
                                Icons.Outlined.Schedule,
                                "Schedule"
                            )
                        },

                            selected = false, onClick = { /*TODO*/ })
                        NavigationDrawerItem(label = {
                            DrawerLabel(
                                Icons.Outlined.Person,
                                "Profile"
                            )
                        },

                            selected = false, onClick = { /*TODO*/ })
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    TripPackageSection(
                        tripPackageList = tripPackageList,
                        modifier = Modifier.weight(.5f),
                        isMobile = false,
                        isPortrait = isPortrait,
                        navController = navController
                    )
                    HorizontalDivider()
                    HotelSection(
                        hotelList = hotelList,
                        modifier = Modifier.weight(.35f),
                        isMobile = false,
                        isPortrait = isPortrait,
                        navController = navController,
                    )
                }
            }
        } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED) {
            val configuration = LocalConfiguration.current
            val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp
            Row {
                PermanentDrawerSheet(
                    drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                    drawerContainerColor = Color(0xFFE0E0E0),
                    modifier = Modifier.fillMaxWidth(if (isPortrait) .35f else .25f)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        GreetingProfile(username, Color.Black)

                        HorizontalDivider()

                        NavigationDrawerItem(
                            label = { DrawerLabel(Icons.Default.Home, "Home") },
                            selected = false,
                            onClick = { /*TODO*/ },
                            colors = NavigationDrawerItemDefaults.colors(
                                selectedContainerColor = Color.LightGray,
                                unselectedContainerColor = AppTheme.colorScheme.onPrimary,
                                selectedTextColor = AppTheme.colorScheme.primary,
                                unselectedTextColor = AppTheme.colorScheme.primary
                            )
                        )
                        NavigationDrawerItem(label = {
                            DrawerLabel(
                                Icons.Outlined.Search,
                                "Search"
                            )
                        },

                            selected = false, onClick = { /*TODO*/ })
                        NavigationDrawerItem(label = {
                            DrawerLabel(
                                Icons.Outlined.Schedule,
                                "Schedule"
                            )
                        },

                            selected = false, onClick = { /*TODO*/ })
                        NavigationDrawerItem(
                            label = {
                                DrawerLabel(
                                    Icons.Outlined.Person,
                                    "Profile"
                                )
                            },
                            selected = false, onClick = { /*TODO*/ })
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(32.dp)
                ) {
                    TripPackageSection(
                        tripPackageList = tripPackageList,
                        modifier = Modifier.weight(.5f),
                        isMobile = false,
                        isPortrait = isPortrait,
                        navController = navController
                    )
                    HorizontalDivider()
                    HotelSection(
                        hotelList = hotelList,
                        modifier = Modifier.weight(.35f),
                        isMobile = false,
                        isPortrait = isPortrait,
                        navController = navController,
                    )

                }
            }
        }
    }
}

@Composable
fun GreetingProfile(username: String, textColor: Color = Color.Gray) {
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
                text = stringResource(id = R.string.home_title, username),
                style = AppTheme.typography.largeBold,
            )
            Text(
                text = stringResource(id = R.string.home_description),
                style = AppTheme.typography.smallRegular,
                color = textColor,
            )
        }
        Image(
            painter = painterResource(id = R.drawable.green_mountain),
            contentDescription = stringResource(R.string.profile_image),
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun TripPackageSection(
    tripPackageList: LazyPagingItems<TripPackage>,
    modifier: Modifier = Modifier,
    isMobile: Boolean = true,
    isPortrait: Boolean = false,
    navController: NavHostController,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.home_package_title),
            style = AppTheme.typography.mediumSemiBold
        )
        if (isMobile) {
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(32.dp)
            ) {
                items(tripPackageList.itemCount) { index ->
                    val tripPackage = tripPackageList[index]
                    if (tripPackage != null) {
                        TripPackageCard(
                            tripPackage = tripPackage,
                            modifier = Modifier.width(250.dp),
                            onClick = {
                                navController.navigate(AppScreen.TripPackage.passData(tripPackage.id))
                            })
                    }
                }
            }
        } else {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(if (isPortrait && !isMobile) 3 else 2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tripPackageList.itemCount) { index ->
                    val tripPackage = tripPackageList[index]
                    TripPackageCard(
                        tripPackage = tripPackage,
                        modifier = Modifier.width(250.dp),
                        onClick = {
                            navController.navigate(AppScreen.TripPackage.route)
                        })
                }
            }
        }
    }
}

@Composable
fun TripPackageCard(
    tripPackage: TripPackage?,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
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
                .fillMaxWidth()
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
    navController: NavHostController
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp), modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.home_hotel_name),
            style = AppTheme.typography.mediumSemiBold
        )
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
                            navController.navigate("${AppScreen.Hotel.route}/${hotel?.id}")
                        })
                }
            }
        } else {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(if (isPortrait && !isMobile) 2 else 1),
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
                        onClick = { navController.navigate(AppScreen.Hotel.route) })
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
    onClick: () -> Unit = {}
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