package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRatingViewModel
import com.bookblitzpremium.upcomingproject.data.model.RatingRecord
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RatingRecordsScreen() {
    val localRatingViewModel: LocalRatingViewModel = hiltViewModel()
    var ratingRecord by remember { mutableStateOf<List<RatingRecord>>(emptyList()) }
    val userID = FirebaseAuth.getInstance().currentUser?.uid

    LaunchedEffect(Unit) {
        if (userID != null) {
            ratingRecord = localRatingViewModel.getRatingByUserID(userID)
        }
    }

    if (ratingRecord.isEmpty()) {
        Box(
            modifier = Modifier.fillMaxSize(),
        ) {
            Text(
                text = "No record found",
                style = AppTheme.typography.largeBold,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(ratingRecord.size) { index ->
                val rating = ratingRecord[index]
                RatingRecordItem(
                    rating = rating,
                    onDelete = {

                    },
                    onUpdate = {

                    }
                )
            }
        }
    }
}

@Composable
fun RatingRecordItem(
    rating: RatingRecord,
    onDelete: () -> Unit,
    onUpdate: (Rating) -> Unit,
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = rememberAsyncImagePainter(rating.hotelImageUrl),
                contentDescription = "Rating Image",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = rating.hotelName,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = rating.description,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingBar(rating = rating.rating, modifier = Modifier.height(24.dp))
                }
            }
        }
    }
}

@Composable
fun RatingBar(rating: Int, modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        for (i in 1..5) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = "Rating",
                tint = if (i <= rating) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f),
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun PhoneRatingRecordsScreenPreview() {
    RatingRecordsScreen()
}

@Preview(showBackground = true, device = "spec:width=800dp,height=1280dp")
@Composable
fun TabletPortraitRatingRecordsScreenPreview() {
    RatingRecordsScreen()
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp")
@Composable
fun TabletLandscapeRatingRecordsScreenPreview() {
    RatingRecordsScreen()
}