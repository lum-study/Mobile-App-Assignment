package com.bookblitzpremium.upcomingproject.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val darkColorScheme = MainDesign(
    background      = Color(0xFF121212), // leave dark gray
    onBackground    = Color.Black,       // was White
    primary         = Color.White,       // was Black
    onPrimary       = Color.White,       // was Black
    secondary       = Color.Gray,
    onSecondary     = Color.White,       // was Black
    surface         = Color(0xFF1E1E1E),
    onSurface       = Color.Black,       // was White
    surfaceVariant  = Color(0xFF333333),
    error           = Color(0xFFCF6679),
    onText          = Color.Black        // was White
)

private val lightColorScheme = MainDesign(
    background      = Color.White,      // ← light background
    onBackground    = Color.Black,      // ← dark text on light
    primary         = Color(0xFF6750A4),// your accent
    onPrimary       = Color.White,
    secondary       = Color(0xFF625B71),
    onSecondary     = Color.White,
    surface         = Color(0xFFF5F5F5),
    onSurface       = Color.Black,
    surfaceVariant  = Color(0xFFE0E0E0),
    error           = Color(0xFFB00020),
    onText          = Color.Black
)

private val typography = AppTypography(
    expandBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Normal,
        fontSize = 33.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified // To be set by theme
    ),
    smallSemiBold = TextStyle(
        fontFamily = poppinsSemibold,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    largeBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified // To be set by theme
    ),
    largeSemiBold = TextStyle(
        fontFamily = poppinsSemibold,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    mediumBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    mediumSemiBold = TextStyle(
        fontFamily = poppinsSemibold,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    mediumNormal = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    smallBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
//        lineHeight = 28.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    smallRegular = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    smallLight = TextStyle(
        fontFamily = poppinsExtralight,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    displayLarge = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    headlineLarge = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 32.sp,
        lineHeight = 40.sp,
        letterSpacing = 0.sp,
        color = Color.Unspecified
    ),
    titleLarge = TextStyle(
        fontFamily = poppinsSemibold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = Color.Unspecified
    ),
    bodyLarge = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = Color.Unspecified
    ),
    labelMedium = TextStyle(
        fontFamily = poppinsMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp,
        color = Color.Unspecified
    ),
    labelSmall = TextStyle(
        fontFamily = poppinsRegular, // Changed from poppinsThin
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp, // Changed from 6.sp to standard caption size
        lineHeight = 16.sp, // Adjusted for readability
        letterSpacing = 0.1.sp,
        color = Color.Unspecified
    ),
    dateBold = TextStyle(
        fontFamily = poppinsRegular, // Changed from poppinsThin
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp, // Changed from 6.sp to standard caption size
        color = Color.Unspecified
    )
)

private val shape = AppShape(
    container = RoundedCornerShape(8.dp), // 8.dp
    button = RoundedCornerShape(16.dp)    // 16.dp
)

private val size = AppSize(
    large = 32.dp,  // 32.dp
    normal = 16.dp, // 16.dp
    medium = 8.dp, // 8.dp
    small = 4.dp   // 4.dp
)


@Composable
fun AppTheme( // Fixed typo
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDark)  darkColorScheme else  lightColorScheme
//    val rippleIndication = rememberRipple() // Fixed typo
    CompositionLocalProvider(
        LocalAppTypography provides typography, // Fixed typo
        LocalAppSize provides size,
        LocalAppColorSchema provides colorScheme,
//        LocalIndication provides rippleIndication,
        LocalAppShape provides shape,
        LocalElevation provides Elevation(
            none = 0.dp,
            small = 2.dp,
            medium = 4.dp,
            large = 8.dp
        ),
        content = content
    )
}

object AppTheme {
    val colorScheme: MainDesign
        @Composable get() = LocalAppColorSchema.current

    val typography: AppTypography
        @Composable get() = LocalAppTypography.current

    val size: AppSize
        @Composable get() = LocalAppSize.current

    val shape: AppShape
        @Composable get() = LocalAppShape.current

    val elevation: Elevation
        @Composable
        get() = LocalElevation.current
}