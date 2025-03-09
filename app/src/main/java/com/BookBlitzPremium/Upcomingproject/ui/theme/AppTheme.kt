package com.BookBlitzPremium.Upcomingproject.ui.theme

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.BookBlitzPremium.Upcomingproject.R // Adjust based on your package

// Color schemes
private val darkColorScheme = MainDesign(
    background = Color(0xFF121212),
    onBackground = Color.White,
    primary = Color(0xFFBB86FC),
    onPrimary = Color.Black,
    secondary = Color(0xFF03DAC6),
    onSecondary = Color.Black,
    surface = Color(0xFF1E1E1E),
    onSurface = Color.White,
    surfaceVariant = Color(0xFF333333),
    error = Color(0xFFCF6679)
)

private val lightColorScheme = MainDesign(
    background = Color.White,
    onBackground = Color.Black,
    primary = Color(0xFF6750A4), // Corrected hex to a valid purple
    onPrimary = Color.White,
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    surface = Color(0xFFF5F5F5),
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE0E0E0),
    error = Color(0xFFB00020)
)

private val typography = AppTypography(
    displayLarge = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
        lineHeight = 64.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified // To be set by theme
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
    val colorScheme = if (isDark) darkColorScheme else lightColorScheme
    val rippleIndication = rememberRipple() // Fixed typo
    CompositionLocalProvider(
        LocalAppTypography provides typography, // Fixed typo
        LocalAppSize provides size,
        LocalAppColorSchema provides colorScheme,
        LocalIndication provides rippleIndication,
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