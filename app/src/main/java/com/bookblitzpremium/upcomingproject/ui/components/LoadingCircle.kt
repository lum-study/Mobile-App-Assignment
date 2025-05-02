package com.bookblitzpremium.upcomingproject.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.bookblitzpremium.upcomingproject.data.model.AuthState

@Composable
fun CheckStatusLoading(
    isLoading: Boolean = false,
    modifier: Modifier = Modifier,
    backgroundAlpha: Float = 0.5f,
    indicatorColor: Color = MaterialTheme.colorScheme.primary
) {
    if (isLoading) {
        Box(
            modifier = modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = backgroundAlpha))
                .clickable(enabled = true, onClick = {}), // Absorb touch
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(color = indicatorColor)
        }
    }
}
