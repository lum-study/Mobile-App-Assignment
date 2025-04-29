package com.bookblitzpremium.upcomingproject.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.components.VideoPlayer
import com.bookblitzpremium.upcomingproject.ui.components.TextEmailSent
import com.bookblitzpremium.upcomingproject.ui.components.TextHeader


//@Preview(showBackground = true, widthDp = 500, heightDp = 1000)
//@Composable
//fun PreviewChangePassword(){
//    val navController = rememberNavController()
//    ChangePassword(false, onNextButtonClicked = navController.navigate(AppScreen.BookingPeople.route))
//}

@Composable
fun ChangePassword(showToggleToTablet: Boolean, onNextButtonClicked: () -> Unit){

    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFFFF9E5), // Light yellow
                        Color(0xFFE6F0FA)  // Light blue
                    )
                )
            )
    ) {

//        VideoPlayer(
//            videoUri = videoUri,
//            modifier = Modifier
//                .fillMaxSize()
//
//        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
                .padding(horizontal = 28.dp)
                .offset(x = offsetValueX),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {

            TextHeader(stringResource(R.string.change_password))

            TextEmailSent()

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.98f)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Automatically spaces elements
            ){
                CustomTextField(
                    value = "",
                    onValueChange = { },
                    label = "Emails",
                    placeholder = "Enter your Emails",
                    leadingIcon = Icons.Default.Email,
                    trailingIcon = Icons.Default.Clear,
                    keyBoardType = KeyboardType.Email,  // ✅ Correctly passing KeyboardType
                    inputType = VisualTransformation.None,  // ✅ No transformation
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = valueHorizontal, vertical = 16.dp)
                )

                Spacer(modifier = Modifier.height(10.dp))

                CustomTextField(
                    value = "",
                    onValueChange = { },
                    label = "Comfirm Passwords",
                    placeholder = "Enter your Comfirm Passwords",
                    leadingIcon = Icons.Default.Lock,
                    trailingIcon = Icons.Default.Clear,
                    keyBoardType = KeyboardType.Password,  // ✅ Correctly passing KeyboardType
                    inputType = PasswordVisualTransformation(),  // ✅ No transformation
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = valueHorizontal, vertical = 16.dp)
                )
            }
        }
    }
}