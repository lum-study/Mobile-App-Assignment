package com.bookblitzpremium.upcomingproject.ui.screen.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ContentPaste
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.ui.screen.trippackageinfo.InformationData
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

//@Preview(showBackground = true, widthDp = 360, heightDp = 700)
@Composable
fun ProfileScreen(navController: NavController) {
    val authViewModel: AuthViewModel = hiltViewModel()
    val username = "Esther Howard"
    AppTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(end = 16.dp, bottom = 8.dp, start = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally

        ) {
                ProfileImage()
                Text(
                    text = username,
                    style = AppTheme.typography.largeSemiBold,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            Column {
                InformationData(
                    imageVector = Icons.Outlined.PersonOutline,
                    title = "My Profile",
                    onRowClick = { navController.navigate(AppScreen.EditProfile.route) },
                    modifier = Modifier.height(70.dp),
                )
                HorizontalDivider()
                InformationData(
                    imageVector = Icons.Outlined.ContentPaste,
                    title = "My Order",
                    onRowClick = {},
                    modifier = Modifier.height(70.dp),
                )
                HorizontalDivider()
                InformationData(
                    imageVector = Icons.Outlined.Logout,
                    title = "Log Out",
                    onRowClick = { authViewModel.signOut() },
                    modifier = Modifier.height(70.dp),
                )
            }
        }
    }
}

@Composable
fun ProfileImage() {
    Box(
        modifier = Modifier.size(104.dp)
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.malaysia_flag),
//            contentDescription = stringResource(R.string.country_image),
//            modifier = Modifier
//                .size(100.dp)
//                .clip(CircleShape),
//            contentScale = ContentScale.Crop
//        )
        Box(
            Modifier
                .background(Color.White, CircleShape)
                .size(40.dp)
                .align(Alignment.BottomEnd)
        ) {
            IconButton(
                onClick = {},
                modifier = Modifier
                    .align(Alignment.Center)
                    .background(Color(0xFF703A00), CircleShape)
                    .size(36.dp)

            ) {
                Icon(
                    imageVector = Icons.Outlined.Edit,
                    contentDescription = stringResource(R.string.transaction_id),
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
