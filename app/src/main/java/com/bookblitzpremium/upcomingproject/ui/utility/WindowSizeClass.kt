package com.bookblitzpremium.upcomingproject.ui.utility


import android.app.Activity
import android.content.res.Configuration
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType

@Composable
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
fun getWindowSizeClass(activity: Activity): WindowSizeClass =
    calculateWindowSizeClass(activity)

/**
 * Checks if the device is a tablet based on width.
 */
fun isTablet(windowSizeClass: WindowSizeClass): Boolean =
    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded

/**
 * Checks if the device has a medium height.
 */
fun isMediumHeight(windowSizeClass: WindowSizeClass): Boolean =
    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium

/**
 * Determines if the device is a phone.
 */
fun isPhone(windowSizeClass: WindowSizeClass): Boolean =
    windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact