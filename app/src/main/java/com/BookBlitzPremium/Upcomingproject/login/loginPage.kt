package com.BookBlitzPremium.Upcomingproject.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.BookBlitzPremium.Upcomingproject.R
import com.BookBlitzPremium.Upcomingproject.ui.theme.AppTheme

@Composable
fun PageLoaded(wording: String, wording2: String) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val isPhone = screenWidthDp <= 360.dp
    val isTablet = screenWidthDp >= 600.dp

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
    ) {
        // Background Image (same for all devices)
        Image(
            painter = painterResource(id = R.drawable.green_mountain),
            contentDescription = "Mountain landscape background",
            modifier = Modifier
                .fillMaxWidth()
                .height(AppTheme.size.large * 7.5f) // 240.dp
                .align(Alignment.BottomCenter)
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .background(Color.Transparent),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title Row
            Row(
                modifier = Modifier
                    .padding(top = AppTheme.size.large * 2) // 64.dp
                    .padding(horizontal = if (isPhone) AppTheme.size.medium else AppTheme.size.large) // 16.dp or 32.dp
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = wording,
                    style = AppTheme.typography.headlineLarge,
                    modifier = Modifier.padding(horizontal = AppTheme.size.small) // 8.dp
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.size.medium)) // 16.dp

            // Secondary Text Row
            Row(
                modifier = Modifier
                    .padding(horizontal = if (isPhone) AppTheme.size.medium else AppTheme.size.large) // 16.dp or 32.dp
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Text(
                    text = wording2,
                    style = AppTheme.typography.bodyLarge,
                    modifier = if (isTablet) Modifier.weight(1f) else Modifier // Stretch on tablets
                )
            }

            Spacer(modifier = Modifier.height(AppTheme.size.medium)) // 24.dp

            // Icon Row (device-specific alignment)
            Row(
                modifier = Modifier
                    .padding(horizontal = if (isPhone) AppTheme.size.medium else AppTheme.size.large) // 16.dp or 32.dp
                    .fillMaxWidth(),
                horizontalArrangement = if (isTablet) Arrangement.SpaceEvenly else Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = "Arrow icon",
                    modifier = Modifier.size(24.dp), // 24.dp
                    tint = Color.Black
                )
                if (isTablet) {
                    // Add a second icon for tablet multi-column layout
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Additional arrow",
                        modifier = Modifier.size(24.dp),
                        tint = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
fun RegristerPage(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){

        Row(){
            Text(
                text = "Welcome Back!",
                style = AppTheme.typography.titleLarge
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = AppTheme.size.normal) // 16.dp spacing
        ) {


        }

        Row(){

        }



    }
}

