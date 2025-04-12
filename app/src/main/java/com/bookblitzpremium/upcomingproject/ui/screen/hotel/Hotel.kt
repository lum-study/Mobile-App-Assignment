package com.bookblitzpremium.upcomingproject.ui.screen.hotel

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.common.enums.AppScreen
import com.bookblitzpremium.upcomingproject.ui.components.HeaderDetails
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.navigation.compose.rememberNavController

@Composable
fun ProductDetailScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF6FAF5))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .padding(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column {
                        Text(
                            text = "Eclipse V2",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Row {
                            Text(
                                text = "$165.00",
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color(0xFF2C6E49)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "$200.00",
                                style = TextStyle(
                                    textDecoration = TextDecoration.LineThrough
                                ),
                                color = Color.Gray,
                                fontSize = 16.sp
                            )
                        }
                    }
                    Text(
                        text = "ERO2542",
                        color = Color.Gray,
                        fontSize = 12.sp,
                        modifier = Modifier
                            .background(Color(0xFFE0E0E0), shape = RoundedCornerShape(8.dp))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Specs Row
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SpecItem("Height:", "4 cm")
                    SpecItem("Width:", "15 cm")
                    SpecItem("Material", "Glass")
                }

                Spacer(modifier = Modifier.height(24.dp))

                Button(
                    onClick = { /* Add to cart logic */ },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF2C6E49))
                ) {
                    Text("Add to Cart", fontSize = 18.sp, color = Color.White)
                }
            }
        }
    }
}

@Composable
fun SpecItem(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = Color.Gray, fontSize = 14.sp)
        Text(text = value, fontWeight = FontWeight.Bold, fontSize = 16.sp)
    }
}


@Composable
fun MobieLayout(showNUmber:Int, defaultSize : Dp, maxSize: Dp) {

    var topHeight by remember { mutableStateOf(defaultSize) } // 🔹 Shared state for top section
    val minHeight = 100.dp // 🔹 Minimum height (almost hides)
    val maxHeight = maxSize // 🔹 Maximum height
    val dragSpeedFactor = 0.3f // 🔹 Slow down dragging effect

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topHeight) // 🔹 Dynamically adjust height
                .background(Color.Black)
        ) {
            Image(
                painter = painterResource(id = R.drawable.hotel_images),
                contentDescription = "Hotel Image",
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize() // ✅ Fill the dynamic height
            )

            if( showNUmber ==1 || showNUmber ==3 ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .align(Alignment.BottomEnd)
                        .padding(horizontal = 48.dp, vertical = 30.dp)
                ){
                    Button(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .width(300.dp)
                            .padding(end = 20.dp)

                    ){
                        Text(
                            text = stringResource(R.string.pick_date),
                            style = AppTheme.typography.mediumBold
                        )
                    }

                    Button(
                        onClick = {

                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.width(300.dp)
                    ){
                        Text(
                            text = stringResource(R.string.pick_date),
                            style = AppTheme.typography.mediumBold
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .background(Color.Black)
        ){
            DragableToTop(
                showBackButton = showNUmber,
                topHeight = topHeight,
                onDrag = { delta ->
                    val scaledDelta = delta * dragSpeedFactor
                    val newHeight = (topHeight.value + scaledDelta).dp
                    topHeight = newHeight.coerceIn(minHeight, maxHeight)
                }
            )
        }
    }
}

@Composable
fun HotelHeaderTable( onNextButtonClicked: () -> Unit ) {
    var showNewComponent by remember { mutableStateOf(false) } // ✅ State for toggling components

    Row(
        modifier = Modifier.fillMaxSize()
    ) {
        // Left Side (Static)
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .paint(
                    painterResource(id = R.drawable.hotel_images),
                    contentScale = ContentScale.Crop
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .align(Alignment.BottomEnd)
                    .padding(horizontal = 16.dp, vertical = 30.dp)
            ) {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .width(300.dp)
                        .padding(end = 20.dp)
                ) {
                    Text(text = stringResource(R.string.pick_date), style = AppTheme.typography.mediumBold)
                }

                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier.width(300.dp)
                ) {
                    Text(text = stringResource(R.string.pick_date), style = AppTheme.typography.mediumBold)
                }
            }
        }

        // Right Side (Dynamic)
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                .background(Color.White)
        ) {
            if (showNewComponent) {
                // ✅ Render new component when button is clicked
                PaymentDetails()
            } else {
                // ✅ Default components
                LazyColumn {
                    item { HotelInfoSection(showBackButton = 1, modifier = Modifier) }
                    item { HotelDescriptionSection(showBackButton = 1, modifier = Modifier) }
                    item { HotelPreviewImages(showBackButton = 1, modifier = Modifier) }
                    item { HotelReviewsSection(showBackButton = 1, modifier = Modifier) }
                    item {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Black,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            onClick = { showNewComponent = true } // ✅ Update state on click
                        ) {
                            Text(text = stringResource(R.string.next_button))
                        }
                    }
                    item { Spacer(modifier = Modifier.height(100.dp)) }
                }
            }
        }
    }
}

@Composable
fun DragableToTop(
    showBackButton: Int,
    topHeight: Dp,
    onDrag: (Float) -> Unit
) {
    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
    val rangeBetweenLocation = if (showBackButton == 1) 340.dp  else if (showBackButton == 2) 150.dp else 500.dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) // ✅ Background color

//            .verticalScroll(rememberScrollState()) // ✅ Enables scrolling
    ) {
        // 🔹 Draggable Divider
        Divider(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(0.5f)
                .height(6.dp)
                .offset(y = 4.dp)
                .clip(RoundedCornerShape(32.dp)) // ✅ Ensures shape is actually applied
                .background(Color.LightGray) // ✅ Background color applies correctly
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        onDrag(delta)
                    }
                )
        )

        // 🔹 Hotel Title & Location Row
        Row(verticalAlignment = Alignment.CenterVertically) {

            HeaderDetails(R.string.swiss_hotel, textOffset, modifier = Modifier.padding(top = 12.dp))

            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .offset(x = rangeBetweenLocation)
                    .size(48.dp)
                    .background(Color.LightGray, RoundedCornerShape(16.dp))
                    .clickable { /* Handle location click */ },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.LocationOn,
                    contentDescription = "Location",
                    tint = Color.Black,
                    modifier = Modifier.size(28.dp)
                )
            }
        }

        Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))

        // 🔹 Hotel Address
        Text(
            text = "211B Baker Street, London, England",
            color = Color.Gray,
            fontSize = 14.sp,
            modifier = Modifier.offset(x = textOffset)
        )

        // 🔹 Star Ratings
        Row(modifier = Modifier.offset(x = textOffset)) {
            repeat(4) {
                Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
            }
            Text(
                text = "4.5 - 1231 Reviews",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        HotelDescriptionSection(showBackButton = 2 , modifier = Modifier)

        val scrollModifier = if (topHeight >= 310.dp && topHeight <=500.dp) {
            Modifier.verticalScroll(rememberScrollState())
//            Modifier
        } else if (topHeight <= 250.dp) {  // ✅ Fixed comparison operator
            Modifier.verticalScroll(rememberScrollState())
//            Modifier
        } else {
            Modifier // ✅ Ensures all cases return a valid Modifier
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .then(scrollModifier)
                .wrapContentHeight()
        ) {

             if (topHeight >= 310.dp && topHeight <=500.dp) {
                HotelPreviewImages(showBackButton = 2, modifier = Modifier)
//                HotelReviewsSection(showBackButton = 2, modifier = Modifier.height(500.dp))
            } else if (topHeight <= 250.dp) {  // ✅ Fixed comparison operator
                HotelPreviewImages(showBackButton = 2, modifier = Modifier)
                HotelReviewsSection(showBackButton = 2, modifier = Modifier.height(500.dp))
                 Spacer(modifier = Modifier.height(200.dp))
            } else {
                HotelPreviewImages(showBackButton = 2, modifier = Modifier)
            }


        }



    }
}


@Composable
fun HotelInfoSection(
    showBackButton: Int ,
    modifier: Modifier
) {

    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
    val rangeBetweenLocation = if (showBackButton == 1) 340.dp  else if (showBackButton == 2) 150.dp else 500.dp

    Column(
        modifier = Modifier
            .background(Color.White , RoundedCornerShape(32.dp))
    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 0.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
            ) {

                if(showBackButton == 2){
                    Divider(
                        modifier = Modifier
                            .offset(x = 100.dp, y = -4.dp)
                            .fillMaxWidth(0.5f)
                            .height(4.dp)
                            .background(Color.Red)
                            .shadow(
                                elevation = 0.dp,
                                shape = RoundedCornerShape(32.dp),
                                clip = true,
                                ambientColor = Color.LightGray,
                            )
                    )
                } else if (showBackButton != 2 && showBackButton!= 1){
                    Divider(
                        modifier = Modifier
                            .offset(x = 260.dp, y = -4.dp)
                            .fillMaxWidth(0.3f)
                            .height(4.dp)
                            .background(Color.LightGray)
                            .shadow(
                                elevation = 0.dp,
                                shape = RoundedCornerShape(32.dp),
                                clip = true,
                                ambientColor = Color.LightGray,
                            )
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    HeaderDetails(R.string.swiss_hotel, textOffset, modifier = Modifier)

                    Spacer(modifier = Modifier.width(8.dp))

                    Box(
                        modifier = Modifier
                            .offset(x = rangeBetweenLocation)
                            .size(48.dp)
                            .background(Color.LightGray, RoundedCornerShape(16.dp))
                            .clickable { /* Handle location click */ },
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.Black,
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }

                Divider(
                    color = Color.Gray,
                    thickness = 2.dp,
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                Text(
                    text = "211B Baker Street, London, England",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    modifier = Modifier.offset(x = textOffset)
                )

                Row(modifier = Modifier.offset(x = textOffset)) {
                    repeat(4) {
                        Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
                    }
                    Text(
                        text = "4.5 - 1231 Reviews",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(start = 4.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun HotelDescriptionSection(showBackButton: Int, modifier: Modifier) {

    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp

    Column(
        verticalArrangement = spacedBy(6.dp),
        modifier = Modifier.padding( top = 16.dp)
    ) {
        HeaderDetails(R.string.description, textOffset, modifier = Modifier)

        Text(
            text = "Hotel Room means an area designed for occupancy, located in the Hotel Property...",
            color = Color.Black,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .offset(x = textOffset)
        )
        Text(
            text = "Read more",
            color = Color.Blue,
            fontSize = 14.sp,
            modifier = Modifier
                .offset(x = textOffset)
                .clickable {}
        )
    }
}

@Composable
fun HotelPreviewImages(showBackButton: Int,modifier: Modifier) {
    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp

    Column(modifier = Modifier.padding( top = 16.dp, end = 16.dp)) {

        HeaderDetails(R.string.preview, textOffset,modifier = Modifier)

        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            horizontalArrangement = spacedBy(8.dp),
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(start = 16.dp)
        ) {
            items(4) {
                Box(
                    modifier = Modifier
                        .size(206.dp)
                        .background(Color.LightGray, RoundedCornerShape(16.dp))
                        .padding(12.dp) // Padding inside the Box
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),

                        ) {
                        Image(
                            painter = painterResource(id = R.drawable.hotel_images),
                            contentDescription = "Hotel preview",
                            modifier = Modifier
                                .fillMaxWidth() // Ensures it fills available width
                                .height(150.dp) // Adjust height to leave space for text
                                .clip(RoundedCornerShape(8.dp))
                        )

                        // Directly placing the Text without a Row
                        Text(
                            text = "Hotels hghggf",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black, // Change to black for better visibility
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.Start) // Align text to start
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HotelReviewsSection(showBackButton: Int,modifier: Modifier) {
    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
    ) {
        HeaderDetails(R.string.reviews , textOffset,modifier = Modifier)
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = spacedBy(16.dp)
        ) {
            items(5) {
                ReviewItem(showBackButton )
            }
        }
    }
}



@Composable
fun ReviewItem(showBackButton: Int,) {
    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
    Column(
        modifier = Modifier
            .offset(x = textOffset)
            .fillMaxWidth()
            .padding(end = 16.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.hotel_images),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )

            Column(
                modifier = Modifier.padding(start = 12.dp, top = 8.dp),
                verticalArrangement = spacedBy(8.dp)
            ) {
                Text(text = "Sasha Perry", color = Color.Gray, fontSize = 14.sp)
                Text(text = "Visitor", color = Color.Gray, fontSize = 14.sp)
            }

            Row(modifier = Modifier.padding(start = 80.dp, top = 8.dp)) {
                repeat(4) {
                    Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
                }
            }
        }

        Text(
            text = "Hotel Room means an area designed for occupancy, located in the Hotel Property...",
            color = Color.Black,
            fontSize = 14.sp,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}



@Preview(name = "Mobile Layout", showBackground = true, widthDp = 500, heightDp = 1000)
@Composable
fun PreviewMobileLayout1() {
    val navController = rememberNavController()
    OverlappingContentTest(2, navController)
}



@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OverlappingContentTest(
    showBackButton: Int,
    navController: NavController
) {
    var isExpanded by remember { mutableStateOf(true) } // 🔥 Toggle state
    val offsetValue by animateDpAsState(
        targetValue = if (isExpanded) 300.dp else 0.dp, // 🔥 Moves content up
        animationSpec = tween(300)
    )

    val textOffset = 24.dp
    val rangeBetweenLocation = when (showBackButton) {
        1 -> 340.dp
        2 -> 150.dp
        else -> 500.dp
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        if (isExpanded) {
            // ✅ Expanded: LazyColumn (Scrollable)
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.hotel_images),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(800.dp)
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .offset(y = -500.dp)
                            .wrapContentSize()
                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
//                            .padding(top = 16.dp,bottom= 32.dp)
                    ) {

                        Column(
                            modifier = Modifier
                                .background(Color.White, RoundedCornerShape(32.dp))
                        ) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 16.dp)
                                    .size(width = 200.dp, height = 5.dp)
                                    .background(Color.Gray, RoundedCornerShape(50))
                                    .clickable { isExpanded = !isExpanded }
                                    .align(Alignment.CenterHorizontally)
                            )

                            Row(
                                modifier = Modifier.background(Color.White),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                HeaderDetails(R.string.swiss_hotel, textOffset, modifier = Modifier.padding(top = 12.dp))
                                Spacer(modifier = Modifier.width(8.dp))

                                Box(
                                    modifier = Modifier
                                        .offset(x = rangeBetweenLocation)
                                        .size(48.dp)
                                        .background(Color.LightGray, RoundedCornerShape(16.dp))
                                        .clickable { /* Handle location click */ },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location",
                                        tint = Color.Black,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                            Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))

                            Text(
                                text = "211B Baker Street, London, England",
                                color = Color.Gray,
                                fontSize = 14.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                                repeat(4) {
                                    Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
                                }
                                Text(
                                    text = "4.5 - 1231 Reviews",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(start = 4.dp)
                                )
                            }

                            HotelDescriptionSection(showBackButton = 2, modifier = Modifier)
                            HotelPreviewImages(showBackButton = 2, modifier = Modifier)
                            HotelReviewsSection(showBackButton = 2, modifier = Modifier.height(500.dp))

                            Button(
                                onClick = { navController.navigate(AppScreen.BookingDate.route) },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color.Blue,
                                    contentColor = Color.White
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(8.dp)
                            ) {
                                Text(text = "Booking")
                            }
                        }
                    }
                }
            }

        } else {
            // ✅ Collapsed: Box (Static, not scrollable)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Transparent)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.hotel_images),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(700.dp),
                    )

                    Column(
                        modifier = Modifier
                            .offset(y = -100.dp)
                            .fillMaxSize()
                    ){
                        Column(
                            modifier = Modifier
                                .background(
                                    Color.White,
                                    RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                                ) // ✅ Rounded top corners
                                .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                                .fillMaxWidth()
                                .fillMaxHeight()
                                .wrapContentHeight(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ){
                            Box(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 16.dp)
                                    .size(width = 200.dp, height = 5.dp)
                                    .background(Color.Gray, RoundedCornerShape(50))
                                    .clickable { isExpanded = !isExpanded }
                            )

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier
                                    .offset(x = -100.dp)
                            ){
                                HeaderDetails(R.string.swiss_hotel, textOffset, modifier = Modifier)
                                Spacer(modifier = Modifier.width(8.dp))

                                Box(
                                    modifier = Modifier
                                        .offset(x = rangeBetweenLocation)
                                        .size(48.dp)
                                        .background(Color.LightGray, RoundedCornerShape(16.dp))
                                        .clickable { /* Handle location click */ },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.LocationOn,
                                        contentDescription = "Location",
                                        tint = Color.Black,
                                        modifier = Modifier.size(28.dp)
                                    )
                                }
                            }

                            Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))

                            Column(
                                modifier = Modifier
                                    .offset(x = -64.dp)
                            ){
                                Text(
                                    text = "211B Baker Street, London, England",
                                    color = Color.Gray,
                                    fontSize = 14.sp,
                                    modifier = Modifier.padding(horizontal = 16.dp)
                                )

                                Row(modifier = Modifier.padding(horizontal = 16.dp)) {
                                    repeat(4) {
                                        Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
                                    }
                                    Text(
                                        text = "4.5 - 1231 Reviews",
                                        color = Color.Gray,
                                        fontSize = 14.sp,
                                        modifier = Modifier.padding(start = 4.dp)
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(100.dp))

                            HotelDescriptionSection(showBackButton = 2, modifier = Modifier)
                            

                        }
                    }
                }
            }
        }
    }
}


@Composable
fun HotelScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.LightGray) // Background for visualization
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Box(modifier = Modifier.fillMaxWidth()) {
                    // 🔹 Large Image
                    Image(
                        painter = painterResource(id = R.drawable.hotel_images),
                        contentDescription = "Hotel Image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(800.dp) // Large image
                    )

                    // 🔹 White Box (Overlapping the Image)
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                            .align(Alignment.BottomCenter) // ✅ Overlaps the image correctly
                            .padding(top = 16.dp, bottom = 32.dp)
                    ) {
                        Spacer(modifier = Modifier.height(16.dp)) // 🔹 Push content down

                        Text(
                            text = "Hotel Details",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(horizontal = 16.dp)
                        )

                        Button(
                            onClick = { /* Navigate to booking */ },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(text = "Book Now")
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHotelScreen() {
    HotelScreen()
}
