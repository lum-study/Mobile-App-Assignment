package com.bookblitzpremium.upcomingproject

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import androidx.annotation.DrawableRes
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.data.model.LineType
import com.bookblitzpremium.upcomingproject.ui.components.CheckStatusLoading
import com.bookblitzpremium.upcomingproject.ui.utility.MapViewModel
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState


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


@RequiresApi(Build.VERSION_CODES.P)
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
    var lineType by remember { mutableStateOf(LineType.SOLID) }

    // Automatically trigger getLatLngFromAddress when screen is composed
    LaunchedEffect(addressInput) {
        viewModel.getLatLngFromAddress(
            context = context,
            addressInput = addressInput,
            addressFilter = { addr -> addr.locality ?: addr.getAddressLine(0) }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top // Align to top for back button
    ) {
//            // Back button
//            Button(
//                onClick = { navController.popBackStack() },
//                modifier = Modifier.align(Alignment.Start)
//            ) {
//                Text("Back")
//            }
//            Spacer(modifier = Modifier.height(16.dp))


        // Loading state
        if (loading) {
            CheckStatusLoading()
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
                color = Color.Red
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
                    currentLatLng = currentLatLng,
                    destLatLng = destLatLng,
                    mapProperties = mapProperties,
                    lineType = lineType,
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



@Composable
fun BoxMaps(
    modifier : Modifier,
    addressInput: String,
    onClick: () -> Unit,
    viewModel: MapViewModel = viewModel(),
) {
    val context = LocalContext.current
    val locationResult by viewModel.locationResult.collectAsState()
    var mapProperties by remember { mutableStateOf(MapProperties()) }

    // Automatically trigger getLatLngFromAddress when screen is composed
    LaunchedEffect(addressInput) {
        viewModel.getLatLngFromAddress(
            context = context,
            addressInput = addressInput,
            addressFilter = { addr -> addr.locality ?: addr.getAddressLine(0) }
        )
    }

    Box(
        modifier = modifier
    ) {
        if (locationResult != null) {
            val (_, destLatLng) = locationResult!!
            val cameraPositionState = rememberCameraPositionState {
                // Center on destination with fixed zoom
                position = CameraPosition.fromLatLngZoom(destLatLng, 15f)
            }

            // Update camera position when destLatLng is available
            LaunchedEffect(destLatLng) {
                cameraPositionState.position = CameraPosition.fromLatLngZoom(destLatLng, 15f)
            }

            // Define the light theme map style
            val mapStyleJson = """
                [
                  {
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#f5f5f5" }
                    ]
                  },
                  {
                    "elementType": "labels.text.fill",
                    "stylers": [
                      { "color": "#3c4043" }
                    ]
                  },
                  {
                    "elementType": "labels.text.stroke",
                    "stylers": [
                      { "color": "#ffffff" }
                    ]
                  },
                  {
                    "featureType": "administrative",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#d1d1d1" }
                    ]
                  },
                  {
                    "featureType": "administrative.country",
                    "elementType": "geometry.stroke",
                    "stylers": [
                      { "color": "#a9a9a9" }
                    ]
                  },
                  {
                    "featureType": "landscape",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#e0e0e0" }
                    ]
                  },
                  {
                    "featureType": "poi",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#ffffff" }
                    ]
                  },
                  {
                    "featureType": "road",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#ffffff" }
                    ]
                  },
                  {
                    "featureType": "road",
                    "elementType": "labels.text.fill",
                    "stylers": [
                      { "color": "#3c4043" }
                    ]
                  },
                  {
                    "featureType": "road.highway",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#fdd835" }
                    ]
                  },
                  {
                    "featureType": "water",
                    "elementType": "geometry",
                    "stylers": [
                      { "color": "#c9e1f5" }
                    ]
                  },
                  {
                    "elementType": "labels.icon",
                    "stylers": [
                      { "visibility": "on" }
                    ]
                  }
                ]
            """.trimIndent()

            // Update mapProperties with the light theme style and disable interactions
            val styledMapProperties = mapProperties.copy(
                mapStyleOptions = MapStyleOptions(mapStyleJson),
            )

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = cameraPositionState,
                properties = styledMapProperties,
                onMapClick = {
                    onClick()
                }
            ) {
                MapMarker(
                    context = context,
                    position = destLatLng,
                    title = "Destination",
                    iconResourceId = R.drawable.hotel_location
                )
            }
        }else{
            CheckStatusLoading(indicatorSize = 40.dp)
        }
    }
}

@Composable
fun MyMap(
    currentLatLng: LatLng,
    destLatLng: LatLng,
    mapProperties: MapProperties,
    lineType: LineType,
    onChangeMapType: (MapType) -> Unit,
    onChangeLineType: (LineType) -> Unit
) {
    val cameraPositionState = rememberCameraPositionState {
        val midLat = (currentLatLng.latitude + destLatLng.latitude) / 2
        val midLng = (currentLatLng.longitude + destLatLng.longitude) / 2
        position = CameraPosition.fromLatLngZoom(
            LatLng(midLat, midLng), 12f
        )
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            cameraPositionState = cameraPositionState,
            properties = mapProperties
        ) {
            MapMarker(
                context = context,
                position = currentLatLng,
                title = "Current Location",
                iconResourceId =  R.drawable.current_location
            )


            MapMarker(
                context = context,
                position = destLatLng,
                title = "Destination",
                iconResourceId = R.drawable.hotel_location
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

        Row(
            modifier = Modifier
                .fillMaxWidth()
        ){
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
}


@Composable
fun MapMarker(
    context: Context,
    position: LatLng,
    title: String,
    @DrawableRes iconResourceId: Int
) {
    val icon = bitmapDescriptorFromVector(
        context, iconResourceId
    )
    Marker(
        state = MarkerState(position = position),
        title = title,
        icon = icon,
    )
}


fun bitmapDescriptorFromVector(
    context: Context,
    vectorResId: Int
): BitmapDescriptor? {


    // retrieve the actual drawable
    val drawable = ContextCompat.getDrawable(context, vectorResId) ?: return null
    drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
    val bm = Bitmap.createBitmap(
        drawable.intrinsicWidth,
        drawable.intrinsicHeight,
        Bitmap.Config.ARGB_8888
    )


    // draw it onto the bitmap
    val canvas = android.graphics.Canvas(bm)
    drawable.draw(canvas)
    return BitmapDescriptorFactory.fromBitmap(bm)
}

