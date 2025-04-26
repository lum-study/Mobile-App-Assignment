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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import androidx.compose.ui.text.font.FontWeight
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo

// Data model for RatingRecord
data class RatingRecord(
    val id: String,
    val title: String,
    val rating: Float,
    val review: String,
    val date: String,
    val imageUrl: String,
    val progress: Float,
)

@Composable
fun RatingRecordsScreen(
    records: List<RatingRecord>,
    onDeleteRecord: (String) -> Unit,
    onUpdateRecord: (RatingRecord) -> Unit,
    modifier: Modifier = Modifier
) {
    // Determine the screen size using currentWindowAdaptiveInfo()
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    val windowWidthSizeClass = windowSizeClass.windowWidthSizeClass

    // Check for tablet or phone sizes
    val isTablet = windowWidthSizeClass != WindowWidthSizeClass.COMPACT // True if it's a tablet
    val isPhone = windowWidthSizeClass == WindowWidthSizeClass.COMPACT // True if it's a phone

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
                onDelete = { onDeleteRecord(record.id) },
                onUpdate = onUpdateRecord,
                isTablet = isTablet
            )
        }
    }
}

@Composable
fun RatingRecordItem(
    record: RatingRecord,
    onDelete: () -> Unit,
    onUpdate: (RatingRecord) -> Unit,
    isTablet: Boolean
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
            // Image on the left
            Image(
                painter = rememberImagePainter(record.imageUrl),
                contentDescription = "Rating Image",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp) // Space between image and text
            )

            // Content for the RatingRecord
            Column(modifier = Modifier.weight(1f)) {
                // First row - Hotel/package name
                Text(
                    text = record.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Second row - Review
                Text(
                    text = record.review,
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 14.sp),
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Third row - Rating stars and progress bar
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

            // Edit and Delete buttons on the right side
            Spacer(modifier = Modifier.width(8.dp))

            Column(horizontalAlignment = Alignment.End) {
                // Status/approval indicator
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Status",
                    tint = Color.Green,
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row {
                    if (isEditing) {
                        IconButton(
                            onClick = {
                                val newRating = editedRating.toFloatOrNull() ?: record.rating
                                onUpdate(
                                    record.copy(
                                        review = editedReview,
                                        rating = newRating.coerceIn(1f, 5f)
                                    )
                                )
                                isEditing = false
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Save,
                                contentDescription = "Save"
                            )
                        }
                    } else {
                        IconButton(
                            onClick = { isEditing = true }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = "Edit"
                            )
                        }
                    }

                    IconButton(
                        onClick = onDelete
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RatingBar(
    rating: Float,
    modifier: Modifier = Modifier
) {
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

@Preview(showBackground = true, name = "Phone Portrait", device = "spec:width=411dp,height=891dp")
@Composable
fun PhonePortraitRatingPreview() {
    MaterialTheme {
        val sampleRecords = listOf(
            RatingRecord(
                id = "1",
                title = "Trip to Paradise Beach",
                rating = 4.5f,
                review = "Amazing beach with clear waters and perfect weather for relaxation.",
                date = "2023-05-15",
                imageUrl = "https://yourimageurl.com/paradise_beach.jpg",
                progress = 0.8f
            ),
            RatingRecord(
                id = "2",
                title = "Hotel Stay at Oceanview",
                rating = 3.0f,
                review = "The hotel was decent but could improve in cleanliness and service.",
                date = "2023-05-10",
                imageUrl = "https://yourimageurl.com/oceanview_hotel.jpg",
                progress = 0.5f
            )
        )

        var records by remember { mutableStateOf(sampleRecords) }

        RatingRecordsScreen(
            records = records,
            onDeleteRecord = { id -> records = records.filter { it.id != id } },
            onUpdateRecord = { updatedRecord -> records = records.map { if (it.id == updatedRecord.id) updatedRecord else it } }
        )
    }
}