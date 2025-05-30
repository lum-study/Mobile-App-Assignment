package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingStatus
import com.bookblitzpremium.upcomingproject.common.enums.BookingType
import com.bookblitzpremium.upcomingproject.data.database.local.entity.TPBooking
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTPBookingViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteTPBookingViewModel
import com.bookblitzpremium.upcomingproject.data.model.TPBookingInformation
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.components.CancelBookingDialog
import com.bookblitzpremium.upcomingproject.ui.components.NotificationService
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun OrderScreen(navController: NavHostController) {
    val today = remember { LocalDate.now() }
    val isMobile =
        currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
    val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isTabletPortrait = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM &&
            windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED

    val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val localTPBookingViewModel: LocalTPBookingViewModel = hiltViewModel()
    val localHotelBookingViewModel: LocalHotelBookingViewModel = hiltViewModel()
    val hotelBookingList =
        remember { localHotelBookingViewModel.getHotelBookingInformationByUserID(userID) }.collectAsLazyPagingItems().itemSnapshotList.items
            .sortedWith(compareBy(
                { booking ->
                    when (getBookingStatus(bookingStatus = booking.status, date = booking.startDate, isTripPackage = false)) {
                        BookingStatus.Cancelled.title -> 3
                        BookingStatus.Completed.title -> 2
                        BookingStatus.Confirmed.title -> 1
                        BookingStatus.ToReview.title -> 0
                        else -> 1
                    }
                },
                { booking ->
                    val date = LocalDate.parse(
                        booking.startDate,
                        DateTimeFormatter.ofPattern("yyyy-MM-dd")
                    )
                    if (date.isBefore(today)) LocalDate.MAX.minusDays(date.toEpochDay()) else date
                }
            ))

    val tripPackageBookingList =
        remember { localTPBookingViewModel.getTPBookingByUserID(userID) }.collectAsLazyPagingItems().itemSnapshotList.items
            .sortedWith { a, b ->
                val aCancelled = a.status == BookingStatus.Cancelled.title
                val bCancelled = b.status == BookingStatus.Cancelled.title

                if (aCancelled && !bCancelled) return@sortedWith 1
                if (!aCancelled && bCancelled) return@sortedWith -1

                val aDate = LocalDate.parse(
                    a.tripPackageStartDate,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                )
                val bDate = LocalDate.parse(
                    b.tripPackageStartDate,
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
                )
                when {
                    !aDate.isBefore(today) && !bDate.isBefore(today) -> aDate.compareTo(bDate)
                    aDate.isBefore(today) && bDate.isBefore(today) -> bDate.compareTo(aDate)
                    aDate.isBefore(today) -> 1
                    else -> -1
                }
            }

    val bookingType = BookingType.entries
    var selectedBookingType by rememberSaveable { mutableStateOf(BookingType.TripPackage) }
    var showCancelBookingDialog by rememberSaveable { mutableStateOf(false) }
    var updatedBookingInfo by remember { mutableStateOf(TPBooking()) }
    var selectedBookingInfo by remember { mutableStateOf(TPBookingInformation()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "My Order",
            style = AppTheme.typography.largeBold,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            bookingType.forEach {
                OutlinedButton(
                    onClick = { selectedBookingType = it },
                    modifier = Modifier.weight(1f),
                    enabled = selectedBookingType != it,
                    colors = ButtonDefaults.outlinedButtonColors(
                        disabledContainerColor = Color(0xFFECECEC),
                        disabledContentColor = AppTheme.colorScheme.primary
                    )
                ) {
                    Text(
                        text = it.title,
                        style = AppTheme.typography.mediumNormal
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        if (selectedBookingType == BookingType.TripPackage && tripPackageBookingList.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tripPackageBookingList.size) { index ->
                    val booking = tripPackageBookingList[index]
                    BookingCard(
                        amount = booking.paymentAmount.toFloat(),
                        quantity = booking.purchaseCount.toString(),
                        name = booking.tripPackageName,
                        status = getBookingStatus(
                            booking.tripPackageStartDate,
                            booking.status,
                            isTripPackage = true
                        ),
                        imageUrl = booking.tripPackageImageUrl,
                        orderDate = booking.purchaseDate,
                        onColumnClick = {
                            navController.navigate(
                                AppScreen.TripPackage.passData(
                                    booking.tripPackageID,
                                    booking.id
                                )
                            )
                        },
                        onRatingClick = { navController.navigate(AppScreen.Ratings.passData(booking.tripPackageID)) },
                        isMobile = isMobile,
                        isTripPackage = true,
                        onEditClick = {
                            showCancelBookingDialog = true
                            updatedBookingInfo = TPBooking(
                                id = booking.id,
                                purchaseCount = booking.purchaseCount,
                                paymentID = booking.paymentID,
                                tripPackageID = booking.tripPackageID,
                                userID = booking.userID,
                                status = BookingStatus.Cancelled.title
                            )
                            selectedBookingInfo = booking
                        }
                    )
                }
            }
        } else if (selectedBookingType == BookingType.Hotel && hotelBookingList.isNotEmpty()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(hotelBookingList.size) { index ->
                    val booking = hotelBookingList[index]
                    BookingCard(
                        amount = booking.totalAmount.toFloat(),
                        quantity = booking.numberOfRoom.toString(),
                        name = booking.hotelName,
                        status = getBookingStatus(
                            booking.startDate,
                            booking.status,
                            isTripPackage = false
                        ),
                        imageUrl = booking.hotelImageUrl,
                        orderDate = booking.purchaseDate,
                        onColumnClick = {
                            navController.navigate(
                                AppScreen.Hotel.passData(
                                    booking.id,
                                    booking.hotelID,
                                    "",
                                    "true",
                                    booking.numberOfRoom.toString(),
                                    booking.numberOFClient.toString(),
                                    booking.startDate,
                                    booking.endDate,
                                )
                            )
                        },
                        onRatingClick = {
                            navController.navigate(
                                AppScreen.Ratings.passData(
                                    booking.hotelID,
                                    booking.id
                                )
                            )
                        },
                        onEditClick = {
                            navController.navigate(
                                AppScreen.BookingHistory.passData(booking.id)
                            )
                        },
                        isMobile = isMobile,
                        isTripPackage = false,
                    )
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

    if (showCancelBookingDialog) {
        val remoteTPBookingViewModel: RemoteTPBookingViewModel = hiltViewModel()
        val context = LocalContext.current
        CancelBookingDialog(
            onDeleteClick = {
                remoteTPBookingViewModel.updateTPBooking(updatedBookingInfo)
                localTPBookingViewModel.addOrUpdateTPBooking(updatedBookingInfo)
                remoteTPBookingViewModel.updatePackageSlot(
                    selectedBookingInfo.tripPackageID,
                    selectedBookingInfo.purchaseCount
                )
                NotificationService(context).showNotification(
                    title = "Booking cancelled.",
                    content = "We're sorry to see you go. Let us know if you need help booking again!"
                )
                showCancelBookingDialog = false
            },
            onDismissButtonClick = { showCancelBookingDialog = false }
        )
    }
}

@Composable
fun BookingCard(
    amount: Float,
    quantity: String,
    name: String,
    status: String,
    imageUrl: String,
    orderDate: String,
    onColumnClick: () -> Unit,
    onRatingClick: () -> Unit,
    isMobile: Boolean = true,
    onEditClick: () -> Unit = {},
    isTripPackage: Boolean,
) {
    Box(

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFFF3F3F3))
                .clickable { onColumnClick() },
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 20.dp, horizontal = 16.dp)
                    .fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                if (imageUrl.startsWith("http")) {
                    UrlImage(
                        imageUrl = imageUrl,
                        modifier = Modifier
                            .height(if (isMobile) 100.dp else 120.dp)
                            .width(if (isMobile) 100.dp else 150.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Base64Image(
                        imageUrl,
                        modifier = Modifier
                            .height(if (isMobile) 100.dp else 120.dp)
                            .width(if (isMobile) 100.dp else 150.dp)
                            .clip(RoundedCornerShape(12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 4.dp),
                    verticalArrangement = Arrangement.SpaceAround,
                ) {
                    Text(
                        text = name,
                        style = AppTheme.typography.mediumBold
                    )
                    Text(
                        text = stringResource(
                            R.string.booking_date,
                            LocalDate.parse(orderDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
                                .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))
                        ),
                        style = AppTheme.typography.smallRegular
                    )
                    Text(
                        text = stringResource(R.string.booking_quantity, quantity),
                        style = AppTheme.typography.smallRegular
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(R.string.price, amount),
                            style = AppTheme.typography.mediumBold
                        )
                        OutlinedButton(
                            onClick = {
                                if (status == BookingStatus.ToReview.title) {
                                    onRatingClick()
                                }
                            },
                            modifier = Modifier.height(24.dp),
                            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
                            enabled = status == BookingStatus.ToReview.title
                        ) {
                            Text(
                                text = status,
                                style = AppTheme.typography.smallSemiBold,
                                color = if (status == BookingStatus.ToReview.title) Color(0xFF4CAF50) else Color.Unspecified
                            )
                        }
                    }
                }
            }
        }
        if (!isTripPackage && status == BookingStatus.Confirmed.title) {
            Icon(
                imageVector = Icons.Default.Edit,
                contentDescription = "Edit",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 8.dp)
                    .size(20.dp)
                    .clickable { onEditClick() }
            )
        }
        if (isTripPackage && status == BookingStatus.Confirmed.title) {
            Icon(
                imageVector = Icons.Default.Delete,
                contentDescription = "Delete",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(end = 8.dp, top = 8.dp)
                    .size(20.dp)
                    .clickable { onEditClick() }
            )
        }
    }

}

fun getBookingStatus(date: String, bookingStatus: String, isTripPackage: Boolean): String {
    val formattedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return if (bookingStatus == BookingStatus.Completed.title || bookingStatus == BookingStatus.Cancelled.title) {
        bookingStatus
    } else if (LocalDate.now() < formattedDate) {
        BookingStatus.Confirmed.title
    } else if (isTripPackage && LocalDate.now() > formattedDate) {
        BookingStatus.Completed.title
    } else {
        BookingStatus.ToReview.title
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun PhoneOrderPreview() {
    val navController = rememberNavController()
    OrderScreen(navController = navController)
}

@Preview(showBackground = true, device = "spec:width=800dp,height=1280dp")
@Composable
fun TabletPortraitOrderPreview() {
    val navController = rememberNavController()
    OrderScreen(navController = navController)
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp")
@Composable
fun TabletLandscapeOrderPreview() {
    val navController = rememberNavController()
    OrderScreen(navController = navController)
}