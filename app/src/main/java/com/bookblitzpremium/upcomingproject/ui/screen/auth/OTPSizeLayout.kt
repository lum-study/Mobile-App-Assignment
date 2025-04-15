package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.UserLogin
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.data.model.OtpState
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
import com.bookblitzpremium.upcomingproject.ui.utility.isMediumHeight
import com.bookblitzpremium.upcomingproject.ui.utility.isTablet
import androidx.compose.runtime.getValue

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
@SuppressLint("ContextCastToActivity")
@Composable
fun DynamicOTPPage(onNextButtonClicked: () -> Unit, navController: NavController, userLoginViewModel: UserLogin) {
    val activity = LocalContext.current as? Activity ?: return PlaceholderUI()

    val windowSizeClass = getWindowSizeClass(activity)

    val isTabletLandscape = isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

    val isPhonePortrait = !isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

    val showTabletUI = when {
        isTabletLandscape -> true  // Show tablet layout
        isPhonePortrait -> false   // Show phone layout
        else -> false
    }


    val state by userLoginViewModel.state.collectAsStateWithLifecycle()
    val focusRequesters = remember {
        List(4) { FocusRequester() }
    }
    val focusManager = LocalFocusManager.current
    val keyboardManager = LocalSoftwareKeyboardController.current

    LaunchedEffect(state.focusedIndex) {
        state.focusedIndex?.let { index ->
            focusRequesters.getOrNull(index)?.requestFocus()
        }
    }

    LaunchedEffect(state.code, keyboardManager) {
        val allNumbersEntered = state.code.none { it == null }
        if(allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    OtpScreen(
        state = state,
        focusRequesters = focusRequesters,
        onAction = { action ->
            when(action) {
                is OtpAction.OnEnterNumber -> {
                    if(action.number != null) {
                        focusRequesters[action.index].freeFocus()
                    }
                }
                else -> Unit
            }
            userLoginViewModel.onAction(action)
        },
        viewModel = userLoginViewModel,
        navController = navController,
        modifier = Modifier
    )

}
