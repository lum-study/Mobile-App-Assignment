//package com.bookblitzpremium.upcomingproject.ui.screen.auth
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxHeight
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.offset
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Brush
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.style.TextDecoration
//import androidx.compose.ui.unit.dp
//import com.bookblitzpremium.upcomingproject.ui.components.VideoPlayer
//import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
//
//@Composable
//fun OTPpage(showToggleToTablet: Boolean){
//
//    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
//    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
//    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
//
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFFFFF9E5), // Light yellow
//                        Color(0xFFE6F0FA)  // Light blue
//                    )
//                )
//            )
//    ) {
//
//        Column(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
//                .padding(horizontal = 28.dp)
//                .offset(x = offsetValueX),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.Center // Center content vertically
//        ) {
//            Text(
//                text = "OTP Verification",
//                style = AppTheme.typography.expandBold,
//                modifier = Modifier
//                    .padding(bottom = 30.dp)
//                    .align(Alignment.CenterHorizontally)
//            )
//
//            Row (
//                modifier = Modifier
//                    .padding(vertical = 50.dp),
//                horizontalArrangement = Arrangement.spacedBy(16.dp)
//
//            ){
////                Column(
////                    horizontalAlignment = Arrangement.spacedBy(16.dp),
////                    modifier = Modifier
////                ){
//                repeat(4) { // Creates 4 Box items
//                    Box(
//                        modifier = Modifier
//                            .size(48.dp) // Circle size
//                            .border(width = 1.dp, color = Color.Black, RoundedCornerShape(8.dp))
//                            .background(Color.LightGray) // Circular background
//                            .padding(12.dp), // Inner padding
//                        contentAlignment = Alignment.Center
//                    ) {
//                        // Content inside the Box (e.g., Icon, Text, etc.)
//                    }
//                }
////                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//                    .padding(start = 24.dp)
//            ) {
//                Text(
//                    text = "Didn’t receive code? "
//                )
//
//                Text(
//                    text = "Resend",
//                    color = Color.Blue, // Set text color to blue
//                    style = TextStyle(
//                        textDecoration = TextDecoration.Underline // Underline the text
//                    ),
//                    modifier = Modifier.clickable {
//                        // Handle click action here
//                    }
//                )
//
//            }
//
////            ButtonHeader(R.string.verify, valueHorizontal , )
//        }
//    }
//}
