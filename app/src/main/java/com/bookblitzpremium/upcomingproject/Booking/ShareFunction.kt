package com.bookblitzpremium.upcomingproject.Booking


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Wifi
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.common.enums.Feature
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRatingViewModel
import com.bookblitzpremium.upcomingproject.data.model.Calendar
import com.bookblitzpremium.upcomingproject.ui.components.HeaderDetails
import com.bookblitzpremium.upcomingproject.ui.components.UrlImage
import com.bookblitzpremium.upcomingproject.ui.screen.booking.CalendarView
import java.time.LocalDate




data class hotelDetails(
    val totalPrice : String ,
    val totalPerson : String ,
    val roomBooked : String ,
    val startDate : String,
    val endDate : String ,
    val paymentID: String
)


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


@Composable
fun BookingSummaryTable(
    startDate: String,
    endDate: String,
    roomCount: String,
    adultCount: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color.LightGray, RoundedCornerShape(16.dp))
            .padding(16.dp)
    ) {
        // First row: Check-In / Check-Out
        Row(Modifier.height(100.dp)) {
            TableCell(
                icon = Icons.Filled.CalendarToday,
                iconDesc = "Check-In",
                label = "Check-In",
                value = startDate,
                modifier = Modifier.weight(1f)
            )
            VerticalDivider()
            TableCell(
                icon = Icons.Filled.CalendarToday,
                iconDesc = "Check-Out",
                label = "Check-Out",
                value = endDate,
                modifier = Modifier.weight(1f)
            )
        }
        HorizontalDivider()
        // Second row: Room / Figure
        Row(Modifier.height(100.dp)) {
            TableCell(
                icon = Icons.Filled.Hotel,
                iconDesc = "Room",
                label = "Room",
                value = roomCount.toString(),
                modifier = Modifier.weight(1f)
            )
            VerticalDivider()
            TableCell(
                icon = Icons.Filled.People,
                iconDesc = "Figure",
                label = "Figure",
                value = adultCount.toString(),
                modifier = Modifier.weight(1f)
            )
        }
    }
}




@Composable
private fun TableCell(
    icon: ImageVector,
    iconDesc: String,
    label: String,
    value: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.White) // Each cell has its own white background
            .padding(12.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = iconDesc,
            tint = Color(0xFF4CAF50), // Soft green color
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.Gray
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            color = Color.Black
        )
    }
}


@Composable
private fun VerticalDivider() {
    Box(
        Modifier
            .width(1.dp)
            .fillMaxHeight()
            .background(Color.LightGray)
    )
}


@Composable
private fun HorizontalDivider() {
    Box(
        Modifier
            .fillMaxWidth()
            .height(1.dp)
            .background(Color.LightGray)
    )
}



@Composable
fun HotelReviewsSection(
    showBackButton: Int,
    modifier: Modifier = Modifier,
    hotelID: String
) {
    val localRatingViewModel : LocalRatingViewModel = hiltViewModel()
    val ratingData by localRatingViewModel.ratings.collectAsState()
    val loading by localRatingViewModel.loading.collectAsState()
    val error by localRatingViewModel.error.collectAsState()

    LaunchedEffect(hotelID) {
        localRatingViewModel.getRatingByHotelId(hotelID)
    }

    Column(
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        HeaderDetails("Reviews", 24.dp, modifier = Modifier)
        Spacer(modifier = Modifier.height(8.dp))

        when {
            loading -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp)
                )
            }
            error != null -> {
                Text(
                    text = error ?: "Unknown error",
                    color = Color.Red,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
            ratingData.isEmpty() -> {
                Text(
                    text = "No reviews available for this hotel",
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    textAlign = TextAlign.Center
                )
            }
            else -> {
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = spacedBy(16.dp)
                ) {
                    items(ratingData.size) { index ->
                        val rating = ratingData[index]
                        ReviewItem(
                            name = rating.name,
                            description = rating.description,
                            rating = rating.rating,
                            icon = rating.icon,
                            hotelID = rating.hotelID
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun ReviewItem(
    name : String,
    description : String,
    rating : Int,
    icon:String,
    hotelID:String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .width(300.dp) // Fixed width for horizontal scrolling
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.Top
        ) {
            // Reviewer avatar
            UrlImage(
                imageUrl = icon,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.width(16.dp))


            // Review details
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = spacedBy(4.dp)
            ) {
                // Name and date
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = name,
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }


                // Star rating
                StarRating(rating = rating)


                // Review text
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun HotelDescriptionSection(
    showBackButton: Int,
    modifier: Modifier = Modifier,
    description: String
) {
    val textOffset = if (showBackButton == 1) 24.dp else 24.dp


    var expanded by remember { mutableStateOf(false) }


    Column(
        verticalArrangement = spacedBy(6.dp),
        modifier = Modifier.padding(top = 16.dp)
    ) {
        HeaderDetails("Description", textOffset, modifier = Modifier)


        Text(
            text = description,
            color = Color.Black,
            fontSize = 14.sp,
            maxLines = if (expanded) Int.MAX_VALUE else 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.offset(x = textOffset)
        )


        Text(
            text = if (expanded) "Read less" else "Read more",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier
                .offset(x = textOffset)
                .clickable { expanded = !expanded }
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewCard(){
    val hotel = "Free Wi-Fi, Free parking, Air-conditioned, Kid-friendly, Restaurant, Free breakfast"
    val rating = 3.5


    FeatureDisplay(
        hotel = hotel,
        rating = rating,
        modifier = Modifier
    )
}


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FeatureDisplay(
    hotel: String,
    rating: Double,
    modifier: Modifier = Modifier
) {
    val hotelFeatures = mapFeaturesFromString(hotel)
    // Add rating as a separate feature
    val ratingFeature = Feature.Rating
    val allFeatures = listOf(ratingFeature) + hotelFeatures

    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        maxItemsInEachRow = 3
    ) {
        allFeatures.forEach { feature ->
            Box(
                modifier = Modifier.weight(1f)
            ) {
                ScanOptionCard(option = feature,rating = if (feature == ratingFeature) rating else null)
            }
        }
    }

}


@Composable
fun ScanOptionCard(
    option: Feature,
    rating: Double? = null,
    modifier: Modifier = Modifier
) {
    val minHeight = 80.dp // Adjusted to fit icon, title, rating, and padding

    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight) // Enforce minimum height
            .clip(RoundedCornerShape(16.dp))
            .background(Color.LightGray),
        contentAlignment = Alignment.Center // Center the column vertically
    ) {
        Column(
//            modifier = Modifier.padding(8.dp), // Move padding to Column
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {
            Icon(
                imageVector = option.icon,
                contentDescription = option.title,
                modifier = Modifier.size(16.dp),
                tint = Color.Black
            )
            Text(
                text = option.title,
                style = MaterialTheme.typography.titleSmall,
                textAlign = TextAlign.Center
            )
            // Display rating if this is the rating feature
            rating?.let {
                Text(
                    text = "$it/5",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}


fun mapFeaturesFromString(input: String): List<Feature> {
    // Split the input string by ", " and trim whitespace
    val featureTitles = input.split(",").map { it.trim() }


    // Map each title to the corresponding Feature enum value
    return featureTitles.mapNotNull { title ->
        Feature.entries.find { feature -> feature.title == title }
    }
}


@Composable
fun SelectFigure(
    onDateSelected: (Int?, Int?) -> Unit,
    modifier: Modifier = Modifier
) {
    var selected by rememberSaveable { mutableStateOf<String?>(null) }


    val options = listOf(
        "4 Person - 1 Room",
        "8 Person - 2 Room",
        "12 Person - 3 Room",
        "16 Person - 4 Room",
        "20 Person - 5 Room",
        "24 Person - 6 Room",
    )


    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        options.forEach { option ->
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(vertical = 4.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(if (selected == option) Color.DarkGray else Color.Black)
                    .clickable {
                        selected = option


                        // Optional: Extract numbers to notify parent
                        val (adult, room) = Regex("(\\d+) Person - (\\d+) Room")
                            .find(option)
                            ?.destructured
                            ?.let { (person, room) -> person.toIntOrNull() to room.toIntOrNull() }
                            ?: (null to null)
                        onDateSelected(adult, room)
                    }
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = option,
                        color = Color.White,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    if (selected == option) {
                        Icon(
                            imageVector = Icons.Default.Check,
                            contentDescription = "Selected",
                            tint = Color.White
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DialogFigure(
    onDismissRequest: () -> Unit,
    onDateSelected: (Int?, Int?) -> Unit
) {
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .height(450.dp)
                .width(600.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {
            SelectFigure(
                modifier = Modifier.padding(vertical = 20.dp, horizontal = 24.dp),
                onDateSelected = onDateSelected  // ✅ pass correctly
            )


            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close dialog",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}




@Composable
fun SelectDate(
    navController: NavController,
    modifier: Modifier = Modifier,
    onDateSelected: (LocalDate?, LocalDate?) -> Unit
) {
    var selectedStartDate by remember { mutableStateOf<LocalDate?>(null) }
    var selectedEndDate by remember { mutableStateOf<LocalDate?>(null) }


    val calendarParameter = Calendar(
        minDate = LocalDate.now(),
        maxDate = LocalDate.now().plusYears(1)
    )


    CalendarView(
        navController = navController,
        startDate = selectedStartDate,
        endDate = selectedEndDate,
        onDateRangeSelected = { start, end ->
            selectedStartDate = start
            selectedEndDate = end
            onDateSelected(start, end)
        },
        optionalParameter = calendarParameter,
    )
}




@Composable
fun DialogDate(
    navController: NavController,
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate?, LocalDate?) -> Unit
){
    Dialog(
        onDismissRequest = {}
    ) {
        Box(
            modifier = Modifier
                .height(600.dp)
                .width(500.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(Color.White)
        ) {


            SelectDate(
                navController = navController,
                onDateSelected = { startDate, endDate ->
                    onDateSelected(startDate, endDate)
                }
            )


            IconButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(8.dp)
                    .size(32.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close dialog",
                    tint = Color.Black,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}


@Composable
fun StarRating(rating: Int, maxRating: Int = 5, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        for (i in 1..maxRating) {
            Icon(
                imageVector = if (i <= rating) Icons.Default.Star else Icons.Default.Star,
                contentDescription = null,
                tint = if (i <= rating) Color(0xFF4CAF50) else Color.Gray, // Green for filled, gray for empty
                modifier = Modifier.size(16.dp)
            )
        }
    }
}

