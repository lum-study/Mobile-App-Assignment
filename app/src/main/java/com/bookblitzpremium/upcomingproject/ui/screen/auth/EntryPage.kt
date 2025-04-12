package com.bookblitzpremium.upcomingproject.ui.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R


@Composable
fun EntryPage(
    onGetStartedClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE6F0FA)) // Light blue background
    ) {

        // Illustration
        Image(
            painter = painterResource(id = R.drawable.hiking_potrait), // Replace with your illustration
            contentDescription = "Traveler Illustration",
            contentScale = ContentScale.Crop, // Ensures the image fills and crops
            modifier = Modifier.matchParentSize()

        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Logo
            Image(
                painter = painterResource(id = R.drawable.logo2), // Replace with your logo
                contentDescription = "Holibob Logo",
                contentScale = ContentScale.Crop ,
                modifier = Modifier
                    .size(150.dp)
                    .padding(top = 32.dp)
            )

            // Welcome Text
            Text(
                text = "Welcome to",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A3C5A), // Dark blue color
                modifier = Modifier.padding(top = 16.dp)
            )

            Text(
                text = "Holibob",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1A3C5A),
            )

            // Tagline
            Text(
                text = "Plan your trip and explore your favourite destinations with us.",
                fontSize = 16.sp,
                color = Color(0xFF6B7280), // Gray color
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 8.dp, start = 16.dp, end = 16.dp)
            )

            Spacer(modifier = Modifier.weight(1f))
            // Get Started Button
            Button(
                onClick = { onGetStartedClick()},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFFC107), // Yellow button color
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Get started",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

data class OnboardingItem(
    val imageResId: Int, // Resource ID for the illustration
    val title: String,
    val subtitle: String,
    val showButton: Boolean = false // Whether to show a "Get started" button (for the last screen)
)

@Composable
fun OnboardingFlow(
    onFinish: () -> Unit // Callback for when the user finishes onboarding
) {
    // List of onboarding items
    val onboardingItems = listOf(
        OnboardingItem(
            imageResId = R.drawable.see_lake, // Replace with your welcome illustration
            title = "Welcome to\nHolibob",
            subtitle = "Plan your trip and explore your favourite destinations with us.",
            showButton = false
        ),
        OnboardingItem(
            imageResId = R.drawable.hiking_rounded, // Replace with your "Find hidden gems" illustration
            title = "Find hidden gems",
            subtitle = "Explore your favourite destinations with us",
            showButton = false
        ),
        OnboardingItem(
            imageResId = R.drawable.diving_rounded, // Replace with a third illustration
            title = "Plan your trip",
            subtitle = "Book flights, hotels, and activities all in one place.",
            showButton = true // Show "Get started" button on the last screen
        )
    )

    // Pager state to manage swipeable pages
    val pagerState = rememberPagerState(pageCount = { onboardingItems.size })

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Swipeable Pager
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Takes remaining space
        ) { page ->
            val item = onboardingItems[page]
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (page == 0) {
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(CircleShape)
//                            .background(Color(0xFFE6F0FA)) // Light blue circular background
                            ,
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = item.imageResId),
                            contentDescription = "Illustration",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }


                } else {
                    // Other screens have a circular background for the illustration
                    Box(
                        modifier = Modifier
                            .size(300.dp)
                            .clip(CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = item.imageResId),
                            contentDescription = "Illustration",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // Title
                Text(
                    text = item.title,
                    fontSize = if (page == 0) 36.sp else 24.sp, // Larger title for the first screen
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF1A3C5A), // Dark blue
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(top = if (page == 0) 16.dp else 32.dp)
                )

                // Subtitle
                Text(
                    text = item.subtitle,
                    fontSize = 16.sp,
                    color = Color(0xFF6B7280), // Gray
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                )

                // Pagination Dots or Button
                if (item.showButton) {

                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = onFinish,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 16.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFFC107), // Yellow button color
                            contentColor = Color.White
                        )
                    ) {
                        Text(
                            text = "Get started",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else {
                    // Show pagination dots for other screens
                    Row(
                        modifier = Modifier
                            .padding(top = 16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        repeat(pagerState.pageCount) { index ->
                            Box(
                                modifier = Modifier
                                    .padding(horizontal = 4.dp)
                                    .size(8.dp)
                                    .clip(CircleShape)
                                    .background(
                                        if (pagerState.currentPage == index) Color(0xFFFF5733) // Orange for current page
                                        else Color(0xFFD1D5DB) // Gray for others
                                    )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.weight(1f))

                    // Slide to Learn More
                    Row(
                        modifier = Modifier
                            .padding(bottom = 32.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Slide to learn more",
                            fontSize = 14.sp,
                            color = Color(0xFFFF5733), // Orange
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "Swipe right",
                            tint = Color(0xFFFF5733),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }
        }
    }
}
