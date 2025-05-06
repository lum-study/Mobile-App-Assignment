package com.bookblitzpremium.upcomingproject.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Preview(widthDp = 350, heightDp = 250)
@Composable
fun TripPackageBookingDialog(
    modifier: Modifier = Modifier,
    hasError: String = "",
    isLoading: Boolean = true,
    onHomeButtonClick: (Boolean) -> Unit = {},
    onViewOrderButtonClick: (Boolean) -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        text = {
            AppTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    when (isLoading) {
                        true -> {
                            CircularProgressIndicator(modifier = Modifier.size(100.dp))
                            Text(
                                text = "Processing your booking...",
                                style = AppTheme.typography.mediumBold,
                            )
                        }

                        false -> {
                            if (hasError.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = "Failed",
                                    tint = Color(0xFFFF0000),
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Booking failed!",
                                    style = AppTheme.typography.mediumBold
                                )
                                Text(
                                    text = hasError,
                                    style = AppTheme.typography.mediumBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = {
                                    onDismissButtonClick()
                                }) {
                                    Text(
                                        text = "Try again later",
                                        style = AppTheme.typography.smallSemiBold
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Success",
                                    tint = Color(0xFF00FF0B),
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Booking Successful!",
                                    style = AppTheme.typography.mediumBold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(onClick = {
                                        onHomeButtonClick(false)
                                    }) {
                                        Text(
                                            text = "Home",
                                            style = AppTheme.typography.smallSemiBold
                                        )
                                    }
                                    Button(onClick = {
                                        onViewOrderButtonClick(false)
                                    }) {
                                        Text(
                                            text = "View Booking",
                                            style = AppTheme.typography.smallSemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun RatingSuccessDialog(
    modifier: Modifier = Modifier,
    hasError: String = "",
    isLoading: Boolean = true,
    onHomeButtonClick: (Boolean) -> Unit = {},
    onViewRecordButtonClick: (Boolean) -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        text = {
            AppTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    when (isLoading) {
                        true -> {
                            CircularProgressIndicator(modifier = Modifier.size(100.dp))
                            Text(
                                text = "Uploading your rating",
                                style = AppTheme.typography.mediumBold,
                            )
                        }

                        false -> {
                            if (hasError.isNotEmpty()) {
                                Icon(
                                    imageVector = Icons.Default.ErrorOutline,
                                    contentDescription = "Failed",
                                    tint = Color(0xFFFF0000),
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Rating failed!",
                                    style = AppTheme.typography.mediumBold
                                )
                                Text(
                                    text = hasError,
                                    style = AppTheme.typography.mediumBold,
                                    maxLines = 1,
                                    overflow = TextOverflow.Ellipsis
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Button(onClick = {
                                    onDismissButtonClick()
                                }) {
                                    Text(
                                        text = "Try again later",
                                        style = AppTheme.typography.smallSemiBold
                                    )
                                }
                            } else {
                                Icon(
                                    imageVector = Icons.Default.CheckCircle,
                                    contentDescription = "Success",
                                    tint = Color(0xFF00FF0B),
                                    modifier = Modifier.size(100.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Rating Successful!",
                                    style = AppTheme.typography.mediumBold
                                )
                                Spacer(modifier = Modifier.height(16.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    Button(onClick = {
                                        onHomeButtonClick(false)
                                    }) {
                                        Text(
                                            text = "Home",
                                            style = AppTheme.typography.smallSemiBold
                                        )
                                    }
                                    Button(onClick = {
                                        onViewRecordButtonClick(false)
                                    }) {
                                        Text(
                                            text = "View Record",
                                            style = AppTheme.typography.smallSemiBold
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        },
        modifier = modifier
    )
}

@Composable
fun CancelBookingDialog(
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {},
    onDismissButtonClick: () -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = { onDismissButtonClick() },
        dismissButton = {
            Button(
                onClick = { onDismissButtonClick() },
            ) {
                Text(
                    text = "Cancel",
                    style = AppTheme.typography.smallSemiBold
                )
            }
        },
        confirmButton = {
            Button(
                onClick = { onDeleteClick() },
            ) {
                Text(
                    text = "Confirm",
                    style = AppTheme.typography.smallSemiBold
                )
            }
        },
        text = {
            AppTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color(0xFFFF0000),
                        modifier = Modifier.size(80.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Are you sure to cancel the booking?",
                        style = AppTheme.typography.mediumBold,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                }
            }
        },
        modifier = modifier
    )
}