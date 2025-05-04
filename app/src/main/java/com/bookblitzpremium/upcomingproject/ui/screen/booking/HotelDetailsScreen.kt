package com.bookblitzpremium.upcomingproject.ui.screen.booking

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.Booking.FeatureDisplay
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalHotelViewModel
import com.bookblitzpremium.upcomingproject.ui.components.SkeletonLoader
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import java.net.URLEncoder


@Preview(showBackground = true)
@Composable
fun PreviewScreen() {
    val navController = rememberNavController()
    HotelDetailScreen(navController, hotelBookingId = "dgdf")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HotelDetailScreen(
    navController: NavController,
    hotelBookingId: String,
    tripPackageID: String = "",
    viewModel: LocalHotelViewModel = hiltViewModel(),
) {
    LaunchedEffect(hotelBookingId) {
        viewModel.getHotelByID(hotelBookingId)
    }

    val hotel by viewModel.selectedHotel.collectAsState()

    if (hotel == null) {
        Box(
            Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            SkeletonLoader()
        }
    } else {
        HotelDetailContent(
            hotel = hotel!!,
            onBook = { price ->
                val hotelID = URLEncoder.encode(hotel?.id, "UTF-8")
                val hotelPrice = URLEncoder.encode(hotel?.price.toString(), "UTF-8")
                navController.navigate(
                    "${AppScreen.BookingDate.route}/$hotelID/$hotelPrice"
                )
            },
            tripPackageID = tripPackageID,
            navController = navController
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HotelDetailContent(
    hotel: Hotel,
    onBook: (String) -> Unit,
    tripPackageID: String,
    navController: NavController
) {
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(AppTheme.colorScheme.background)
    ) {
        HotelImageSection(hotel.imageUrl, tripPackageID)
        Spacer(Modifier.height(16.dp))
        HotelInfoSection(hotel.name, hotel.address, hotel.rating, navController, hotel.feature)
        Spacer(Modifier.height(16.dp))
        AboutSection(hotel.name, hotel.rating)
        Spacer(Modifier.weight(1f))
        if (tripPackageID == "") {
            BookNowButton(price = hotel.price.toString(), onBook = onBook)
        }
    }
}

@Composable
private fun HotelImageSection(imageUrl: String, tripPackageID: String) {
    var showImage by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        showImage = true
    }

    if (tripPackageID.isEmpty()) {
        Box(
            Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .aspectRatio(3f / 4f)
                .clip(RoundedCornerShape(16.dp))
                .background(AppTheme.colorScheme.surfaceVariant)
        ) {
            if (showImage) {
                UrlImage(
                    imageUrl = imageUrl,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    } else {
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            UrlImage(
                imageUrl = imageUrl,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(200.dp)
                    .height(200.dp)
            )
        }
    }
}

@Composable
private fun HotelInfoSection(name: String, address: String, rating: Double, navController: NavController, hotel: String) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = name,
                style = AppTheme.typography.largeBold,
                color = AppTheme.colorScheme.onBackground
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                Icons.Default.LocationOn,
                contentDescription = null,
                tint = AppTheme.colorScheme.secondary
            )
            Spacer(Modifier.width(4.dp))
            Text(
                text = address,
                color = AppTheme.colorScheme.secondary,
                style = AppTheme.typography.labelMedium
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(24.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            FeatureDisplay(
                hotel = hotel,
                modifier = Modifier
                    .padding(top = 8.dp)
                    .fillMaxWidth(),
                rating = rating
            )
        }
        Spacer(Modifier.height(12.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Button(
                onClick = {
                    navController.navigate(AppScreen.Maps.route)
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = AppTheme.colorScheme.primary, // Use primary color
                    contentColor = AppTheme.colorScheme.onPrimary // Use onPrimary for text
                )
            ) {
                Text(
                    text = "Maps",
                    style = AppTheme.typography.mediumBold, // Add typography
                    color = AppTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
private fun RatingItem(rating: Double) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Default.Star,
            contentDescription = null,
            tint = AppTheme.colorScheme.starRating // Use defined starRating color
        )
        Spacer(Modifier.width(4.dp))
        Text(
            text = rating.toString(),
            style = AppTheme.typography.labelMedium, // Use defined typography
            color = AppTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun StatItem(icon: ImageVector, text: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, contentDescription = null, tint = AppTheme.colorScheme.secondary)
        Spacer(Modifier.width(4.dp))
        Text(
            text = text,
            style = AppTheme.typography.labelMedium, // Use defined typography
            color = AppTheme.colorScheme.onBackground
        )
    }
}

@Composable
private fun AboutSection(name: String, rating: Double) {
    Column(Modifier.padding(horizontal = 16.dp)) {
        Text(
            text = "About Destination",
            style = AppTheme.typography.titleLarge, // Use defined typography
            color = AppTheme.colorScheme.onBackground
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = generateHotelDescription(name, rating),
            style = AppTheme.typography.labelMedium, // Use defined typography
            color = AppTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun BookNowButton(price: String, onBook: (String) -> Unit) {
    Button(
        onClick = { onBook(price) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colorScheme.primary,
            contentColor = AppTheme.colorScheme.onPrimary
        )
    ) {
        Text(
            text = "Book Now",
            style = AppTheme.typography.mediumBold // Add typography
        )
    }
}

@Composable
fun BookingHotelScreen(
    navController: NavController,
    viewModel: LocalHotelViewModel = hiltViewModel(),
    hotelBooking: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LaunchedEffect(Unit) {
            viewModel.getHotelByID(hotelBooking)
        }
        val hotel by viewModel.selectedHotel.collectAsState()
        if (hotel != null) {
            val hotelData = hotel!!
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.64f)
                    .clip(RoundedCornerShape(bottomEnd = 16.dp, bottomStart = 16.dp))
            ) {
                UrlImage(
                    imageUrl = hotelData.imageUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .clip(RoundedCornerShape(16.dp)),
                    contentScale = ContentScale.Crop,
                )
            }

            // Main Content
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colorScheme.background) // Use background color
                    .padding(16.dp)
            ) {
                // Title with Heart Icon
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = hotelData.name,
                        style = AppTheme.typography.largeBold, // Use defined typography
                        color = AppTheme.colorScheme.onBackground
                    )

                    Button(
                        onClick = {
                            navController.navigate(AppScreen.Maps.route)
                        },
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = AppTheme.colorScheme.primary, // Use primary color
                            contentColor = AppTheme.colorScheme.onPrimary // Use onPrimary for text
                        )
                    ) {
                        Text(
                            text = "Maps",
                            style = AppTheme.typography.mediumBold, // Add typography
                            color = AppTheme.colorScheme.onPrimary
                        )
                    }
                }
                // Location
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = AppTheme.colorScheme.secondary
                    )
                    Text(
                        text = hotelData.address,
                        style = AppTheme.typography.labelMedium, // Use defined typography
                        color = AppTheme.colorScheme.secondary,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                // Rating, Distance, Restaurants
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = "Rating",
                            tint = AppTheme.colorScheme.starRating // Use defined starRating color
                        )
                        Text(
                            text = hotelData.rating.toString(),
                            style = AppTheme.typography.labelMedium, // Use defined typography
                            color = AppTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(start = 4.dp)
                        )
                    }
                }

                // About Destination
                Text(
                    text = "About Destination",
                    style = AppTheme.typography.titleLarge, // Use defined typography
                    color = AppTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                val description = generateHotelDescription(hotelData.name, hotelData.rating)

                Text(
                    text = description,
                    style = AppTheme.typography.labelMedium, // Use defined typography
                    color = AppTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.height(16.dp))

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        val hotelID = hotelData.id
                        val hotelPrice = hotelData.price.toString()
                        navController.navigate(
                            "${AppScreen.BookingDate.route}/$hotelID/$hotelPrice"
                        )
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = AppTheme.colorScheme.primary, // Use primary color
                        contentColor = AppTheme.colorScheme.onPrimary // Use onPrimary for text
                    )
                ) {
                    Text(
                        text = "Book Now",
                        style = AppTheme.typography.mediumBold, // Add typography
                        color = AppTheme.colorScheme.onPrimary
                    )
                }
            }
        } else {
            CircularProgressIndicator()
        }
    }
}

fun generateHotelDescription(hotelName: String, rating: Double): String {
    val templates = listOf(
        "$hotelName offers a luxurious stay with a remarkable $rating★ rating, perfect for travelers seeking comfort and convenience.",
        "Experience premium hospitality at $hotelName, rated $rating★ for its top-notch service and serene atmosphere.",
        "With a $rating★ rating, $hotelName stands out as one of the most sought-after stays, blending elegance and value.",
        "Enjoy breathtaking views and world-class service at $hotelName — proudly rated $rating★ by guests.",
        "$hotelName delivers exceptional comfort and amenities, earning a solid $rating★ from satisfied visitors.",
        "Guests love $hotelName for its peaceful vibes and excellent service, reflected in its $rating★ rating.",
        "Stay at $hotelName and enjoy the perfect balance of luxury and affordability, with a guest rating of $rating★."
    )

    return templates.random()
}