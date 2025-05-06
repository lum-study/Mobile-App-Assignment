package com.bookblitzpremium.upcomingproject

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.TabletAuth.encodeToUri
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import kotlinx.coroutines.launch


@Composable
fun GenderSelectionScreen(
    navController: NavController,
    tabletScreen: Boolean,
    email: String = "",
    password: String = "",
    remoteUserViewModel: RemoteUserViewModel = hiltViewModel(),
) {

    val selectedGender by remoteUserViewModel.selectedGender.collectAsState()
    val loading by remoteUserViewModel.loading.collectAsState()
    val error by remoteUserViewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        // Title
        Text(
            text = "What's your gender?",
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.padding(top = 32.dp)
        )

        // Stepper (1/3) with clickable bubbles

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentStep = 2 // This can be dynamic based on the current route
            repeat(3) { index ->
                val stepNumber = index + 1
                val isActive = stepNumber == currentStep
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(
                            color = if (isActive) Color.Black else Color.LightGray,
                            shape = RoundedCornerShape(32.dp)
                        )
                        .then(
                            if (stepNumber <= currentStep) {
                                Modifier.clickable {
                                    when (stepNumber) {
                                        1 -> navController.navigate("${AppScreen.Register.route}/${email.encodeToUri()}/${password.encodeToUri()}")
                                        2 -> navController.navigate("${AppScreen.GenderScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}/${selectedGender}")
                                    }
                                }
                            } else {
                                Modifier // no click
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "$stepNumber",
                        color = Color.White,
                        fontSize = 12.sp
                    )
                }
                if (index < 2) { // Draw line between steps
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }

        // Gender Options
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Male Option
            GenderOption(
                gender = "Male",
                isSelected = selectedGender == "Male",
                icon = Icons.Default.Male,
                tableScreen = tabletScreen ,
                onClick = { remoteUserViewModel.selectGender("Male") }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Female Option
            GenderOption(
                gender = "Female",
                isSelected = selectedGender == "Female",
                icon = Icons.Default.Female,
                tableScreen = tabletScreen,
                onClick = { remoteUserViewModel.selectGender("Female") }
            )
        }

        // Error Message
        error?.let {
            Text(
                text = "Error: $it",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        val coroutine = rememberCoroutineScope()

        // Next Button
        Button(
            onClick = {
                coroutine.launch {
                    selectedGender?.let { navController.navigate("${AppScreen.WelcomeRegristerScreen.route}/${email.encodeToUri()}/${password.encodeToUri()}/${it.encodeToUri()}") }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            border = BorderStroke(
                width = 2.dp,
                brush = Brush.linearGradient(
                    colors = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
                    start = Offset(0f, 0f),
                    end = Offset(1000f, 1000f)
                )
            ),
            enabled = !loading
        ) {
            Text(
                text = "Next",
                color = Color.Black,
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun GenderOption(
    gender: String,
    isSelected: Boolean,
    icon: ImageVector,
    tableScreen: Boolean,
    mobileScreen: Boolean = false,
    onClick: () -> Unit
) {
    // If it's mobile screen, prioritize it and skip everything else
    if (mobileScreen) {
        Box(
            modifier = Modifier
                .size(120.dp)
                .clickable { onClick() }
                .background(
                    color = if (isSelected) Color(0xFFE5E7EB) else Color.Transparent,
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = icon,
                    contentDescription = gender,
                    modifier = Modifier.size(64.dp),
                    tint = Color.Black
                )
                Text(
                    text = gender,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
                if (isSelected) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = "Selected Nonlinear",
                        tint = Color.Green,
                        modifier = Modifier
                            .size(24.dp)
                            .align(Alignment.End)
                            .offset(x = 16.dp, y = (-16).dp)
                    )
                }
            }
        }
        return // 👉 Skip rendering anything else
    }

    // Tablet or default screen logic
    val boxSize = if (tableScreen) 250.dp else 300.dp
    val iconSize = if (tableScreen) 120.dp else 100.dp

    Box(
        modifier = Modifier
            .size(boxSize)
            .clickable { onClick() }
            .background(
                color = if (isSelected) Color(0xFFE5E7EB) else Color.Transparent,
                shape = CircleShape
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = icon,
                contentDescription = gender,
                modifier = Modifier.size(iconSize),
                tint = Color.Black
            )
            Text(
                text = gender,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 8.dp)
            )
            if (isSelected) {
                Icon(
                    imageVector = Icons.Default.Check,
                    contentDescription = "Selected Nonlinear",
                    tint = Color.Green,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.End)
                        .offset(x = 16.dp, y = (-16).dp)
                )
            }
        }
    }
}
