package com.bookblitzpremium.upcomingproject.ui.screen.auth

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.bookblitzpremium.upcomingproject.data.database.local.viewmodel.AuthViewModel
import com.bookblitzpremium.upcomingproject.data.model.OtpAction
import com.bookblitzpremium.upcomingproject.ui.utility.getWindowSizeClass
import com.bookblitzpremium.upcomingproject.ui.utility.isMediumHeight
import com.bookblitzpremium.upcomingproject.ui.utility.isTablet

@SuppressLint("ContextCastToActivity")
@Composable
fun DynamicOTPPage(navController: NavController, userModel: AuthViewModel, email: String) {
    val activity = LocalContext.current as? Activity ?: return PlaceholderUI()

    val windowSizeClass = getWindowSizeClass(activity)

    val isTabletLandscape = isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

    val isPhonePortrait = !isTablet(windowSizeClass) && isMediumHeight(windowSizeClass)

    val showTabletUI = when {
        isTabletLandscape -> true  // Show tablet layout
        isPhonePortrait -> false   // Show phone layout
        else -> false
    }


    val state by userModel.state.collectAsStateWithLifecycle()
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
        if (allNumbersEntered) {
            focusRequesters.forEach {
                it.freeFocus()
            }
            focusManager.clearFocus()
            keyboardManager?.hide()
        }
    }

    OtpScreen2(
        state = state,
        focusRequesters = focusRequesters,
        onAction = { action ->
            when (action) {
                is OtpAction.OnEnterNumber -> {
                    if (action.number != null) {
                        focusRequesters[action.index].freeFocus()
                    }
                }

                else -> Unit
            }
            userModel.onAction(action)
        },
        viewModel = userModel,
        navController = navController,
        modifier = Modifier,
        email = email
    )

}
