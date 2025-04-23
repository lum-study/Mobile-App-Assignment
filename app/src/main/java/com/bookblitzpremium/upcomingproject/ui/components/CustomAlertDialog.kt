package com.bookblitzpremium.upcomingproject.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Preview(widthDp = 350, heightDp = 250)
@Composable
fun TripPackageBookingDialog(
    isLoading: Boolean = true,
    onHomeButtonClick: (Boolean) -> Unit = {},
    onViewOrderButtonClick: (Boolean) -> Unit = {},
) {
    AlertDialog(
        onDismissRequest = {},
        confirmButton = {},
        text = {
            AppTheme {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier.fillMaxSize()
                ) {
                    when (isLoading) {
                        true -> {
                            CircularProgressIndicator(modifier = Modifier.size(100.dp))
                            Text(
                                "Processing your booking...",
                                style = AppTheme.typography.mediumBold
                            )
                        }

                        false -> {
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
        },
        modifier = Modifier
            .height(250.dp)
            .width(300.dp)
    )
}