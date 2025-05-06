package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.DisposableEffect
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.Male
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.util.Log
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.GenderOption
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.database.remote.viewmodel.RemoteUserViewModel
import com.bookblitzpremium.upcomingproject.data.model.SignupState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun GenderMobileVersion(
    navController: NavController,
    email: String,
    password:String,
    onClick: () -> Unit = {},
    remoteUserViewModel: RemoteUserViewModel = hiltViewModel(),
    viewModel: AuthViewModel = hiltViewModel()
){
    val selectedGender by remoteUserViewModel.selectedGender.collectAsState()
    val loading by remoteUserViewModel.loading.collectAsState()
    val error by remoteUserViewModel.error.collectAsState()

    val signupState by viewModel.signupState.collectAsState()
    var toastMessage by remember { mutableStateOf<String?>(null) }
    var toastTrigger by remember { mutableStateOf(0) }
    var triggerSignup by rememberSaveable { mutableStateOf(false) }
    var signupJob by remember { mutableStateOf<Job?>(null) }
    val coroutineScope = rememberCoroutineScope()

    val context = LocalContext.current

    // Show Toast for errors
    LaunchedEffect(toastMessage, toastTrigger) {
        toastMessage?.let { message ->
            withContext(Dispatchers.Main) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            }
            toastMessage = null
        }
    }

    // Handle signup state changes
    LaunchedEffect(triggerSignup, signupState) {
        if (triggerSignup) {
            Log.d("GenderSelectionScreen", "Triggering signup with gender: $selectedGender")
            triggerSignup = false
            signupJob = viewModel.performSignup(email, password, selectedGender ?: "")
        }

        when (signupState) {
            is SignupState.Success -> {
                withContext(Dispatchers.Main) {
                    navController.navigate(AppScreen.Home.route) {
                        popUpTo(AppScreen.Login.route) { inclusive = true }
                    }
                }
                viewModel.clearSignUpState()
            }
            is SignupState.Error -> {
                toastMessage = (signupState as SignupState.Error).message
                toastTrigger++
                viewModel.clearSignUpState()
            }
            else -> {}
        }
    }

    // Cancel signup job when composable is disposed
    DisposableEffect(Unit) {
        onDispose {
            signupJob?.cancel()
            signupJob = null
            viewModel.clearSignUpState()
        }
    }


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

        var userID = FirebaseAuth.getInstance().currentUser?.uid.toString()

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
                tableScreen = false ,
                mobileScreen = true,
                onClick = { remoteUserViewModel.selectGender("Male") }
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Female Option
            GenderOption(
                gender = "Female",
                isSelected = selectedGender == "Female",
                icon = Icons.Default.Female,
                tableScreen = false,
                mobileScreen = true,
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

        val conrotine = rememberCoroutineScope()

        // Next Button
        Button(
            onClick = {
                conrotine.launch {
                    if (selectedGender != null) {
                        triggerSignup = true
                    } else {
                        remoteUserViewModel.setError("Please select a gender")
                    }
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
