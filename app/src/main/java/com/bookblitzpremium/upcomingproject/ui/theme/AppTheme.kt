package com.bookblitzpremium.upcomingproject.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Dark theme color scheme
private val lightTheme = MainDesign(
    background = Color.White,
    onBackground = Color.Black,
    primary = Color.Black,
    onPrimary = Color.White,
    secondary = Color(0xFF625B71),
    onSecondary = Color.White,
    surface = Color(0xFFF5F5F5),
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE0E0E0),
    error = Color(0xFFB00020),
    selectDate = Color(0x8D151212),
    onText = Color.Black,
    starRating = Color(0xFFFFD700),
    primaryGradientStart = Color(0xFF8B5CF6),
    primaryGradientEnd = Color(0xFFEC4899),
    inRangeBackground = Color(0xFF625B71).copy(alpha = 0.5f)
)


// Light theme color scheme
private val lightColorScheme = MainDesign(
    background = Color.White,
    onBackground = Color.Black,
    primary = Color(0xFF6750A4),         // Purple accent
    onPrimary = Color.White,
    secondary = Color(0xFF625B71),       // Muted purple
    onSecondary = Color.White,
    surface = Color(0xFFF5F5F5),         // Light gray
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE0E0E0),  // Slightly darker gray
    error = Color(0xFFB00020),
    onText = Color.Black,
    selectDate = Color(0xFF6750A4),
    starRating = Color(0xFFFFD700),      // Gold
    primaryGradientStart = Color(0xFF8B5CF6), // Purple
    primaryGradientEnd = Color(0xFFEC4899),    // Pink
    inRangeBackground = Color(0xFF625B71).copy(alpha = 0.5f)
)


// Typography instance
private val typography = AppTypography(
    expandBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 33.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    smallSemiBold = TextStyle(
        fontFamily = poppinsSemibold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        lineHeight = 20.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    largeBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    largeSemiBold = TextStyle(
        fontFamily = poppinsSemibold,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    mediumBold = TextStyle(
        fontFamily = poppinsBold,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 28.sp,
        letterSpacing = (-0.25).sp,
        color = Color.Unspecified
    ),
    mediumSemiBold = TextStyle(
        fontFamily = poppinsSemibold,
        fontWeight = FontWeight.SemiBold,
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
        fontWeight = FontWeight.ExtraLight,
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
    labelLarge = TextStyle(
        fontFamily = poppinsMedium,
        fontWeight = FontWeight.Medium,
        fontSize = 24.sp,
        lineHeight = 36.sp,
        letterSpacing = (-0.25).sp,
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
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.1.sp,
        color = Color.Unspecified
    ),
    dateBold = TextStyle(
        fontFamily = poppinsRegular,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        color = Color.Unspecified
    )
)


// Size instance
private val size = AppSize(
    large = 32.dp,
    normal = 16.dp,
    medium = 8.dp,
    small = 4.dp
)


// Shape instance
private val shape = AppShape(
    container = RoundedCornerShape(8.dp),
    button = RoundedCornerShape(16.dp)
)




// App theme composable
@Composable
fun AppTheme(
    isDark: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDark) lightColorScheme else lightTheme
    CompositionLocalProvider(
        LocalAppColorSchema provides colorScheme,
        LocalAppTypography provides typography,
        LocalAppShape provides shape,
        LocalAppSize provides size,
        LocalElevation provides Elevation(none = 0.dp, small = 2.dp, medium = 4.dp, large = 8.dp),
        LocalOnTextColor provides colorScheme.onText,
        LocalStarRatingColor provides colorScheme.starRating,
        LocalPrimaryGradientStart provides colorScheme.primaryGradientStart,
        LocalPrimaryGradientEnd provides colorScheme.primaryGradientEnd,
        content = content
    )
}


// App theme object
object AppTheme {
    val colorScheme: MainDesign
        @Composable get() = LocalAppColorSchema.current


    val typography: AppTypography
        @Composable get() = LocalAppTypography.current


    val shape: AppShape
        @Composable get() = LocalAppShape.current


    val size: AppSize
        @Composable get() = LocalAppSize.current


    val elevation: Elevation
        @Composable get() = LocalElevation.current
}

