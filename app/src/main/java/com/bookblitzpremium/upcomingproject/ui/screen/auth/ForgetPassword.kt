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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.bookblitzpremium.upcomingproject.ui.components.CustomTextField
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.UserLogin
import com.bookblitzpremium.upcomingproject.ui.components.ButtonHeader
import com.bookblitzpremium.upcomingproject.ui.components.CustomDialog
import com.bookblitzpremium.upcomingproject.ui.components.TextEmailSent
import com.bookblitzpremium.upcomingproject.ui.components.TextHeader

@Preview(showBackground = true)
@Composable
fun PreviewForgetPassword() {
    val navController = rememberNavController()
    ForgetPassword(
        showToggleToTablet = false, // Example value
        onNextButtonClicked = {} ,
        viewModel = viewModel(),
        navController = navController
    )
}


@Composable
fun ForgetPassword(
    showToggleToTablet: Boolean,
    onNextButtonClicked: () -> Unit,
    viewModel: UserLogin,
    navController: NavController
){
    val valueHorizontal = if (showToggleToTablet) 46.dp else 16.dp
    val maxSizeAvailable = if (showToggleToTablet) 0.4f else 1f
    val offsetValueX = if (showToggleToTablet) 620.dp else 0.dp
    val offsetValueY = if (showToggleToTablet) 120.dp else 200.dp

    var emails by rememberSaveable { mutableStateOf("") }
    var showDialog by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        Column(
            modifier = Modifier
                .fillMaxHeight()
                .fillMaxWidth(maxSizeAvailable) // 40% width for a "column" effect
                .padding(horizontal = 16.dp, vertical = 40.dp)
                .offset(x = offsetValueX),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Center content vertically
        ) {

            TextHeader( stringResource(R.string.forget_password) )

            Spacer(modifier = Modifier.height(16.dp))

            TextEmailSent()

            Spacer(modifier = Modifier.height(16.dp))

            CustomTextField(
                value = emails,
                onValueChange = { emails = it },
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

            ButtonHeader(
                textResId = R.string.login,
                valueHorizontal = valueHorizontal,
                userFunction = {
                    showDialog = true
                }
            )

            if (showDialog) {

                viewModel.sendEmailToChangePassword(emails)

                CustomDialog(
                    onDismissRequest = {  },
                    onNextClick = { navController.navigate(AppScreen.Login.route) },
                )
            }

        }
    }
}