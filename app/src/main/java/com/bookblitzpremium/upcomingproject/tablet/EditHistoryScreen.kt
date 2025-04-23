package com.bookblitzpremium.upcomingproject.tablet

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DestinationCard(
    imagePainter: Painter,
    title: String,
    price: String,
    coordinates: String,
    onDetailsClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        shape = RoundedCornerShape(16.dp),
        elevation =  CardDefaults.cardElevation(
            defaultElevation = 4.dp
        ),
        onClick = {}
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Background image
            Image(
                painter = imagePainter,
                contentDescription = title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )

            // Semi-transparent overlay at the bottom
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .align(Alignment.BottomCenter)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.5f))
                        )
                    )
            )

            // Coordinates text at the top
            Box(
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(8.dp)
                    .border(1.dp, Color.Blue, RoundedCornerShape(4.dp))
                    .padding(4.dp)
            ) {
                Text(
                    text = coordinates,
                    color = Color.White,
                    fontSize = 12.sp
                )
            }

            // Title and price text at the bottom
            Column(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = price,
                    color = Color.White,
                    fontSize = 16.sp
                )
            }

            // Details button on the right
            Button(
                onClick = onDetailsClick,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
            ) {
                Text(
                    text = "Details",
                    color = Color.Black
                )
            }
        }
    }
}

@Composable
fun TravelOffersScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        DestinationCard(
            imagePainter = ColorPainter(Color.Gray),
            title = "Switzerland",
            price = "from $699",
            coordinates = "818.04 x 0",
            onDetailsClick = { /* Navigate to details screen */ }
        )
        DestinationCard(
            imagePainter = ColorPainter(Color.Gray),
            title = "Japan",
            price = "from $799",
            coordinates = "900.00 x 1",
            onDetailsClick = { /* Navigate to details screen */ }
        )
    }
}

// Optional preview for development
@Preview
@Composable
fun PreviewTravelOffersScreen() {
    TravelOffersScreen()
}