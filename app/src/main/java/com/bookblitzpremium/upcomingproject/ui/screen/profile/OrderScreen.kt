package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.common.enums.BookingStatus
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalTPBookingViewModel
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun OrderScreen(navController: NavHostController) {
    val isMobile = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT
    val windowSizeClass: WindowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val isTabletPortrait = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM &&
            windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED

    val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    val localTPBookingViewModel: LocalTPBookingViewModel = hiltViewModel()
    val tripPackageBookingList =
        remember { localTPBookingViewModel.getTPBookingByUserID(userID) }.collectAsLazyPagingItems()

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
        Spacer(modifier = Modifier.height(8.dp))

        if(tripPackageBookingList.itemCount > 0) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(tripPackageBookingList.itemCount) { index ->
                    val booking = tripPackageBookingList[index]
                    BookingCard(
                        amount = booking!!.paymentAmount.toFloat(),
                        quantity = booking.purchaseCount.toString(),
                        tripPackageName = booking.tripPackageName,
                        status = getBookingStatus(booking.tripPackageStartDate, booking.status),
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
                        onRatingClick = { navController.navigate(AppScreen.Ratings.route) },
                        isMobile = isMobile
                    )
                }
            }
        }
        else {
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

        /*if (isTabletPortrait) {
            // Tablet Portrait - Split screen layout
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Hotel",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 28.dp, top = 16.dp)
                )
                VerticalScrollableImageList(
                    imageList = hotelImages,
                    modifier = Modifier.weight(1f)
                )
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    "Package",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 28.dp, top = 16.dp)
                )
                VerticalScrollableImageList(
                    imageList = packageImages,
                    modifier = Modifier.weight(1f)
                )
            }
        } else {
            // Phone or Landscape layout
            Column {
                Text(
                    "Hotel",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 28.dp)
                )
                HorizontalScrollableImageList(imageList = hotelImages)

                Text(
                    "Package",
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(start = 28.dp, top = 20.dp)
                )
                HorizontalScrollableImageList(imageList = packageImages)
            }
        }*/
    }
}

@Composable
fun VerticalScrollableImageList(
    imageList: List<Int>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(imageList) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Scrollable Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}

@Composable
fun HorizontalScrollableImageList(imageList: List<Int>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(imageList) { imageRes ->
            Image(
                painter = painterResource(id = imageRes),
                contentDescription = "Scrollable Image",
                modifier = Modifier
                    .width(380.dp)
                    .height(180.dp)
                    .clip(RoundedCornerShape(16.dp)),
                contentScale = ContentScale.Crop
            )
        }
    }
}


//@Preview(showBackground = true, name = "Phone Portrait", device = "spec:width=411dp,height=891dp")
//@Composable
//fun PhonePortraitPreviewMyOrder() {
//    OrderScreen(
//
//    )
//}
//
//
//@Preview(showBackground = true, name = "Tablet Portrait", device = "spec:width=800dp,height=1280dp")
//@Composable
//fun TabletPortraitPreviewMyOrder() {
//    OrderScreen(
//    )
//}
//
//
//@Preview(
//    showBackground = true,
//    name = "Tablet Landscape",
//    device = "spec:width=1280dp,height=800dp"
//)
//@Composable
//fun TabletLandscapePreviewOrder() {
//    OrderScreen(
//    )
//}

@Composable
fun BookingCard(
    amount: Float,
    quantity: String,
    tripPackageName: String,
    status: String,
    imageUrl: String,
    orderDate: String,
    onColumnClick: () -> Unit,
    onRatingClick: () -> Unit,
    isMobile: Boolean = true,
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
            Base64Image(
                imageUrl,
                modifier = Modifier
                    .height(if(isMobile)100.dp else 120.dp)
                    .width(if(isMobile)100.dp else 150.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp),
                verticalArrangement = Arrangement.SpaceAround,
            ) {
                Text(
                    text = tripPackageName,
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
}

fun getBookingStatus(date: String, bookingStatus: String): String {
    val formattedDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    return if (bookingStatus == BookingStatus.Completed.title || bookingStatus == BookingStatus.Cancelled.title) {
        bookingStatus
    } else if (LocalDate.now() < formattedDate) {
        BookingStatus.Confirmed.title
    } else {
        BookingStatus.ToReview.title
    }
}