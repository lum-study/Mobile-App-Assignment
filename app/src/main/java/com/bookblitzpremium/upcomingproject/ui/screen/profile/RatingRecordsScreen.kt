package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import androidx.hilt.navigation.compose.hiltViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalRatingViewModel
import com.bookblitzpremium.upcomingproject.data.database.local.entity.Rating
import kotlinx.coroutines.launch
import androidx.navigation.compose.rememberNavController

data class RatingRecord(
    val id: String,
    val title: String,
    val rating: Float,
    val review: String,
    val date: String,
    val imageUrl: String,
    val progress: Float,
)

fun Rating.toRatingRecord(): RatingRecord {
    return RatingRecord(
        id = this.id,
        title = this.name,
        rating = this.rating.toFloat(),
        review = this.description,
        date = "2023-06-15",
        imageUrl = if (this.icon.isEmpty()) "https://example.com/default.jpg" else this.icon,
        progress = this.rating.toFloat() / 5f
    )
}

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
fun RatingRecordsScreen(
    hotelId: String,
    viewModel: LocalRatingViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val scope = rememberCoroutineScope()
    val ratingList by viewModel.getAllRatingsFlow().collectAsState(initial = emptyList())
    val records = ratingList.map { it.toRatingRecord() }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(records) { record ->
            RatingRecordItem(
                record = record,
                onDelete = {
                    scope.launch {
                        viewModel.deleteRating(record.toRatingEntity(hotelId))
                    }
                },
                onUpdate = { updated ->
                    scope.launch {
                        viewModel.addOrUpdateRating(updated.toRatingEntity(hotelId))
                    }
                }
            )
        }
    }
}

@Composable
fun RatingRecordItem(
    record: RatingRecord,
    onDelete: () -> Unit,
    onUpdate: (RatingRecord) -> Unit
) {
    var isEditing by remember { mutableStateOf(false) }
    var editedReview by remember { mutableStateOf(record.review) }
    var editedRating by remember { mutableStateOf(record.rating.toString()) }

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
                painter = rememberAsyncImagePainter(record.imageUrl),
                contentDescription = "Rating Image",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = record.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (isEditing) {
                    OutlinedTextField(
                        value = editedReview,
                        onValueChange = { editedReview = it },
                        label = { Text("Edit Review") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    OutlinedTextField(
                        value = editedRating,
                        onValueChange = { editedRating = it },
                        label = { Text("Edit Rating") },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = record.review,
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    RatingBar(rating = record.rating, modifier = Modifier.height(24.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    LinearProgressIndicator(
                        progress = record.progress,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(8.dp)
                            .padding(vertical = 4.dp),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Status",
                    tint = Color.Green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    if (isEditing) {
                        IconButton(onClick = {
                            val newRating = editedRating.toFloatOrNull() ?: record.rating
                            onUpdate(record.copy(review = editedReview, rating = newRating))
                            isEditing = false
                        }) {
                            Icon(Icons.Default.Save, contentDescription = "Save")
                        }
                    } else {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(Icons.Default.Edit, contentDescription = "Edit")
                        }
                    }
                    IconButton(onClick = onDelete) {
                        Icon(Icons.Default.Delete, contentDescription = "Delete")
                    }
                }
            }
        }
    }
}

@Composable
fun RatingBar(rating: Float, modifier: Modifier = Modifier) {
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
    val viewModel = hiltViewModel<LocalRatingViewModel>()
    RatingRecordsScreen(hotelId = "hotel123", viewModel = viewModel)
}

@Preview(showBackground = true, device = "spec:width=800dp,height=1280dp")
@Composable
fun TabletPortraitRatingRecordsScreenPreview() {
    val viewModel = hiltViewModel<LocalRatingViewModel>()
    RatingRecordsScreen(hotelId = "hotel123", viewModel = viewModel)
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp")
@Composable
fun TabletLandscapeRatingRecordsScreenPreview() {
    val viewModel = hiltViewModel<LocalRatingViewModel>()
    RatingRecordsScreen(hotelId = "hotel123", viewModel = viewModel)
}