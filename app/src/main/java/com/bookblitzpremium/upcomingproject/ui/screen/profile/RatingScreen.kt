package com.bookblitzpremium.upcomingproject.ui.screen.rating

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.compose.ui.tooling.preview.Preview
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.screen.profile.RatingRecord
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRatingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import androidx.hilt.navigation.compose.hiltViewModel
import java.util.UUID

fun RatingRecord.toRatingEntity(hotelId: String): Rating {
    return Rating(
        id = this.id,
        name = this.title,
        description = this.review,
        rating = this.rating.toInt(),
        icon = this.imageUrl,
        hotelID = hotelId
    )
}

@Composable
fun RatingScreen(
    hotelId: String,
    onBackPressed: () -> Unit,
    onRatingSubmitted: (RatingRecord) -> Unit = {},
    navController: NavHostController
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val windowSizeClass = activity?.let { getWindowSizeClass(it) }
    val windowWidthSizeClass = windowSizeClass?.widthSizeClass ?: WindowWidthSizeClass.Compact
    val viewModel: LocalRatingViewModel = hiltViewModel()

    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "Rate Your Experience",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            for (i in 1..5) {
                IconButton(
                    onClick = { rating = i },
                    modifier = Modifier.size(48.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "Rate $i star",
                        tint = if (i <= rating) Color(0xFFFFD700) else Color.LightGray,
                        modifier = Modifier.size(40.dp)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tell us what can be Improved?",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.align(Alignment.Start),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            listOf("Overall Service", "Customer Support").forEach { label ->
                Button(
                    onClick = { comment = label },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) { Text(text = label, fontSize = 14.sp) }
            }
        }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
            listOf("Speed and Efficiency", "Repair Quality").forEach { label ->
                Button(
                    onClick = { comment = label },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) { Text(text = label, fontSize = 14.sp) }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = comment,
            onValueChange = { comment = it },
            label = { Text("Write your feedback...") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp),
            shape = RoundedCornerShape(16.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (rating > 0) {
                    val newRating = RatingRecord(
                        id = UUID.randomUUID().toString(),
                        title = "New Rating",
                        rating = rating.toFloat(),
                        review = comment,
                        date = "2023-06-15",
                        imageUrl = "https://example.com/image.jpg",
                        progress = rating.toFloat() / 5f
                    )
                    viewModel.addOrUpdateRating(newRating.toRatingEntity(hotelId))
                    onRatingSubmitted(newRating)
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)),
            enabled = rating > 0
        ) {
            Text(text = "Submit Rating", fontSize = 18.sp)
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun PhoneRatingScreenPreview() {
    val navController = rememberNavController()
    RatingScreen(hotelId = "hotel123", onBackPressed = {}, onRatingSubmitted = {}, navController = navController)
}

@Preview(showBackground = true, device = "spec:width=800dp,height=1280dp")
@Composable
fun TabletPortraitRatingScreenPreview() {
    val navController = rememberNavController()
    RatingScreen(hotelId = "hotel123", onBackPressed = {}, onRatingSubmitted = {}, navController = navController)
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp")
@Composable
fun TabletLandscapeRatingScreenPreview() {
    val navController = rememberNavController()
    RatingScreen(hotelId = "hotel123", onBackPressed = {}, onRatingSubmitted = {}, navController = navController)
}