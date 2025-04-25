package com.bookblitzpremium.upcomingproject.ui.screen.search

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingType
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRecentSearchViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTripPackageViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.home.HotelCard
import com.bookblitzpremium.upcomingproject.ui.screen.home.TripPackageCard
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Composable
fun SearchScreen(navController: NavHostController) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    AppTheme {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                var showEmptyListToast by remember { mutableStateOf(false) }
                var keyword by remember { mutableStateOf("") }
                val hotelViewModel: LocalHotelViewModel = hiltViewModel()
                val hotelList =
                    remember(keyword) { hotelViewModel.getByKeyword(keyword) }.collectAsLazyPagingItems()
                val maxPrice = hotelList.itemSnapshotList.items.maxOfOrNull { it.price }
                val minPrice = hotelList.itemSnapshotList.items.minOfOrNull { it.price }
                SearchBar(
                    searchText = keyword,
                    onInputChange = {
                        keyword = it
                    },
                    onFilterButtonClick = {
                        if (hotelList.itemCount > 1) {
                            navController.navigate(
                                AppScreen.Filter.passData(
                                    keyword,
                                    minPrice?.toString() ?: "0",
                                    maxPrice?.toString() ?: "0"
                                )
                            )
                        } else if (hotelList.itemCount == 0)
                            showEmptyListToast = !showEmptyListToast
                    }
                )
                if (showEmptyListToast) {
                    val context = LocalContext.current
                    LaunchedEffect(Unit) {
                        Toast.makeText(
                            context,
                            "No record found",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        showEmptyListToast = !showEmptyListToast
                    }
                }

                if (hotelList.itemCount > 0) {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(1),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(hotelList.itemCount) { index ->
                            val hotel = hotelList[index]
                            HotelCard(
                                hotel,
                                modifier = Modifier.height(250.dp),
                                onClick = {
                                    navController.navigate(
                                        AppScreen.Hotel.passData(
                                            hotel!!.id,
                                            ""
                                        )
                                    )
                                })
                        }
                    }
                } else {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        Text(
                            text = "No record found",
                            style = AppTheme.typography.largeBold,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
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
//                        SearchBar(onFilterButtonClick = {}, isMobile = false)
//                        FilterScreen(navController = rememberNavController())
                    }
                }
                FilteredResultScreen(
                    isMobile = false,
                    modifier = Modifier.padding(24.dp),
                    navController = navController
                )
            }
        }
    }
}

@Composable
fun SearchBar(
    searchText: String,
    onInputChange: (String) -> Unit,
    onFilterButtonClick: () -> Unit,
    isMobile: Boolean = true,
) {
    TextField(
        value = searchText,
        onValueChange = { onInputChange(it) },
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
                contentDescription = stringResource(R.string.search_icon),
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
                        contentDescription = stringResource(R.string.filter_icon),
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

//@Composable
//fun DefaultSearch(
//    isFiltered: Boolean = false,
//    modifier: Modifier = Modifier,
//    isMobile: Boolean = true,
//) {
//    val configuration = LocalConfiguration.current
//    val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp
//    val rowCount = if (isPortrait && !isMobile) 2 else 1
//
//    Column(
//        modifier = modifier.fillMaxSize(),
//        verticalArrangement = Arrangement.spacedBy(32.dp),
//    ) {
//        Column(
//            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)
//        ) {
//            Row(
//                horizontalArrangement = Arrangement.SpaceBetween,
//                verticalAlignment = Alignment.CenterVertically,
//                modifier = Modifier.fillMaxWidth()
//            ) {
//                if (!isFiltered) {
//                    Text(
//                        text = stringResource(R.string.last_search),
//                        style = AppTheme.typography.mediumSemiBold
//                    )
//                    Text(
//                        text = stringResource(R.string.clear_all),
//                        style = AppTheme.typography.smallRegular,
//                        color = Color.Blue,
//                    )
//                } else {
//                    Text(
//                        text = stringResource(R.string.trip_packages),
//                        style = AppTheme.typography.mediumSemiBold,
//                    )
//                }
//            }
//            LazyHorizontalGrid(
//                rows = GridCells.Fixed(rowCount),
//                verticalArrangement = Arrangement.spacedBy(32.dp),
//                horizontalArrangement = Arrangement.spacedBy(32.dp),
//                modifier = Modifier.weight(1f)
//            ) {
//                items(tripPackageList) { tripPackage ->
//                    TripPackageCard(tripPackage = null, modifier = Modifier.width(250.dp))
//                }
//            }
//        }
//        Column(
//            verticalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.weight(1f)
//        ) {
//            Text(
//                text = if (!isFiltered) stringResource(R.string.hot_package) else stringResource(R.string.hotel),
//                style = AppTheme.typography.mediumSemiBold
//            )
//            LazyHorizontalGrid(
//                rows = GridCells.Fixed(rowCount),
//                verticalArrangement = Arrangement.spacedBy(32.dp),
//                horizontalArrangement = Arrangement.spacedBy(32.dp),
//                modifier = Modifier.weight(1f)
//            ) {
//                items(hotelList) { hotel ->
//                    HotelCard(hotel = null, modifier = Modifier.width(250.dp))
//                }
//            }
//        }
//    }
//}

@Composable
fun FilteredResultScreen(
    modifier: Modifier = Modifier,
    isMobile: Boolean = true,
    navController: NavHostController,
) {
    val recentSearchViewModel: LocalRecentSearchViewModel = hiltViewModel()
    val recentSearch by recentSearchViewModel.recentSearch.collectAsState()

    val features = recentSearch?.feature?.split(",") ?: emptyList()

    val tripPackageViewModel: LocalTripPackageViewModel = hiltViewModel()
    val hotelViewModel: LocalHotelViewModel = hiltViewModel()


    val tripPackageList = remember(recentSearch) {
        tripPackageViewModel.filterTripPackage(
            input = recentSearch?.keyword ?: "",
            startPrice = recentSearch?.startPrice ?: 0.0,
            endPrice = recentSearch?.endPrice ?: 0.0,
            departure = recentSearch?.departureStation ?: "",
            arrival = recentSearch?.arrivalStation ?: "",
            startDate = recentSearch?.startDate ?: "",
            endDate = recentSearch?.endDate ?: "",
        )
    }.collectAsLazyPagingItems()

    val hotelList = remember(recentSearch) {
        hotelViewModel.filterHotel(
            input = recentSearch?.keyword ?: "",
            rating = recentSearch?.rating ?: 0.0,
            startPrice = recentSearch?.startPrice ?: 0.0,
            endPrice = recentSearch?.endPrice ?: 0.0,
            feature1 = features.getOrNull(0) ?: "",
            feature2 = features.getOrNull(1) ?: ""
        )
    }.collectAsLazyPagingItems()

    LazyVerticalGrid(
        columns = GridCells.Fixed(if (isMobile) 1 else 3),
        verticalArrangement = Arrangement.spacedBy(if (isMobile) 16.dp else 32.dp),
        horizontalArrangement = Arrangement.spacedBy(if (isMobile) 16.dp else 32.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (recentSearch?.option == BookingType.TripPackage.title) {
            items(tripPackageList.itemCount) { index ->
                val tripPackage = tripPackageList[index]
                TripPackageCard(tripPackage, modifier = Modifier.height(250.dp), onClick = {
                    navController.navigate(AppScreen.TripPackage.passData(tripPackage!!.id, ""))
                })
            }
        } else {
            items(hotelList.itemCount) { index ->
                val hotel = hotelList[index]
                HotelCard(hotel, modifier = Modifier.height(250.dp), onClick = {
                    navController.navigate(AppScreen.Hotel.passData(hotel!!.id, ""))
                })
            }
        }
    }
}
