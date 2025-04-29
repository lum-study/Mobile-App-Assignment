package com.bookblitzpremium.upcomingproject.TabletAuth


import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.launch

data class GenderField(
    val tabletScreen: Boolean,
    val selectedGender: String = "male",
    val email: String = "",
    val password: String = "",
)

@Composable
fun GenderSelectionScreen(
    remoteUserViewModel: RemoteUserViewModel,
    navController: NavController,
    gender: GenderField,
    onClick: () -> Unit
) {
    var selectedGender by rememberSaveable { mutableStateOf(gender.selectedGender ?: "") }

    val current = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Select Your Gender",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            ),
            modifier = Modifier.padding(top = 16.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Stepper (2/3)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            val currentStep = 2
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
                                        1 -> navController.navigate("step1/${gender.email.encodeToUri()}/${gender.password.encodeToUri()}")
                                        2 -> navController.navigate("gender/${gender.email.encodeToUri()}/${gender.password.encodeToUri()}/${(selectedGender).encodeToUri()}")
                                        3 -> {
                                            if (selectedGender.isNotEmpty()) {
                                                navController.navigate("step2/${gender.email.encodeToUri()}/${gender.password.encodeToUri()}/${(selectedGender).encodeToUri()}")
                                            } else {
                                                Toast.makeText(current, "Please select a gender first", Toast.LENGTH_SHORT).show()
                                            }
                                        }
                                    }
                                }
                            } else {
                                Modifier
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
                if (index < 2) {
                    Box(
                        modifier = Modifier
                            .width(24.dp)
                            .height(2.dp)
                            .background(Color.LightGray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Gender Selection Options
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { selectedGender = "Male" },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGender == "Male") Color.Black else Color.LightGray
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Male",
                    color = if (selectedGender == "Male") Color.White else Color.Black,
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp))

            Button(
                onClick = { selectedGender = "Female" },
                modifier = Modifier
                    .weight(1f)
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedGender == "Female") Color.Black else Color.LightGray
                ),
                shape = RoundedCornerShape(24.dp)
            ) {
                Text(
                    text = "Female",
                    color = if (selectedGender == "Female") Color.White else Color.Black,
                    fontSize = 16.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (selectedGender.isEmpty()) {
                    Toast.makeText(current, "Please select a gender", Toast.LENGTH_SHORT).show()
                } else {
                    onClick()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Black
            ),
            shape = RoundedCornerShape(24.dp)
        ) {
            Text(
                text = "Next",
                color = Color.White,
                fontSize = 16.sp
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
