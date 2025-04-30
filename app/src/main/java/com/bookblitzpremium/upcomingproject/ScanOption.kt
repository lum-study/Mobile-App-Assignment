package com.bookblitzpremium.upcomingproject

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookblitzpremium.upcomingproject.common.enums.Feature
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Hotel
import kotlin.collections.forEach

data class ScanOption(
    val title: String,
    val subtitle: String,
    val iconRes: Int // Drawable resource ID for the icon
)





//@Composable
//fun OptionScreen(
//    onClose: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    val scanOptions = listOf(
//        ScanOption("Documents", "Scan multiple documents", R.drawable.protrait_2),
//        ScanOption("ID card", "Scan ID cards", R.drawable.content),
//        ScanOption("Measure", "Measure length and area", R.drawable.hiking_new),
//        ScanOption("Count", "Count similar objects", R.drawable.beach),
//        ScanOption("Passport", "Scan passports", R.drawable.maps),
//        ScanOption("Math", "Solve math problems", R.drawable.hotel_images)
//    )
//
//    var scrollOffset by remember { mutableStateOf(0.dp) }
//    val animatedOffset by animateDpAsState(
//        targetValue = scrollOffset,
//        label = "Scroll Animation"
//    )
//
//    val scrollState = rememberScrollState()
//
//    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .padding(16.dp)
//            .verticalScroll(scrollState)
//            .padding(top = animatedOffset)
//    ) {
//        // Header (following HotelReviewsSection pattern)
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 16.dp),
//            horizontalArrangement = Arrangement.Center,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Text(
//                text = "Scan",
//                style = MaterialTheme.typography.headlineSmall,
//                color = MaterialTheme.colorScheme.onSurface
//            )
//        }
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        // Grid of scan options
//        LazyVerticalGrid(
//            columns = GridCells.Fixed(2),
//            verticalArrangement = Arrangement.spacedBy(16.dp),
//            horizontalArrangement = Arrangement.spacedBy(16.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(500.dp)
//        ) {
//            items(scanOptions.size) { option ->
//                val option = scanOptions[option]
//                ScanOptionCard(
//                    option = option,
//                    onClick = {
//                        scrollOffset = -500.dp // Animate scroll up when clicked
//                    }
//                )
//            }
//        }
//
//        Spacer(modifier = Modifier.height(16.dp))
//
//        // Close button at the bottom (following the pattern)
//        IconButton(
//            onClick = onClose,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//                .clip(CircleShape)
//                .background(Color.Black)
//        ) {
//            Icon(
//                imageVector = Icons.Default.Close,
//                contentDescription = "Close",
//                modifier = Modifier.size(48.dp),
//                tint = Color.White
//            )
//        }
//    }
//}