package com.bookblitzpremium.upcomingproject.ui.screen.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.TravelScreen
import com.bookblitzpremium.upcomingproject.ui.components.ClickableFun
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.ui.components.LineOver
import com.bookblitzpremium.upcomingproject.ui.components.SignInWithGoogle
import com.bookblitzpremium.upcomingproject.ui.components.TextHeader
import com.bookblitzpremium.upcomingproject.ui.components.VideoPlayer
import com.bookblitzpremium.upcomingproject.ui.components.videoUri

@Composable
fun RegristerPage(showToggleToTablet: Boolean,  onNextButtonClicked: () -> Unit, navController: NavController){

    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
    val offsetValueY = if (showToggleToTablet) 120.dp else 200.dp

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.3f)) // Adjust opacity here
    ) {

        VideoPlayer(
            videoUri = videoUri,
            modifier = Modifier
                .fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
                .padding(horizontal = 28.dp)
                .offset(x = offsetValueX),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ){

            TextHeader( stringResource(R.string.register_as_a_account) )

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp) // Automatically spaces elements
            ) {
                CustomTextField(
                    value = "",
                    onValueChange = { },
                    label = "Username",
                    placeholder = "Enter your Username",
                    leadingIcon = Icons.Default.Person,
                    trailingIcon = Icons.Default.Clear,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = valueHorizontal, vertical = 12.dp)
                )

                CustomTextField(
                    value = "",
                    onValueChange = { },
                    label = "Password",
                    placeholder = stringResource(R.string.enter_your_password),
                    leadingIcon = Icons.Default.Lock,
                    trailingIcon = Icons.Default.Clear,
                    keyBoardType = KeyboardType.Password,
                    inputType = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = valueHorizontal, vertical = 12.dp)
                )

                CustomTextField(
                    value = "",
                    onValueChange = { },
                    label = "Confirm Password",
                    placeholder = "Enter your Confirm Password",
                    leadingIcon = Icons.Default.Lock,
                    trailingIcon = Icons.Default.Clear,
                    keyBoardType = KeyboardType.Password,
                    inputType = PasswordVisualTransformation(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = valueHorizontal, vertical = 12.dp)
                )
            }



//            ButtonHeader(R.string.regrister_text, valueHorizontal)
            LineOver()

            SignInWithGoogle(valueHorizontal)

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp, horizontal = valueHorizontal),
                horizontalArrangement = Arrangement.Center
            ) {
                ClickableFun(
                    text = stringResource(R.string.already_have_an_account_login),
                    onClick = { navController.navigate(TravelScreen.LOGIN.name) } // ✅ Wrap in a lambda
                )

            }
        }
    }
}