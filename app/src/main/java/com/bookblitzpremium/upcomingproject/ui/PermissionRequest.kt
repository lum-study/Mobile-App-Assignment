package com.bookblitzpremium.upcomingproject.ui

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@Composable
fun RequestNotificationPermissions(
    onPermissionGranted: (Boolean) -> Unit
) {
    // State to track whether notification permission is granted
    var hasNotificationPermission by remember { mutableStateOf(false) }

    // Create the launcher to request permission and handle the result
    val permissionResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            hasNotificationPermission = granted
            onPermissionGranted(granted)  // Pass the result to the callback
        }
    )

    // Request notification permission when the component is launched
    LaunchedEffect(key1 = true) {
        permissionResult.launch(Manifest.permission.POST_NOTIFICATIONS)
    }

    if (hasNotificationPermission) {
        // You can now show the notification or proceed with related tasks
        Text("Permission granted. You can now send notifications.")
    } else {
        Text("Notification permission is required to send notifications.")
    }

}
