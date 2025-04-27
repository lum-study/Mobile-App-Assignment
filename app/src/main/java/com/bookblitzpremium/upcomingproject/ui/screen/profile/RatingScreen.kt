package com.bookblitzpremium.upcomingproject.ui.screen.rating

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.ui.screen.profile.RatingRecord
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
import androidx.window.core.layout.WindowSizeClass
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen

@Composable
fun RatingScreen(
    hotelId: String,
    onBackPressed: () -> Unit,
    onRatingSubmitted: (RatingRecord) -> Unit = {},
    navController: NavHostController // Add NavController
) {
    // Correct usage of LocalContext to get the Activity context
    val context = LocalContext.current
    val activity = context as? Activity
    val windowSizeClass = activity?.let { getWindowSizeClass(it) }

    // Ensure safe handling for null windowSizeClass
    val windowWidthSizeClass = windowSizeClass?.widthSizeClass ?: WindowWidthSizeClass.Compact // Default to Compact if null

    // Check for device type (Phone, Tablet, etc.)
    val isTablet = windowWidthSizeClass == WindowWidthSizeClass.Expanded // Check for tablet size
    val isPhone = windowWidthSizeClass == WindowWidthSizeClass.Compact // Check for phone size

    var rating by remember { mutableStateOf(0) }
    var comment by remember { mutableStateOf("") }

    // List of Rating Records
    var records by remember { mutableStateOf<List<RatingRecord>>(emptyList()) }

    // Function to handle adding a new record
    val addRating = { newRecord: RatingRecord ->
        records = records + newRecord // Add the new record to the list
    }

    // Function to handle deleting a record
    val deleteRating = { recordId: String ->
        records = records.filter { it.id != recordId } // Remove record by id
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        // Top Bar with Back button
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButton(onClick = onBackPressed) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back",
                    tint = Color.Black
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Feedback",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.width(48.dp)) // Placeholder to balance the BackIconButton size
        }

        // "Rate Your Experience" Title
        Text(
            text = "Rate Your Experience",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Star Rating Section
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

        // "Tell us what can be Improved?" Section
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tell us what can be Improved?",
            style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Medium),
            modifier = Modifier.align(Alignment.Start),
            fontSize = 16.sp
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Feedback Options (Buttons)
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { comment = "Overall Service" },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "Overall Service", fontSize = 14.sp)
            }
            Button(
                onClick = { comment = "Customer Support" },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "Customer Support", fontSize = 14.sp)
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = { comment = "Speed and Efficiency" },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "Speed and Efficiency", fontSize = 14.sp)
            }
            Button(
                onClick = { comment = "Repair Quality" },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(text = "Repair Quality", fontSize = 14.sp)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Text Input for Feedback
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

        // Gray line before Submit Button
        Divider(color = Color.Gray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // Submit Button in a separate section with blue color
        Button(
            onClick = {
                if (rating > 0) {
                    // Do rating submission logic here if needed
                    val newRating = RatingRecord(
                        id = "4",  // Dynamically generated or passed in
                        title = "New Rating",
                        rating = rating.toFloat(),
                        review = comment,
                        date = "2023-06-15",  // Example date
                        imageUrl = "https://example.com/image.jpg",  // Placeholder image URL
                        progress = 0.75f
                    )
                    onRatingSubmitted(newRating) // Submit the rating
                    navController.navigate(AppScreen.RatingRecords.route) // Navigate to RatingRecordsScreen
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(55.dp),
            shape = RoundedCornerShape(16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2196F3)), // Blue color
            enabled = rating > 0
        ) {
            Text(text = "Submit Rating", fontSize = 18.sp)
        }
    }
}


@Preview(showBackground = true, name = "RatingScreen Preview")
@Composable
fun RatingScreenPreview() {
    MaterialTheme {
        val navController = rememberNavController()
        RatingScreen(
            hotelId = "hotel123",
            onBackPressed = { navController.popBackStack() },
            onRatingSubmitted = { ratingRecord ->
                println("Submitted Rating: ${ratingRecord.title}")
            },
            navController = navController
        )
    }
}