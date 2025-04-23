package com.bookblitzpremium.upcomingproject.tablet

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookblitzpremium.upcomingproject.R

@Composable
fun MyScreen() {
    Box(modifier = Modifier.fillMaxSize()) {
        // Background image
        Image(
            painter = painterResource(id = R.drawable.hiking_potrait), // Replace with your image resource
            contentDescription = null, // Null for decorative background
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        // Column aligned to the bottom
        Column(
            modifier = Modifier
                .fillMaxWidth() // Only fill the width, not the height
                .align(Alignment.BottomCenter) // Align the Column to the bottom center of the Box
                .background(Color.Transparent.copy(alpha = 0.5f))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Discover new places with Bookblitzpremium!",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
            )

            // Spacer for vertical spacing
            Spacer(modifier = Modifier.height(16.dp))

            // Descriptive text with background for readability
            Text(
                text = "plan your trips, book excursions,\n and find the best routes",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center
                ),
            )

            // Spacer for vertical spacing
            Spacer(modifier = Modifier.height(16.dp))

            // Button
            Button(
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black
                ),
                onClick = { /* Add your action here */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Click Me")
            }
        }
    }
}