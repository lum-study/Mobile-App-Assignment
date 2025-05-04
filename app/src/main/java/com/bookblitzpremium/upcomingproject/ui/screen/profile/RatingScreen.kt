package com.bookblitzpremium.upcomingproject.ui.screen.rating

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.businessviewmodel.BusinessRatingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.ui.components.RatingSuccessDialog
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RatingScreen(
    hotelId: String,
    bookingID: String = "",
    navController: NavHostController,
) {
    val context = LocalContext.current
    val activity = context as? Activity
    val windowSizeClass = activity?.let { getWindowSizeClass(it) }
    val windowWidthSizeClass = windowSizeClass?.widthSizeClass ?: WindowWidthSizeClass.Compact

    val businessRatingViewModel: BusinessRatingViewModel = hiltViewModel()
    val isLoading by businessRatingViewModel.loading.collectAsState()
    val hasError by businessRatingViewModel.error.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    val localUserViewModel: LocalUserViewModel = hiltViewModel()
    val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    var userInfo by remember { mutableStateOf<User?>(null) }
    LaunchedEffect(userID) {
        userInfo = localUserViewModel.getUserByID(userID)
    }

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

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            listOf("Overall Service", "Customer Support").forEach { label ->
                Button(
                    onClick = { comment = label },
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray)
                ) { Text(text = label, fontSize = 14.sp) }
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
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
                    val newRating = Rating(
                        name = userInfo!!.name,
                        description = comment,
                        rating = rating,
                        icon = userInfo!!.iconImage,
                        hotelID = hotelId,
                        userID = userID
                    )
                    businessRatingViewModel.addRatingToRemoteAndLocal(
                        rating = newRating,
                        bookingID = bookingID
                    )
                    showDialog = !showDialog
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

        if (showDialog) {
            RatingSuccessDialog(
                modifier = Modifier
                    .height(300.dp)
                    .width(300.dp),
                hasError = hasError ?: "",
                isLoading = isLoading,
                onHomeButtonClick = {
                    showDialog = it
                    navController.navigate(AppScreen.Home.route) {
                        popUpTo(AppScreen.Home.route) {
                            inclusive = true
                        }
                    }
                },
                onViewRecordButtonClick = {
                    showDialog = it
                    navController.navigate(AppScreen.ProfileGraph.route) {
                        popUpTo(AppScreen.Home.route)
                    }
                    navController.navigate(AppScreen.RatingRecords.route)
                },
                onDismissButtonClick = {
                    showDialog = !showDialog
                }
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun PhoneRatingScreenPreview() {
    val navController = rememberNavController()
    RatingScreen(
        hotelId = "hotel123",
        navController = navController
    )
}

@Preview(showBackground = true, device = "spec:width=800dp,height=1280dp")
@Composable
fun TabletPortraitRatingScreenPreview() {
    val navController = rememberNavController()
    RatingScreen(
        hotelId = "hotel123",
        navController = navController
    )
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp")
@Composable
fun TabletLandscapeRatingScreenPreview() {
    val navController = rememberNavController()
    RatingScreen(
        hotelId = "hotel123",
        navController = navController
    )
}