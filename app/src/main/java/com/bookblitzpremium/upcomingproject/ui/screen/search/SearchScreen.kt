package com.bookblitzpremium.upcomingproject.ui.screen.search

import android.widget.Toast
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingType
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType
import com.bookblitzpremium.upcomingproject.data.businessviewmodel.FilterViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRecentSearchViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTripPackageViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.home.HotelCard
import com.bookblitzpremium.upcomingproject.ui.screen.home.TripPackageCard
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.bookblitzpremium.upcomingproject.ui.utility.getDeviceType

@Composable
fun SearchScreen(navController: NavHostController) {
    val filterViewModel: FilterViewModel = hiltViewModel()

    val recentSearchViewModel: LocalRecentSearchViewModel = hiltViewModel()
    val recentSearch by recentSearchViewModel.recentSearch.collectAsState()
    var isRecentSearchClear by remember { mutableStateOf(false) }
    LaunchedEffect(recentSearch) {
        isRecentSearchClear = recentSearch == null
    }
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)

    var showEmptyListToast by rememberSaveable { mutableStateOf(false) }
    var keyword by rememberSaveable { mutableStateOf("") }
    val hotelViewModel: LocalHotelViewModel = hiltViewModel()
    val hotelList =
        remember(keyword) { hotelViewModel.getByKeyword(keyword) }.collectAsLazyPagingItems()
    val maxPrice = hotelList.itemSnapshotList.items.maxOfOrNull { it.price }
    val minPrice = hotelList.itemSnapshotList.items.minOfOrNull { it.price }
    filterViewModel.updateStartPrice(minPrice?.toFloat() ?: 0f)
    filterViewModel.updateEndPrice(maxPrice?.toFloat() ?: 1240f)

    AppTheme {
        when (deviceType) {
            DeviceType.MobilePortrait -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
                ) {
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
                    if (hotelList.itemCount > 0 && (isRecentSearchClear || keyword.isNotEmpty())) {
                        Spacer(modifier = Modifier.height(16.dp))
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
                    } else if (!isRecentSearchClear) {
                        Spacer(modifier = Modifier.height(8.dp))
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 24.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = "Clear",
                                style = AppTheme.typography.mediumNormal.copy(textDecoration = TextDecoration.Underline),
                                textAlign = TextAlign.End,
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    isRecentSearchClear = true
                                    recentSearchViewModel.deleteAll()
                                },
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        FilteredResultScreen(navController = navController)
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
            }

            DeviceType.TabletLandscape -> {
                Row {
                    PermanentDrawerSheet(
                        drawerShape = RoundedCornerShape(topEnd = 24.dp, bottomEnd = 24.dp),
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            SearchBar(
                                onFilterButtonClick = {},
                                isMobile = false,
                                searchText = keyword,
                                onInputChange = { keyword = it }
                            )
                            FilterScreen(
                                navController = navController,
                                keyword = keyword,
                                minPrice = minPrice?.toString() ?: "27.00",
                                maxPrice = maxPrice?.toString() ?: "1240.00",
                                isMobile = false,
                                filterViewModel = filterViewModel,
                                onApplyClick = {
                                    filterViewModel.clearFilters()
                                    keyword = ""
                                },
                            )
                        }
                    }
                    if (!isRecentSearchClear && keyword.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp, end = 24.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = "Clear",
                                style = AppTheme.typography.mediumNormal.copy(textDecoration = TextDecoration.Underline),
                                textAlign = TextAlign.End,
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    isRecentSearchClear = true
                                    recentSearchViewModel.deleteAll()
                                },
                            )
                        }
                        FilteredResultScreen(navController = navController)
                    }
                }
                    else if (hotelList.itemCount > 0 || keyword.isNotEmpty()) {
                        Spacer(modifier = Modifier.height(16.dp))
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 16.dp, end = 16.dp)
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
                                    }
                                )
                            }
                        }
                    } else if (hotelList.itemCount == 0 && keyword.isNotEmpty()) {
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
            }

            else -> {
                var isFilterOpened by rememberSaveable { mutableStateOf(false) }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 32.dp, end = 32.dp, bottom = 16.dp, start = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    SearchBar(
                        searchText = keyword,
                        onInputChange = {
                            keyword = it
                        },
                        onFilterButtonClick = {
                            if (hotelList.itemCount > 1) {
                                isFilterOpened = !isFilterOpened
                            } else
                                showEmptyListToast = !showEmptyListToast
                        },
                    )

                    if (isFilterOpened) {
                        FilterScreen(
                            navController = navController,
                            keyword = keyword,
                            minPrice = minPrice?.toString() ?: "27.00",
                            maxPrice = maxPrice?.toString() ?: "1240.00",
                            isMobile = false,
                            onApplyClick = {
                                isFilterOpened = !isFilterOpened
                                filterViewModel.clearFilters()
                                keyword = ""
                            },
                            filterViewModel = filterViewModel,
                        )
                    }
                    if (showEmptyListToast) {
                        val context = LocalContext.current
                        LaunchedEffect(Unit) {
                            Toast.makeText(
                                context,
                                "Filter not available",
                                Toast.LENGTH_LONG
                            )
                                .show()
                            showEmptyListToast = !showEmptyListToast
                        }
                    }
                    if (!isRecentSearchClear && keyword.isEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 24.dp),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Text(
                                text = "Clear",
                                style = AppTheme.typography.mediumNormal.copy(textDecoration = TextDecoration.Underline),
                                textAlign = TextAlign.End,
                                color = Color.Blue,
                                modifier = Modifier.clickable {
                                    isRecentSearchClear = true
                                    recentSearchViewModel.deleteAll()
                                },
                            )
                        }
                        FilteredResultScreen(navController = navController)
                    }
                    else if (hotelList.itemCount > 0 && keyword.isNotEmpty()) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(2),
                            verticalArrangement = Arrangement.spacedBy(32.dp),
                            horizontalArrangement = Arrangement.spacedBy(32.dp),
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
                    } else if (hotelList.itemCount == 0 && keyword.isNotEmpty()) {
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

@Composable
fun FilteredResultScreen(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val configuration = LocalConfiguration.current
    val deviceType = getDeviceType(windowSizeClass, configuration)
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
            feature1 = features.getOrNull(0)?.trim() ?: "",
            feature2 = features.getOrNull(1)?.trim() ?: ""
        )
    }.collectAsLazyPagingItems()

    if ((recentSearch?.option == BookingType.Hotel.title && hotelList.itemCount == 0) ||
        (recentSearch?.option == BookingType.TripPackage.title && tripPackageList.itemCount == 0)
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = "No record found.",
                style = AppTheme.typography.largeBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
    LazyVerticalGrid(
        columns = GridCells.Fixed(if (deviceType == DeviceType.MobilePortrait) 1 else 2),
        verticalArrangement = Arrangement.spacedBy(if (deviceType == DeviceType.MobilePortrait) 16.dp else 32.dp),
        horizontalArrangement = Arrangement.spacedBy(if (deviceType == DeviceType.MobilePortrait) 16.dp else 32.dp),
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        if (recentSearch?.option == BookingType.TripPackage.title) {
            items(tripPackageList.itemCount) { index ->
                val tripPackage = tripPackageList[index]
                TripPackageCard(
                    tripPackage = tripPackage,
                    modifier = Modifier.height(if (deviceType == DeviceType.MobilePortrait) 250.dp else 300.dp),
                    onClick = {
                        navController.navigate(AppScreen.TripPackage.passData(tripPackage!!.id, ""))
                    },
                    isMobile = false
                )
            }
        } else {
            items(hotelList.itemCount) { index ->
                val hotel = hotelList[index]
                HotelCard(
                    hotel,
                    modifier = Modifier.height(if (deviceType == DeviceType.MobilePortrait) 250.dp else 300.dp),
                    onClick = {
                        navController.navigate(AppScreen.Hotel.passData(hotel!!.id, ""))
                    })
            }
        }
    }
}
