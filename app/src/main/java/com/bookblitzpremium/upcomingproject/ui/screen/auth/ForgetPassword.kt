package com.bookblitzpremium.upcomingproject.ui.screen.auth

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ViewModel.UserLogin
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.TextEmailSent
import com.bookblitzpremium.upcomingproject.ui.components.TextHeader

@Preview(showBackground = true)
@Composable
fun PreviewForgetPassword() {
    ForgetPassword(
        showToggleToTablet = false, // Example value
        onNextButtonClicked = {} // Empty lambda for preview
    )
}


@Composable
fun ForgetPassword(
    showToggleToTablet: Boolean,
    onNextButtonClicked: () -> Unit
){
    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
    val offsetValueY = if (showToggleToTablet) 120.dp else 200.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(
//                brush = Brush.verticalGradient(
//                    colors = listOf(
//                        Color(0xFFFFF9E5), // Light yellow
//                        Color(0xFFE6F0FA)  // Light blue
//                    )
//                )
//            )
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
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .offset(x = offsetValueX),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {

//            Image(
//                painter = painterResource(id = R.drawable.forget_password),
//                contentDescription = "Illustration",
//                modifier = Modifier
//                    .height(300.dp)
//                    .width(380.dp)
//                    .offset(x = -12.dp)
//            )

            TextHeader( stringResource(R.string.forget_password) )

            Spacer(modifier = Modifier.height(16.dp))

            TextEmailSent()

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = "",
                onValueChange = { },
                label = "Enter",
                placeholder = "Enter your Emails",
                leadingIcon = Icons.Default.Email,
                trailingIcon = Icons.Default.Clear,
                keyBoardType = KeyboardType.Email,  // ✅ Correctly passing KeyboardType
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = valueHorizontal, vertical = 16.dp)
            )

            Spacer(modifier = Modifier.height(10.dp))

//            ButtonHeader( textResId = R.string.login , valueHorizontal )

//            Spacer(modifier = Modifier.height(30.dp))

//            Divider(color = Color.Gray, thickness = 1.dp)

//            Text(
//                text = "Or login with",
//                style = AppTheme.typography.labelMedium,
//                modifier = Modifier
//                    .padding(vertical = 16.dp)
//            )

//            ButtonHeader(
//                R.string.login,
//                valueHorizontal,
//                UserLogin(),
//                email,
//                password
//            )
        }
    }
}