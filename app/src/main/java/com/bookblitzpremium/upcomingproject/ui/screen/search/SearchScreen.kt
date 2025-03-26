package com.bookblitzpremium.upcomingproject.ui.screen.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.model.Hotel
import com.bookblitzpremium.upcomingproject.model.TripPackage
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ui.screen.home.HotelCard
import com.bookblitzpremium.upcomingproject.ui.screen.home.TripPackageCard
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme


//@Preview(showBackground = true, widthDp = 360, heightDp = 806)
@Composable
fun SearchScreen() {
    val tripPackageList: List<TripPackage> = listOf(
        TripPackage(R.drawable.green_mountain, "Trip to Bali", "Enjoy a relaxing vacation"),
        TripPackage(R.drawable.blue_mountain, "Trip to Paris", "Explore the city of love"),
        TripPackage(R.drawable.image, "Trip to Tokyo", "Experience Japan's culture")
    )
    val hotelList: List<Hotel> = listOf(
        Hotel(R.drawable.green_mountain, "Test", "Enjoy a relaxing vacation"),
        Hotel(R.drawable.blue_mountain, "Test 2", "Explore the city of love"),
        Hotel(R.drawable.image, "Test 3", "Experience Japan's culture")
    )
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    var isNavigating by remember { mutableStateOf(false) }

    LaunchedEffect(isNavigating) {
        if (isNavigating) {
            kotlinx.coroutines.delay(2000)
            isNavigating = false
        }
    }

    AppTheme {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                SearchBar(onFilterButtonClick = {
//                    if (!isNavigating) {
//                        isNavigating = true
//                        navController.navigate(SearchPath.Filter.name)
//                    }
                })
//                DefaultSearch(
//                    isFiltered = false, hotelList = hotelList, tripPackageList = tripPackageList
//                )
                FilteredResultScreen(isMobile = true)
            }
        } else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM) {
            val configuration = LocalConfiguration.current
            val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp
            val fillMaxWidth = if (isPortrait) .35f else .25f
            Row {
                PermanentDrawerSheet(
                    drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                    drawerContainerColor = Color(0xFFE0E0E0),
                    modifier = Modifier.fillMaxWidth(fillMaxWidth)
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.padding(16.dp)
                    ) {
                        SearchBar(onFilterButtonClick = {}, isMobile = false)
//                        FilterScreen(navController = rememberNavController())
                    }
                }
                FilteredResultScreen(isMobile = false, modifier = Modifier.padding(24.dp))
            }
        }
    }
}

@Composable
fun SearchBar(
    onFilterButtonClick: () -> Unit,
    isMobile: Boolean = true,
) {
    var searchText by remember { mutableStateOf("") }
    TextField(
        value = searchText,
        onValueChange = { searchText = it },
        placeholder = {
            if (searchText.isEmpty()) {
                Text(
                    text = stringResource(R.string.search_placeholder),
                    style = AppTheme.typography.mediumNormal,
                )
            }
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = Color.Blue,
            )
        },
        trailingIcon = {
            if (isMobile) {
                IconButton(
                    onClick = {
                        onFilterButtonClick()
                    }, modifier = Modifier.fillMaxHeight()
                ) {
                    Icon(
                        imageVector = Icons.Filled.Tune,
                        contentDescription = "Filter Icon",
                    )
                }
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .shadow(
                elevation = 1.dp,
                shape = RoundedCornerShape(28.dp),
                spotColor = Color.LightGray,
                ambientColor = Color.Black
            ),
    )
}

@Composable
fun DefaultSearch(
    isFiltered: Boolean = false,
    hotelList: List<Hotel>,
    tripPackageList: List<TripPackage>,
    modifier: Modifier = Modifier,
    isMobile: Boolean = true,
) {
    val configuration = LocalConfiguration.current
    val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp
    val rowCount = if (isPortrait && !isMobile) 2 else 1

    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(32.dp),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (!isFiltered) {
                    Text(
                        text = stringResource(R.string.last_search),
                        style = AppTheme.typography.mediumSemiBold
                    )
                    Text(
                        text = stringResource(R.string.clear_all),
                        style = AppTheme.typography.smallRegular,
                        color = Color.Blue,
                    )
                } else {
                    Text(
                        text = stringResource(R.string.trip_packages),
                        style = AppTheme.typography.mediumSemiBold,
                    )
                }
            }
            LazyHorizontalGrid(
                rows = GridCells.Fixed(rowCount),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(tripPackageList) { tripPackage ->
                    TripPackageCard(tripPackage = tripPackage, modifier = Modifier.width(250.dp),  )
                }
            }
        }
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)
        ) {
            Text(
                text = if (!isFiltered) stringResource(R.string.hot_package) else stringResource(R.string.hotel),
                style = AppTheme.typography.mediumSemiBold
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(rowCount),
                verticalArrangement = Arrangement.spacedBy(32.dp),
                horizontalArrangement = Arrangement.spacedBy(32.dp),
                modifier = Modifier.weight(1f)
            ) {
                items(hotelList) { hotel ->
                    HotelCard(hotel = hotel, modifier = Modifier.width(250.dp))
                }
            }
        }
    }
}

@Composable
fun FilteredResultScreen(
    modifier: Modifier = Modifier,
    navController: NavController? = null,
    hotelList: List<Hotel>? = listOf(
        Hotel(R.drawable.green_mountain, "Test", "Enjoy a relaxing vacation"),
        Hotel(R.drawable.blue_mountain, "Test 2", "Explore the city of love"),
        Hotel(R.drawable.image, "Test 3", "Experience Japan's culture"),
        Hotel(R.drawable.image, "Test 3", "Experience Japan's culture"),
        Hotel(R.drawable.image, "Test 3", "Experience Japan's culture"),
        Hotel(R.drawable.image, "Test 3", "Experience Japan's culture"),
    ),
    tripPackageList: List<TripPackage>? = listOf(
        TripPackage(R.drawable.green_mountain, "Test", "Enjoy a relaxing vacation"),
    ),
    isMobile: Boolean = true,
) {
    if (!tripPackageList.isNullOrEmpty() && !hotelList.isNullOrEmpty()) {
        DefaultSearch(
            isFiltered = true,
            tripPackageList = tripPackageList,
            hotelList = hotelList,
            modifier = if (isMobile) Modifier else Modifier.padding(24.dp),
            isMobile = isMobile,
        )
    } else {
        LazyVerticalGrid(
            columns = GridCells.Fixed(if (isMobile) 1 else 3),
            verticalArrangement = Arrangement.spacedBy(if (isMobile) 16.dp else 32.dp),
            horizontalArrangement = Arrangement.spacedBy(if (isMobile) 16.dp else 32.dp),
            modifier = modifier.fillMaxSize()
        ) {
            if (!tripPackageList.isNullOrEmpty()) {
                items(tripPackageList) { tripPackage ->
                    TripPackageCard(tripPackage, modifier = Modifier.height(250.dp))
                }
            } else if (!hotelList.isNullOrEmpty()) {
                items(hotelList) { hotel ->
                    HotelCard(hotel, modifier = Modifier.height(250.dp))
                }
            }
        }
    }
}
