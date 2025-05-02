package com.bookblitzpremium.upcomingproject

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.data.model.LineType
import com.bookblitzpremium.upcomingproject.ui.utility.MapViewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


@Composable
fun LocationPermissionWrapper(content: @Composable () -> Unit) {
    val context = LocalContext.current
    var hasPermission by remember { mutableStateOf(checkForPermission(context)) }

    if (!hasPermission) {
        LocationPermissionScreen {
            hasPermission = true
        }
    } else {
        content()
    }
}

fun checkForPermission(context: Context): Boolean {
    return ActivityCompat.checkSelfPermission(
        context,
        android.Manifest.permission.ACCESS_FINE_LOCATION
    ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                context,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
}

@Composable
fun LocationPermissionScreen(
    onPermissionGranted: () -> Unit
) {
    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (allGranted) {
            onPermissionGranted()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Location Permission Required",
            fontSize = 20.sp
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                locationPermissionLauncher.launch(
                    arrayOf(
                        android.Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    )
                )
            }
        ) {
            Text("Grant Permission")
        }
    }
}


@Composable
fun MapScreen(
    addressInput: String,
    navController: NavController,
    viewModel: MapViewModel = viewModel(),
) {
    val context = LocalContext.current
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val locationResult by viewModel.locationResult.collectAsState()
    var mapProperties by remember { mutableStateOf(MapProperties()) }
    var changeIcon by remember { mutableStateOf(false) }
    var lineType by remember { mutableStateOf(LineType.SOLID) }

    // Automatically trigger getLatLngFromAddress when screen is composed
    LaunchedEffect(addressInput) {
        viewModel.getLatLngFromAddress(
            context = context,
            addressInput = addressInput,
            addressFilter = { addr -> addr.locality ?: addr.getAddressLine(0) }
        )
    }

    LocationPermissionWrapper {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top // Align to top for back button
        ) {
            // Back button
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.Start)
            ) {
                Text("Back")
            }
            Spacer(modifier = Modifier.height(16.dp))

            // Loading state
            if (loading) {
                CircularProgressIndicator()
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Loading location data...",
                    fontSize = 20.sp
                )
            }
            // Error state
            else if (error != null) {
                Text(
                    text = error ?: "Unknown error",
                    fontSize = 20.sp,
                    color = androidx.compose.ui.graphics.Color.Red
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(
                    onClick = {
                        viewModel.getLatLngFromAddress(
                            context = context,
                            addressInput = addressInput,
                            addressFilter = { addr ->
                                addr.locality ?: addr.getAddressLine(0)
                            }
                        )
                    }
                ) {
                    Text("Retry: Show Map to $addressInput")
                }
            }
            // Success state
            else if (locationResult != null) {
                locationResult?.let { (currentLatLng, destLatLng, addressText) ->
                    MyMap(
                        context = context,
                        currentLatLng = currentLatLng,
                        destLatLng = destLatLng,
                        mapProperties = mapProperties,
                        lineType = lineType,
                        changeIcon = changeIcon,
                        onChangeMarkerIcon = { changeIcon = !changeIcon },
                        onChangeMapType = { mapProperties = mapProperties.copy(mapType = it) },
                        onChangeLineType = { lineType = it }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Destination: ${addressText ?: "Unknown"}",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MyMap(
    context: Context,
    currentLatLng: LatLng,
    destLatLng: LatLng,
    mapProperties: MapProperties,
    lineType: LineType,
    changeIcon: Boolean,
    onChangeMarkerIcon: () -> Unit,
    onChangeMapType: (MapType) -> Unit,
    onChangeLineType: (LineType) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        val midLat = (currentLatLng.latitude + destLatLng.latitude) / 2
        val midLng = (currentLatLng.longitude + destLatLng.longitude) / 2
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(
            LatLng(midLat, midLng), 12f
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            cameraPositionState = cameraPositionState,
            properties = mapProperties
        ) {
            Marker(
                state = MarkerState(position = currentLatLng),
                title = "Current Location",
                icon = if (changeIcon) {
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)
                } else {
                    BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
                }
            )

            Marker(
                state = MarkerState(position = destLatLng),
                title = "Destination",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )

            Polyline(
                points = listOf(currentLatLng, destLatLng),
                color = Color.Black, // Blue color (fixed)
                width = 5f,
                pattern = when (lineType) {
                    LineType.SOLID -> null
                    LineType.DASHED -> listOf(Dash(20f), Gap(10f))
                    LineType.NONE -> null
                }
            )
        }

        Button(onClick = onChangeMarkerIcon) {
            Text("Toggle Current Marker Icon")
        }
        Button(onClick = {
            onChangeMapType(if (mapProperties.mapType == MapType.NORMAL) MapType.SATELLITE else MapType.NORMAL)
        }) {
            Text("Toggle Map Type")
        }
        Button(onClick = {
            onChangeLineType(when (lineType) {
                LineType.SOLID -> LineType.DASHED
                LineType.DASHED -> LineType.NONE
                LineType.NONE -> LineType.SOLID
            })
        }) {
            Text("Toggle Line Type")
        }
    }
}