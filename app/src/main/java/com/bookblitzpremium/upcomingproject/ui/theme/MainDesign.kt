package com.bookblitzpremium.upcomingproject.ui.theme

import androidx.compose.material3.Text
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class MainDesign(
    val background: Color,
    val onBackground: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val surface: Color,
    val onSurface: Color,
    val surfaceVariant: Color,
    val error: Color,
    val onText: Color,
    val starRating: Color,
    val primaryGradientStart: Color,
    val primaryGradientEnd: Color,
    val inRangeBackground: Color
)

data class AppTypography(
    val largeBold: TextStyle,
    val largeSemiBold: TextStyle,
    val mediumBold: TextStyle,
    val mediumSemiBold: TextStyle,
    val mediumNormal: TextStyle,
    val smallLight: TextStyle,
    val smallRegular: TextStyle,
    val expandBold: TextStyle,
    val displayLarge: TextStyle,
    val headlineLarge: TextStyle,
    val titleLarge: TextStyle,
    val bodyLarge: TextStyle,
    val labelLarge: TextStyle,
    val labelMedium: TextStyle,
    val labelSmall: TextStyle,
    val smallSemiBold: TextStyle,
    val smallBold: TextStyle,
    val dateBold : TextStyle
)

data class AppShape(
    val container: Shape,
    val button: Shape
)

data class AppSize(
    val large: Dp,
    val normal: Dp,
    val medium: Dp,
    val small: Dp,
)

data class Elevation(
    val none: Dp = 0.dp,
    val small: Dp = 2.dp,
    val medium: Dp = 4.dp,
    val large: Dp = 8.dp
)

val LocalAppColorSchema = staticCompositionLocalOf {
    MainDesign(
        background = Color.Unspecified,
        onBackground = Color.Unspecified,
        primary = Color.Unspecified,
        onPrimary = Color.Unspecified,
        secondary = Color.Unspecified,
        onSecondary = Color.Unspecified,
        onText = Color.Unspecified,
        surface = Color.Unspecified,
        onSurface = Color.Unspecified,
        surfaceVariant = Color.Unspecified,
        error = Color.Unspecified,
        starRating = Color.Unspecified,
        primaryGradientStart = Color.Unspecified,
        primaryGradientEnd = Color.Unspecified,
        inRangeBackground = Color.Unspecified
    )
}

val LocalAppTypography = staticCompositionLocalOf {
    AppTypography(
        largeBold = TextStyle.Default,
        largeSemiBold = TextStyle.Default,
        mediumBold = TextStyle.Default,
        mediumSemiBold = TextStyle.Default,
        mediumNormal = TextStyle.Default,
        smallLight = TextStyle.Default,
        smallRegular = TextStyle.Default,
        expandBold = TextStyle.Default,
        displayLarge = TextStyle.Default,
        headlineLarge = TextStyle.Default,
        titleLarge = TextStyle.Default,
        bodyLarge = TextStyle.Default,
        labelMedium = TextStyle.Default,
        labelSmall = TextStyle.Default,
        smallSemiBold = TextStyle.Default,
        smallBold = TextStyle.Default,
        dateBold = TextStyle.Default,
        labelLarge = TextStyle.Default
    )
}

val LocalAppShape = staticCompositionLocalOf {
    AppShape(
        container = RectangleShape,
        button = RectangleShape
    )
}

val LocalAppSize = staticCompositionLocalOf {
    AppSize(
        large = Dp.Unspecified,
        normal = Dp.Unspecified,
        medium = Dp.Unspecified,
        small = Dp.Unspecified,
    )
}

val LocalOnTextColor = staticCompositionLocalOf { Color.Black }
val LocalStarRatingColor = staticCompositionLocalOf { Color(0xFFFFD700) }
val LocalPrimaryGradientStart = staticCompositionLocalOf { Color(0xFF8B5CF6) }
val LocalPrimaryGradientEnd = staticCompositionLocalOf { Color(0xFFEC4899) }


val LocalElevation = staticCompositionLocalOf {
    Elevation(
        none = 0.dp,
        small = 2.dp,
        medium = 4.dp,
        large = 8.dp
    )
}