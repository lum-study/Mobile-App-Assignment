package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.R

@Composable
fun OrderScreen(windowSizeClass: WindowSizeClass) {
    val hotelImages = List(10) { R.drawable.beach }
    val packageImages = List(10) { R.drawable.beach }
    val isTabletPortrait = windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.MEDIUM &&
            windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED

    Column(modifier = Modifier.fillMaxSize()) {
        // Header
        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = { /* Handle back navigation */ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
            Text(
                "My Order/Package",
                style = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold)
            )
            IconButton(
                onClick = { /* Handle back navigation */ },
                modifier = Modifier.size(24.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.QrCodeScanner,
                    contentDescription = "Back",
                    modifier = Modifier.size(24.dp)
                )
            }
        }

        if (isTabletPortrait) {
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
                    modifier = Modifier.weight(1f))
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
        }
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


@Preview(showBackground = true, name = "Phone Portrait", device = "spec:width=411dp,height=891dp")
@Composable
fun PhonePortraitPreviewMyOrder() {
    OrderScreen(
        windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    )
}


@Preview(showBackground = true, name = "Tablet Portrait", device = "spec:width=800dp,height=1280dp")
@Composable
fun TabletPortraitPreviewMyOrder() {
    OrderScreen(
        windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    )
}


@Preview(showBackground = true, name = "Tablet Landscape", device = "spec:width=1280dp,height=800dp")
@Composable
fun TabletLandscapePreviewOrder() {
    OrderScreen(
        windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    )
}