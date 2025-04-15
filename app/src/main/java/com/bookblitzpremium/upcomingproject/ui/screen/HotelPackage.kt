package com.bookblitzpremium.upcomingproject.ui.screen

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R


data class NewsItem(
    val title: String,
    val timestamp: String,
    @DrawableRes val imageResId: Int
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
) {
    // Sample news items (replace with real data from your repository)
    val newsItems = listOf(
        NewsItem(
            title = "Latest Updates",
            timestamp = "",
            imageResId = R.drawable.airasia_bg // Add this image
        ),
        NewsItem(
            title = "Tech Trail",
            timestamp = "1 day",
            imageResId = R.drawable.batik_air_bg // Add this image
        ),
        NewsItem(
            title = "Midweek Muffins",
            timestamp = "1 day",
            imageResId = R.drawable.beach // Add this image
        ),
        NewsItem(
            title = "Company Events",
            timestamp = "",
            imageResId = R.drawable.firefly_icon // Add this image
        )
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Company News",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        ),
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { /* TODO: Open drawer */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.logo2), // Add this icon
                            contentDescription = "Menu",
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Share action */ }) {
                        Icon(
                            painter = painterResource(id = R.drawable.logo2), // Add this icon
                            contentDescription = "Share",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFFD81B60), // Pink color from screenshot
                    titleContentColor = Color.White,
                    navigationIconContentColor = Color.White,
                    actionIconContentColor = Color.White
                )
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(newsItems) { newsItem ->
                NewsItemCard(newsItem)
            }
        }
    }
}


@Composable
fun NewsItemCard(newsItem: NewsItem) {

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color(0xFFF5F5F5)) // Light gray background
    ) {
        // Airplane Image
        Image(
            painter = painterResource(id = newsItem.imageResId), // Add this image
            contentDescription = "Airplane",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(RoundedCornerShape(16.dp))
                ,
            contentScale = ContentScale.Crop
        )

        // Edit Button
        Button(
            onClick = {
                // TODO: Handle edit action (e.g., open an image picker)
            },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            shape = CircleShape,
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color.Black
            ),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
        ) {
            Text(
                text = "Edit",
                style = MaterialTheme.typography.bodyMedium.copy(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium
                )
            )
        }
    }

//    Card(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(horizontal = 16.dp),
//        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
//    ) {
//        Column {
//            // Image
//            Image(
//                painter = painterResource(id = newsItem.imageResId),
//                contentDescription = newsItem.title,
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(200.dp),
//                contentScale = ContentScale.Crop
//            )
//            // Title and Timestamp
//            Column(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp)
//            ) {
//                Text(
//                    text = newsItem.title,
//                    style = MaterialTheme.typography.titleMedium.copy(
//                        fontWeight = FontWeight.Bold,
//                        fontSize = 18.sp
//                    ),
//                    color = MaterialTheme.colorScheme.onBackground
//                )
//                if (newsItem.timestamp.isNotEmpty()) {
//                    Text(
//                        text = newsItem.timestamp,
//                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 14.sp),
//                        color = Color.Gray,
//                        modifier = Modifier.padding(top = 4.dp)
//                    )
//                }
//            }
//        }
//    }
}