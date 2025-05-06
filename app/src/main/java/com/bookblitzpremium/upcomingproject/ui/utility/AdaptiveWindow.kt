package com.bookblitzpremium.upcomingproject.ui.utility

import android.content.res.Configuration
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowSizeClass
import androidx.window.core.layout.WindowWidthSizeClass
import com.bookblitzpremium.upcomingproject.common.enums.DeviceType

fun getDeviceType(windowSizeClass: WindowSizeClass, configuration: Configuration): DeviceType {
    return when {
        windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT && configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> DeviceType.MobilePortrait
        windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && configuration.orientation == Configuration.ORIENTATION_LANDSCAPE -> DeviceType.TabletLandscape
        windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.EXPANDED && configuration.orientation == Configuration.ORIENTATION_PORTRAIT -> DeviceType.TabletPortrait
        else -> DeviceType.TabletPortrait
    }
}