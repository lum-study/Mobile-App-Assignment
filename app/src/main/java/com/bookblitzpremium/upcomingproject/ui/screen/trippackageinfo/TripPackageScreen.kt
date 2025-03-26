package com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.outlined.Flight
import androidx.compose.material.icons.outlined.HomeWork
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.model.TripPackage
import com.bookblitzpremium.upcomingproject.ui.screen.hotel.DynamicHotelDetails
import com.bookblitzpremium.upcomingproject.ui.screen.travel.TravelHeaderTable
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Preview(showBackground = true, widthDp = 1440, heightDp = 900)
@Composable
fun TripPackageScreen() {
    TripPackageScreen(rememberNavController())
}

@Composable
fun TripPackageScreen(navController: NavController) {
    val tripPackage =
        TripPackage(R.drawable.green_mountain, "Trip to Bali", "Enjoy a relaxing vacation")
    val description =
        "Blue Lagoon Drive from Reykjavík, the capital of Iceland, to the southeast for about an houryou can reach Blue Lagoon, the famous"
    val availableSlot = 50
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass


    AppTheme {
        if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.MEDIUM) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Image(
                        painterResource(
                            id = tripPackage.imageResource
                        ),
                        contentDescription = tripPackage.packageTitle,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )
                    Text(
                        text = description,
                        style = AppTheme.typography.smallRegular,
                        color = Color.Gray,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                    Text(
                        text = stringResource(R.string.available_slot, availableSlot),
                        style = AppTheme.typography.mediumSemiBold,
                        modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.information),
                        style = AppTheme.typography.mediumSemiBold,
                    )
                    InformationData(
                        imageVector = Icons.Outlined.Task,
                        title = stringResource(R.string.schedule),
                        description = "Stay tuned Stay tuned Stay tunedStay tuned Stay tuned Stay tuned Stay tuned Stay tuned Stay tuned",
                        onRowClick = { navController.navigate(AppScreen.Schedule.route) }
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                    )
                    InformationData(
                        imageVector = Icons.Outlined.Flight,
                        title = stringResource(R.string.flight),
                        description = "Stay tuned Stay tuned Stay tunedStay tuned Stay tuned Stay tuned Stay tuned Stay tuned Stay tuned",
                        rotateDeg = 45f,
                        onRowClick = { navController.navigate(AppScreen.Flight.route) }
                    )
                    Divider(
                        color = Color.Gray,
                        thickness = 1.dp,
                    )
                    InformationData(
                        imageVector = Icons.Outlined.HomeWork,
                        title = stringResource(R.string.hotel),
                        description = "Stay tuned Stay tuned Stay tunedStay tuned Stay tuned Stay tuned Stay tuned Stay tuned Stay tuned",
                        onRowClick = { navController.navigate(AppScreen.Hotel.route) }
                    )
                    Button(
                        onClick = {},
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(R.string.apply_button)
                        )
                    }
                }
            }
        }
        else if (windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.EXPANDED) {
            val configuration = LocalConfiguration.current
            val isPortrait = configuration.screenWidthDp < configuration.screenHeightDp
            TravelHeaderTable()
        }
    }
}

@Composable
fun InformationData(
    modifier: Modifier = Modifier,
    imageVector: ImageVector,
    title: String,
    description: String = "",
    rotateDeg: Float = 0f,
    onRowClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                onRowClick()
            },
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .background(Color.White) // Light pink
        ) {
            Icon(
                imageVector = imageVector,
                contentDescription = stringResource(R.string.back_button),
                modifier = Modifier
                    .rotate(rotateDeg)
                    .size(32.dp),
                tint = Color.Black
            )
        }
        if (description.isNotEmpty()) {
            Column(
                modifier = Modifier
                    .padding(start = 12.dp)
                    .weight(1f)
            ) {
                Text(
                    text = title,
                    style = AppTheme.typography.mediumSemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = description,
                    style = AppTheme.typography.smallRegular,
                    modifier = Modifier.fillMaxWidth(),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
        } else {
            Text(
                text = title,
                style = AppTheme.typography.mediumNormal,
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 12.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = stringResource(R.string.next_button),
            tint = Color.LightGray,
        )
    }
}