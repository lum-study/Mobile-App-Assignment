package com.bookblitzpremium.upcomingproject.ui.screen.hotel

import android.annotation.SuppressLint
import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp


//@SuppressLint("ContextCastToActivity", "SuspiciousIndentation")
//@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
//@Composable
//fun PreviewHotelDetails( onNextButtonClicked: () -> Unit,) {
//    val activity = LocalContext.current as? Activity
//    if (activity != null ) {
//
//        val windowSizeClass = calculateWindowSizeClass(activity = activity)
//
//        when {
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
//
//                    Box( // ✅ Wrap `Test()` in a full-height Box
//                        modifier = Modifier.fillMaxSize()
//                            .background(Color.Black)
//                    ) {
//                        HotelHeaderTable(onNextButtonClicked = onNextButtonClicked,)
//                    }
//
////                MobileLayout(3 , 500.dp ,800.dp)
//
//            }
//
//            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
//                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
////                LazyColumn(
////                    modifier = Modifier
////                        .fillMaxSize()
////                        .background(Color.Black),
////                ) {
////                    item { HotelHeader(showBackButton = 2) }
////                    item{
////                        Column(
////                            modifier = Modifier
////                                .background(Color.White , RoundedCornerShape(32.dp)) //
////                        ){
////                            HotelInfoSection(
////                                showBackButton = 2, // Example value, adjust as needed
////                            )
////                            HotelDescriptionSection(showBackButton = 2)
////                            HotelPreviewImages(showBackButton = 2)
////                            HotelReviewsSection(showBackButton = 2)
////                        }
////                    }
////                }
//
//                        MobileLayout(2 , 300.dp , 500.dp, )
//            }
//
//            else -> {
//                MobileLayout(3 , 500.dp ,800.dp )
//
//
////                LazyColumn(
////                    modifier = Modifier
////                        .fillMaxSize()
////                        .background(Color.Black),
////                ) {
////                    item { HotelHeader(showBackButton = 3) }
////                    item {
////                        Column(
////                            modifier = Modifier
////                                .background(Color.White, RoundedCornerShape(32.dp)) //
////                        ) {
//////                            HotelInfoSection(showBackButton = 3)
//////                            HotelDescriptionSection(showBackButton = 3)
//////                            HotelPreviewImages(showBackButton = 3)
//////                            HotelReviewsSection(showBackButton = 3)
////                        }
////                    }
////                }
//
//            }
//        }
//    } else {
//        Column(modifier = Modifier.padding(16.dp)) {
//            Text(text = "Activity is null - Preview mode or invalid context")
//        }
//    }
//}
@SuppressLint("ContextCastToActivity")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PreviewPaymentMethod( ) {
    val activity = LocalContext.current as? Activity

    if (activity != null) {
        val windowSizeClass = calculateWindowSizeClass(activity = activity)

        when {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .padding(bottom = 30.dp)
                ){
//                    PaymentDetails()
                }
            }

            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .padding(bottom = 30.dp)
                ){
//                    PaymentDetails()
                }
            }

            else -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .fillMaxHeight()
                        .padding(bottom = 30.dp)
                ){
//                    PaymentDetails()
                }
            }
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Activity is null - Preview mode or invalid context")
        }
    }
}

@SuppressLint("ContextCastToActivity", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PreviewPackageDetails() {
    val activity = LocalContext.current as? Activity
    if (activity != null ) {

        val windowSizeClass = calculateWindowSizeClass(activity = activity)

        when {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {

                Box( // ✅ Wrap `Test()` in a full-height Box
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Black)
                ) {
//                    TravelHeaderTable()
                }
            }

            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {

            }

            else -> {

            }
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Activity is null - Preview mode or invalid context")
        }
    }
}

@SuppressLint("ContextCastToActivity", "SuspiciousIndentation")
@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun PreviewPSelectFigure() {
    val activity = LocalContext.current as? Activity
    if (activity != null ) {

        val windowSizeClass = calculateWindowSizeClass(activity = activity)

        when {
            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded &&
                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {

                Box( // ✅ Wrap `Test()` in a full-height Box
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
                ) {
                    SelectingFigure(1 , modifier = Modifier)
                }
            }

            windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact &&
                    windowSizeClass.heightSizeClass == WindowHeightSizeClass.Medium -> {
                Box( // ✅ Wrap `Test()` in a full-height Box
                    contentAlignment = Alignment.BottomCenter,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black)
//                        .offset(y = 400.dp)
                ) {
                    SelectingFigure(2, modifier = Modifier)
                }
            }

            else -> {
                SelectingFigure(3, modifier = Modifier)
            }
        }
    } else {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Activity is null - Preview mode or invalid context")
        }
    }
}