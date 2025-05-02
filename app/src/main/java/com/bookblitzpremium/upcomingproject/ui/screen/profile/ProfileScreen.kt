package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Female
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Male
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Task
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.common.enums.Gender
import com.bookblitzpremium.upcomingproject.data.database.local.entity.User
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.LocalUserViewModel
import com.bookblitzpremium.upcomingproject.ui.components.Base64Image
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun ProfileScreen(
    navController: NavHostController,
    userName: String,
    onBackClick: () -> Unit = {},
    onMenuItemClick: (String) -> Unit = {},
) {
    val hotelId = "hotel123"
    val windowSizeClass = LocalConfiguration.current.screenWidthDp
    val isTabletPortrait = windowSizeClass in 601..1279
    val isTabletLandscape = windowSizeClass >= 1280

    val localUserViewModel: LocalUserViewModel = hiltViewModel()
    val userID = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    var userInfo by remember { mutableStateOf<User?>(null) }
    LaunchedEffect(userID) {
        userInfo = localUserViewModel.getUserByID(userID)
    }

    AppTheme {
        when {
            isTabletLandscape -> TabletProfileScreen(
                navController,
                userName,
                onBackClick,
                onMenuItemClick,
                hotelId,
                userInfo
            )

            isTabletPortrait -> TabletProfileScreen(
                navController,
                userName,
                onBackClick,
                onMenuItemClick,
                hotelId,
                userInfo
            )

            else -> PhoneProfileScreen(
                navController,
                userName,
                onBackClick,
                onMenuItemClick,
                hotelId,
                userInfo
            )
        }
    }
}

@Composable
fun TabletProfileScreen(
    navController: NavHostController,
    userName: String,
    onBackClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    hotelId: String,
    userInfo: User?,
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
            ProfileHeader(tabletMode = true, userInfo = userInfo, onBackClick = onBackClick)
        }
    }
}

@Composable
fun PhoneProfileScreen(
    navController: NavHostController,
    userName: String,
    onBackClick: () -> Unit,
    onMenuItemClick: (String) -> Unit,
    hotelId: String,
    userInfo: User?,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(0.dp)
            .verticalScroll(rememberScrollState())
    ) {
        ProfileHeader(userInfo = userInfo, onBackClick = onBackClick)
        ProfileMenuItems(onMenuItemClick, hotelId, navController)
    }
}

@Composable
fun ProfileHeader(tabletMode: Boolean = false, userInfo: User?, onBackClick: () -> Unit) {
    val iconImage = userInfo?.iconImage ?: ""
    val gender = userInfo?.gender ?: ""
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
        if (iconImage.isNotEmpty()) {
            Base64Image(
                base64String = iconImage,
                modifier = Modifier
                    .size(if (tabletMode) 150.dp else 100.dp)
                    .clip(RoundedCornerShape(100.dp)),
                contentScale = ContentScale.Crop
            )
        } else {
            Image(
                imageVector = if (gender == Gender.Male.title) Icons.Default.Male else Icons.Default.Female,
                contentDescription = "Profile picture",
                modifier = Modifier
                    .size(if (tabletMode) 150.dp else 100.dp)
                    .clip(RoundedCornerShape(100.dp)),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = userInfo?.name ?: "",
            fontSize = if (tabletMode) 28.sp else 24.sp,
            style = AppTheme.typography.mediumBold
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
fun ProfileMenuItems(
    onMenuItemClick: (String) -> Unit,
    hotelId: String,
    navController: NavHostController,
) {
    val menuItems = listOf(
        "Edit Profile" to Icons.Outlined.Person,
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
    onClick: () -> Unit,
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