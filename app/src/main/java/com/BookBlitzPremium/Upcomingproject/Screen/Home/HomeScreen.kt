package com.BookBlitzPremium.Upcomingproject.Screen.Home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material.icons.outlined.Search
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.BookBlitzPremium.Upcomingproject.Model.Hotel
import com.BookBlitzPremium.Upcomingproject.Model.TripPackage
import com.BookBlitzPremium.Upcomingproject.R
import com.BookBlitzPremium.Upcomingproject.ui.theme.AppTheme

//900,1440
//360,806
@Preview(showBackground = true, widthDp = 1440, heightDp = 900)
@Composable
fun HomeScreen() {
    val username = "Abu Bakar"
    val tripPackageList: List<TripPackage> = listOf(
        TripPackage(R.drawable.green_mountain, "Trip to Bali", "Enjoy a relaxing vacation"),
        TripPackage(R.drawable.green_mountain, "Trip to Bali", "Enjoy a relaxing vacation"),
        TripPackage(R.drawable.green_mountain, "Trip to Bali", "Enjoy a relaxing vacation"),
        TripPackage(R.drawable.green_mountain, "Trip to Bali", "Enjoy a relaxing vacation"),
        TripPackage(R.drawable.green_mountain, "Trip to Bali", "Enjoy a relaxing vacation"),
        TripPackage(R.drawable.blue_mountain, "Trip to Paris", "Explore the city of love"),
        TripPackage(R.drawable.image, "Trip to Tokyo", "Experience Japan's culture")
    )
    val hotelList: List<Hotel> = listOf(
        Hotel(R.drawable.green_mountain, "Arab", "Dubai"),
        Hotel(R.drawable.blue_mountain, "Test 2", "Explore the city of love"),
        Hotel(
            R.drawable.image,
            "Test 3",
            "Experience Japan's culture Experience Japan's culture Experience Japan's culture"
        )
    )
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass

    AppTheme {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                GreetingProfile(username)
                HorizontalDivider()
                TripPackageSection(
                    tripPackageList = tripPackageList, modifier = Modifier.weight(1f)
                )
                HotelSection(
                    hotelList = hotelList, modifier = Modifier
                        .weight(1f)
                        .padding(top = 8.dp)
                )
            }
        } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED) {
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
                                selectedContainerColor = AppTheme.colorScheme.brown,
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
                        tripPackageList,
                        modifier = Modifier.weight(.5f),
                        isMobile = false,
                        isPortrait = isPortrait
                    )
                    HorizontalDivider()
                    HotelSection(
                        hotelList,
                        modifier = Modifier.weight(.35f),
                        isMobile = false,
                        isPortrait = isPortrait
                    )
                }
            }
        } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM) {
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
                                selectedContainerColor = AppTheme.colorScheme.brown,
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
                        tripPackageList,
                        modifier = Modifier.weight(.5f),
                        isMobile = false,
                        isPortrait = isPortrait
                    )
                    HorizontalDivider()
                    HotelSection(
                        hotelList,
                        modifier = Modifier.weight(.35f),
                        isMobile = false,
                        isPortrait = isPortrait
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
            contentDescription = "Mountain landscape background",
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape),
            contentScale = ContentScale.FillBounds
        )
    }
}

@Composable
fun TripPackageSection(
    tripPackageList: List<TripPackage>,
    modifier: Modifier = Modifier,
    isMobile: Boolean = true,
    isPortrait: Boolean = false
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
                items(tripPackageList) { tripPackage ->
                    TripPackageCard(tripPackage = tripPackage, modifier = Modifier.width(250.dp))
                }
            }
        } else {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(if (isPortrait && !isMobile) 3 else 2),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(tripPackageList) { tripPackage ->
                    TripPackageCard(tripPackage = tripPackage, modifier.width(300.dp))
                }
            }
        }
    }
}

@Composable
fun TripPackageCard2(
    tripPackage: TripPackage,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier
    ) {
        Image(
//            painter = rememberImagePainter(
//                data = tripPackage.imageResource
//            ),
            painterResource(id = tripPackage.imageResource),
            contentDescription = tripPackage.packageTitle,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        )
        Text(
            text = tripPackage.packageTitle
        )
    }
}

@Composable
fun TripPackageCard(
    tripPackage: TripPackage,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier.clip(RoundedCornerShape(36.dp)), contentAlignment = Alignment.BottomCenter) {
        Image(
//            painter = rememberImagePainter(
//                data = tripPackage.imageResource
//            ),
            painterResource(id = tripPackage.imageResource),
            contentDescription = tripPackage.packageTitle,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = tripPackage.packageTitle,
                modifier = Modifier
                    .fillMaxWidth(),
                style = AppTheme.typography.mediumBold,
                color = Color.White,
            )
            Text(
                text = tripPackage.packageDesc,
                modifier = Modifier
                    .fillMaxWidth(),
                style = AppTheme.typography.smallRegular,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                color = Color.White,
            )
        }
    }
}

@Composable
fun HotelSection(
    hotelList: List<Hotel>,
    modifier: Modifier = Modifier,
    isMobile: Boolean = true,
    isPortrait: Boolean = false
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
                items(hotelList) { hotel ->
                    HotelCard(hotel = hotel, modifier = Modifier.width(150.dp))
                }
            }
        } else {
            LazyHorizontalGrid(
                rows = GridCells.Fixed(if (isPortrait && !isMobile) 2 else 1),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(hotelList) { hotel ->
                    HotelCard(
                        hotel = hotel, modifier = Modifier
                            .width(250.dp)
                            .height(150.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun HotelCard2(
    hotel: Hotel,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        modifier = modifier.wrapContentSize(Alignment.TopStart)
    ) {
        Image(
//            painter = rememberImagePainter(
//                data = tripPackage.imageResource
//            ),
            painterResource(id = hotel.imageResource),
            contentDescription = hotel.hotelName,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Text(
            text = hotel.hotelName
        )
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
    hotel: Hotel,
    modifier: Modifier = Modifier,
) {
    Card(
        shape = RoundedCornerShape(32.dp),
        modifier = modifier.wrapContentSize(Alignment.TopStart),
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
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
//            painter = rememberImagePainter(
//                data = tripPackage.imageResource
//            ),
                painterResource(id = hotel.imageResource),
                contentDescription = hotel.hotelName,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(.2f)
                    .padding(vertical = 4.dp, horizontal = 24.dp)
            ) {
                Text(
                    text = hotel.hotelName,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    style = AppTheme.typography.mediumBold
                )
                Text(
                    text = hotel.description,
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    style = AppTheme.typography.smallRegular,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

        }
    }
}