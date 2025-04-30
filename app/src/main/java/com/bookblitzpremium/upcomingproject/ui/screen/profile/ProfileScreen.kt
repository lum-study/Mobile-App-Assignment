package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.AddCard
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userName: String,
    onBackClick: () -> Unit = {},
    onMenuItemClick: (String) -> Unit = {}
) {
    val hotelId = "hotel123"
    val windowSizeClass = LocalConfiguration.current.screenWidthDp
    val isTabletPortrait = windowSizeClass in 601..1279
    val isTabletLandscape = windowSizeClass >= 1280

    AppTheme {
        when {
            isTabletLandscape -> TabletProfileScreen(navController, userName, onBackClick, onMenuItemClick, hotelId)
            isTabletPortrait -> TabletProfileScreen(navController, userName, onBackClick, onMenuItemClick, hotelId)
            else -> PhoneProfileScreen(navController, userName, onBackClick, onMenuItemClick, hotelId)
        }
    }
}

@Composable
fun TabletProfileScreen(
    navController: NavHostController,
    userName: String,
    onBackClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    hotelId: String
) {
    Row(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        Box(
            modifier = Modifier
                .weight(1f)
                .padding(16.dp)
        ) {
            Column {
                IconButton(onClick = onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back",
                        tint = Color.Black
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                ProfileMenuItems(onMenuItemClick, hotelId, navController)
            }
        }

        Divider(
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp),
            color = Color.LightGray
        )

        Column(
            modifier = Modifier
                .weight(2f)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Box(modifier = Modifier.height(50.dp))
            ProfileHeader(tabletMode = true, userName = userName, onBackClick = onBackClick)
        }
    }
}

@Composable
fun PhoneProfileScreen(
    navController: NavHostController,
    userName: String,
    onBackClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    hotelId: String
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(userName = userName, onBackClick = onBackClick)
        ProfileMenuItems(onMenuItemClick, hotelId, navController)
    }
}

@Composable
fun ProfileHeader(tabletMode: Boolean = false, userName: String, onBackClick: () -> Unit) {
    Text(
        text = "Profile",
        style = AppTheme.typography.largeBold,
        textAlign = TextAlign.Center,
        modifier = Modifier.fillMaxWidth()
    )

    Spacer(modifier = Modifier.height(8.dp))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.beach),
            contentDescription = "Profile picture",
            modifier = Modifier
                .size(if (tabletMode) 150.dp else 100.dp)
                .clip(RoundedCornerShape(100.dp)),
            contentScale = ContentScale.Crop
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = userName,
            fontSize = if (tabletMode) 28.sp else 24.sp,
            style = AppTheme.typography.mediumBold
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ProfileMenuItems(onMenuItemClick: (String) -> Unit, hotelId: String, navController: NavHostController) {
    val menuItems = listOf(
        "Edit Profile" to Icons.Outlined.Person,
        "Payment Methods" to Icons.Outlined.AddCard,
        "My Orders" to Icons.Outlined.Task,
        "Ratings" to Icons.Default.Star,
        "Log out" to Icons.Default.Logout
    )

    Column(modifier = Modifier.fillMaxWidth()) {
        menuItems.forEach { (item, iconRes) ->
            ProfileMenuItem(
                text = item,
                iconRes = iconRes,
                onClick = {
                    onMenuItemClick(item)
                }
            )
            Divider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = Color.LightGray,
                thickness = 1.dp
            )
        }
    }
}

@Composable
fun ProfileMenuItem(
    text: String,
    iconRes: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = iconRes,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 16.sp,
            modifier = Modifier.weight(1f)
        )

        Icon(
            imageVector = Icons.Default.KeyboardArrowRight,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = Color.Gray
        )
    }
}

@Preview(showBackground = true, device = "spec:width=411dp,height=891dp")
@Composable
fun PhoneProfilePreview() {
    val navController = rememberNavController()
    ProfileScreen(
        navController = navController,
        userName = "John Doe",
        onBackClick = {},
        onMenuItemClick = {}
    )
}

@Preview(showBackground = true, device = "spec:width=800dp,height=1280dp")
@Composable
fun TabletPortraitProfilePreview() {
    val navController = rememberNavController()
    ProfileScreen(
        navController = navController,
        userName = "John Doe",
        onBackClick = {},
        onMenuItemClick = {}
    )
}

@Preview(showBackground = true, device = "spec:width=1280dp,height=800dp")
@Composable
fun TabletLandscapeProfilePreview() {
    val navController = rememberNavController()
    ProfileScreen(
        navController = navController,
        userName = "John Doe",
        onBackClick = {},
        onMenuItemClick = {}
    )
}