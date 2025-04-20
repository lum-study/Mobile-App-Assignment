//package com.bookblitzpremium.upcomingproject.ui.screen.auth
//
//import android.annotation.SuppressLint
//import android.content.pm.PackageManager
//import android.os.Build
//import android.widget.Toast
//import androidx.activity.compose.rememberLauncherForActivityResult
//import androidx.activity.result.contract.ActivityResultContracts
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.*
//import androidx.compose.foundation.shape.CircleShape
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.*
//import androidx.compose.runtime.*
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.graphics.Color
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.platform.LocalContext
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.TextStyle
//import androidx.compose.ui.text.font.FontWeight
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.compose.ui.unit.sp
//import androidx.core.content.ContextCompat
//import androidx.navigation.NavController
//import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
//import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
//import com.bookblitzpremium.upcomingproject.data.model.PasswordResetState
//
//@SuppressLint("ObsoleteSdkInt")
//@Composable
//fun EmailVerificationScreen(
//    viewModel: AuthViewModel,
//    navController: NavController,
//    modifier: Modifier = Modifier
//) {
//    val context = LocalContext.current
//    val userDetails by viewModel.userDetails.collectAsState()
//    val passwordResetState by viewModel.passwordResetState.collectAsState()
//
//    // Launcher to request permission
//    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
//        if (!isGranted) {
//            Toast.makeText(context, "Please allow notifications", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    // Check if permission is granted (for UI feedback)
//    val permissionGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//        ContextCompat.checkSelfPermission(
//            context,
//            android.Manifest.permission.POST_NOTIFICATIONS
//        ) == PackageManager.PERMISSION_GRANTED
//    } else {
//        true // Not required for pre-Android 13
//    }
//
//    // State to control whether to show the custom permission screen
//    var showPermissionScreen by remember { mutableStateOf(false) }
//
//    // Automatically check permission and show custom screen if needed
//    LaunchedEffect(Unit) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !permissionGranted) {
//            showPermissionScreen = true
//        }
//    }
//
//    Box(
//        modifier = modifier.fillMaxSize()
//    ) {
//        // Main email verification screen content
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Spacer(modifier = Modifier.height(70.dp))
//
//            Box(
//                modifier = Modifier
//                    .size(280.dp)
//                    .clip(CircleShape)
//                    .background(Color.Gray)
//            ) {
////                Image(
////                    painter = painterResource(""), // Reuse the same image for consistency
////                    contentDescription = null,
////                    contentScale = ContentScale.Crop,
////                    modifier = Modifier.fillMaxSize()
////                )
//            }
//
//            Text(
//                text = "Verify Your Email",
//                style = TextStyle(
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Black
//                ),
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(vertical = 30.dp)
//            )
//
//            Text(
//                text = "Email: ${userDetails?.email ?: "Not available"}",
//                style = TextStyle(
//                    fontSize = 16.sp,
//                    color = Color.Black
//                ),
//                textAlign = TextAlign.Center,
//                modifier = Modifier.padding(bottom = 16.dp)
//            )
//
//            if (userDetails?.isEmailVerified == true) {
//                Text(
//                    text = "Your email is verified!",
//                    color = Color.Green,
//                    fontSize = 16.sp,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(bottom = 16.dp)
//                )
//                Button(
//                    onClick = {
//                        navController.navigate(AppScreen.Register.route) {
//                            popUpTo(0) {
//                                inclusive = true
//                            }
//                            launchSingleTop = true
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 12.dp, vertical = 16.dp)
//                        .height(48.dp),
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7))
//                ) {
//                    Text("Continue to Home", color = Color.White)
//                }
//            } else {
//                Button(
//                    onClick = {
//                        if (permissionGranted) {
//                            val user = viewModel.getCurrentUser()
//                            viewModel.verifyEmailAddress(user)
//                        } else {
//                            Toast.makeText(
//                                context,
//                                "Please allow notifications",
//                                Toast.LENGTH_SHORT
//                            ).show()
//                            showPermissionScreen = true
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 12.dp, vertical = 16.dp)
//                        .height(48.dp),
//                    enabled = passwordResetState != PasswordResetState.Loading,
//                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7))
//                ) {
//                    Text("Send Verification Link", color = Color.White)
//                }
//
//                TextButton(
//                    onClick = {
//                        viewModel.checkAuthStatus() // Refresh user state to check if email is verified
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 12.dp)
//                ) {
//                    Text(
//                        "I’ve verified my email",
//                        color = Color(0xFF673AB7),
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//
//                TextButton(
//                    onClick = {
//                        viewModel.signOut()
//                        viewModel.checkAuthStatus()
//                        navController.navigate(AppScreen.Login.route) {
//                            popUpTo(0) {
//                                inclusive = true
//                            }
//                            launchSingleTop = true
//                        }
//                    },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 12.dp)
//                ) {
//                    Text(
//                        "Sign Out",
//                        color = Color.Gray,
//                        fontSize = 16.sp,
//                        fontWeight = FontWeight.Medium
//                    )
//                }
//            }
//
//            // Display the state of the email verification operation
//            LaunchedEffect(passwordResetState) {
//                when (passwordResetState) {
//                    PasswordResetState.Success -> {
//                        Toast.makeText(
//                            context,
//                            "Verification link sent! Check your email.",
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                    is PasswordResetState.Error -> {
//                        Toast.makeText(
//                            context,
//                            (passwordResetState as PasswordResetState.Error).message,
//                            Toast.LENGTH_LONG
//                        ).show()
//                    }
//                    else -> {}
//                }
//            }
//
//            // Show instructions or error message
//            when (passwordResetState) {
//                PasswordResetState.Success -> {
//                    Text(
//                        text = "A verification link has been sent to your email. Please check your inbox (and spam/junk folder).",
//                        color = Color.Green,
//                        fontSize = 16.sp,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
//                is PasswordResetState.Error -> {
//                    Text(
//                        text = (passwordResetState as PasswordResetState.Error).message,
//                        color = Color.Red,
//                        fontSize = 16.sp,
//                        textAlign = TextAlign.Center,
//                        modifier = Modifier.padding(top = 16.dp)
//                    )
//                }
//                else -> {}
//            }
//        }
//
//        // Show the custom permission screen if needed
//        if (showPermissionScreen) {
//            CustomPermissionScreen(
//                onRequestPermission = {
//                    launcher.launch(android.Manifest.permission.POST_NOTIFICATIONS)
//                    showPermissionScreen = false
//                },
//                onDeny = {
//                    showPermissionScreen = false
//                    Toast.makeText(context, "Please allow notifications", Toast.LENGTH_SHORT).show()
//                }
//            )
//        }
//    }
//}
//
////@Composable
////fun CustomPermissionScreen(
////    onRequestPermission: () -> Unit,
////    onDeny: () -> Unit
////) {
////    Surface(
////        modifier = Modifier.fillMaxSize(),
////        color = Color.Black.copy(alpha = 0.8f)
////    ) {
////        Column(
////            modifier = Modifier
////                .fillMaxSize()
////                .padding(16.dp),
////            horizontalAlignment = Alignment.CenterHorizontally,
////            verticalArrangement = Arrangement.Center
////        ) {
////            Card(
////                modifier = Modifier
////                    .fillMaxWidth()
////                    .padding(horizontal = 16.dp),
////                shape = RoundedCornerShape(16.dp),
////                colors = CardDefaults.cardColors(containerColor = Color.White)
////            ) {
////                Column(
////                    modifier = Modifier
////                        .fillMaxWidth()
////                        .padding(24.dp),
////                    horizontalAlignment = Alignment.CenterHorizontally
////                ) {
////                    Text(
////                        text = "We Need Your Permission",
////                        fontSize = 24.sp,
////                        fontWeight = FontWeight.Bold,
////                        color = Color.Black,
////                        textAlign = TextAlign.Center,
////                        modifier = Modifier.padding(bottom = 16.dp)
////                    )
////
////                    Text(
////                        text = "To send you email verification notifications, we need permission to send notifications. Please allow notifications to proceed.",
////                        fontSize = 16.sp,
////                        color = Color.Black,
////                        textAlign = TextAlign.Center,
////                        modifier = Modifier.padding(bottom = 32.dp)
////                    )
////
////                    Button(
////                        onClick = onRequestPermission,
////                        modifier = Modifier
////                            .fillMaxWidth()
////                            .height(48.dp),
////                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF673AB7))
////                    ) {
////                        Text("Grant Permission", color = Color.White)
////                    }
////
////                    Spacer(modifier = Modifier.height(16.dp))
////
////                    TextButton(
////                        onClick = onDeny,
////                        modifier = Modifier.fillMaxWidth()
////                    ) {
////                        Text(
////                            "Deny",
////                            color = Color.Gray,
////                            fontSize = 16.sp,
////                            fontWeight = FontWeight.Medium
////                        )
////                    }
////                }
////            }
////        }
////    }
////}