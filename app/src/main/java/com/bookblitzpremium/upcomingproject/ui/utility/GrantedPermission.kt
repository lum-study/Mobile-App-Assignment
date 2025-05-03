package com.bookblitzpremium.upcomingproject.ui.utility

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

object PermissionUtils {
    fun arePermissionsGranted(context: Context): Boolean {
        val notificationGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else {
            true // No runtime permission needed below API 33
        }
        val locationGranted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        return notificationGranted && locationGranted
    }


    @Composable
    fun RequestPermissionDialog(
        onPermissionsGranted: () -> Unit = {},
        onDismiss: () -> Unit = {}
    ) {
        val context = LocalContext.current
        var showDialog by remember { mutableStateOf(false) }


        val notificationPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            showDialog = !arePermissionsGranted(context)
            if (!showDialog) onPermissionsGranted()
        }


        val locationPermissionLauncher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) {
            showDialog = !arePermissionsGranted(context)
            if (!showDialog) onPermissionsGranted()
        }


        LaunchedEffect(Unit) {
            showDialog = !arePermissionsGranted(context)
        }


        if (showDialog) {
            AlertDialog(
                onDismissRequest = {
                    showDialog = false
                    onDismiss()
                },
                title = { Text("Permissions Required") },
                text = {
                    Text(
                        "This app requires notification and location permissions to provide booking updates and map features. Please allow these permissions to continue."
                    )
                },
                confirmButton = {
                    TextButton(onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                            ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.POST_NOTIFICATIONS
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.ACCESS_FINE_LOCATION
                            ) != PackageManager.PERMISSION_GRANTED
                        ) {
                            locationPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                        }
                    }) {
                        Text("Allow")
                    }
                },
                dismissButton = {
                    TextButton(onClick = {
                        showDialog = false
                        onDismiss()
                    }) {
                        Text("Dismiss")
                    }
                }
            )
        }
    }
}

