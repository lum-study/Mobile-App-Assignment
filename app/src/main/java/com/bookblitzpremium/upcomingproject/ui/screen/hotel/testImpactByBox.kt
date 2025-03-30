package com.bookblitzpremium.upcomingproject.ui.screen.hotel

import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ui.components.HeaderDetails
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme

@Composable
fun ImageShow(){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(700.dp)
            .paint(
                painterResource(id = R.drawable.hotel_images),
                contentScale = ContentScale.Crop
            )
    ){

    }
}

@Preview(showBackground = true , widthDp = 500 , heightDp = 1000)
@Composable
fun PreviewMobileLayout(){
    RoundedBottomSheet()
}



@Composable
fun MobieLayout1(showNumber: Int, defaultSize: Dp, maxSize: Dp) {
    var topHeight by remember { mutableStateOf(defaultSize) } // 🔹 Controls the height of top section
    val minHeight = 100.dp // 🔹 Minimum height (almost hides)
    val maxHeight = maxSize // 🔹 Maximum height

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 100.dp) // ✅ Ensures space at the bottom
    ) {
       item{
           Image(
               painter = painterResource(id = R.drawable.hotel_images), // Change this to your image
               contentDescription = null,
               contentScale = ContentScale.Crop,
               modifier = Modifier.fillMaxSize()
           )
       }

        item{
            Column(
                modifier = Modifier
                    .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
            ){
                HotelInfoSection(showBackButton = 1, modifier = Modifier)
                HotelDescriptionSection(showBackButton = 1, modifier = Modifier)
                HotelPreviewImages(showBackButton = 1, modifier = Modifier)
                HotelReviewsSection(showBackButton = 1, modifier = Modifier)

                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Black,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    onClick = { } // ✅ Handle button click
                ) {
                    Text(text = "Next")
                }

                Spacer(modifier = Modifier.height(100.dp))
            }
        }
//        item { HotelInfoSection(showBackButton = 1, modifier = Modifier) }
//        item { HotelDescriptionSection(showBackButton = 1, modifier = Modifier) }
//        item { HotelPreviewImages(showBackButton = 1, modifier = Modifier) }
//        item { HotelReviewsSection(showBackButton = 1, modifier = Modifier) }

//        item {
//            Button(
//                colors = ButtonDefaults.buttonColors(
//                    containerColor = Color.Black,
//                    contentColor = Color.White
//                ),
//                shape = RoundedCornerShape(16.dp),
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(16.dp),
//                onClick = { } // ✅ Handle button click
//            ) {
//                Text(text = "Next")
//            }
//        }

        // ✅ Ensures content doesn't get cut off at the bottom
//        item {  }
    }
}







@Composable
fun HotelHeaderTable1( onNextButtonClicked: () -> Unit ) {
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
                    Text(text = "Pick Date", style = AppTheme.typography.mediumBold)
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
                    Text(text = "Pick Date", style = AppTheme.typography.mediumBold)
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
                            Text(text = "Next")
                        }
                    }
                    item { Spacer(modifier = Modifier.height(100.dp)) }
                }
            }
        }
    }
}

@Composable
fun DragableToTop1(
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
            .background(Color.White,RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)) // ✅ Background color

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
//            Modifier.verticalScroll(rememberScrollState())
            Modifier
        } else if (topHeight <= 250.dp) {  // ✅ Fixed comparison operator
//            Modifier.verticalScroll(rememberScrollState())
            Modifier
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
                HotelPreviewImages1(showBackButton = 2, modifier = Modifier)
//                HotelReviewsSection(showBackButton = 2, modifier = Modifier.height(500.dp))
            } else if (topHeight <= 250.dp) {  // ✅ Fixed comparison operator
                HotelPreviewImages(showBackButton = 2, modifier = Modifier)
                HotelReviewsSection(showBackButton = 2, modifier = Modifier.height(500.dp))
            } else {
                HotelPreviewImages(showBackButton = 2, modifier = Modifier)
            }


        }
    }
}


@Composable
fun HotelInfoSection1(showBackButton: Int , modifier: Modifier) {

    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
    val rangeBetweenLocation = if (showBackButton == 1) 340.dp  else if (showBackButton == 2) 150.dp else 500.dp

    Column(
        modifier = Modifier
            .background(Color.White , RoundedCornerShape(32.dp)) //
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
fun HotelDescriptionSection1(showBackButton: Int, modifier: Modifier) {

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
fun HotelPreviewImages1(showBackButton: Int,modifier: Modifier) {
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
fun HotelReviewsSection1(showBackButton: Int,modifier: Modifier) {
    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
    Column(modifier = Modifier.padding(top = 16.dp)) {
        HeaderDetails(R.string.reviews , textOffset,modifier = Modifier)
        Spacer(modifier = Modifier.height(8.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 8.dp),
            horizontalArrangement = spacedBy(16.dp)
        ) {
            items(5) {
                ReviewItem1(showBackButton )
            }
        }
    }
}



@Composable
fun ReviewItem1(showBackButton: Int,) {
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


@Composable
fun DraggableDividerExample1() {

    var topHeight by remember { mutableStateOf(300.dp) } // Initial height of top section
    val minHeight = 50.dp // Minimum height limit for the top section
    val maxHeight = 500.dp // Maximum height before the divider stops
    val dragSpeedFactor = 0.3f // 🔹 Slows down movement (smaller = slower)

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Top Section
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(topHeight)
                .background(Color.Red)
        ) {
            Text(
                text = "Top Section",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        // Draggable Divider
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp) // Thickness of the divider
                .background(Color.Gray)
                .draggable(
                    orientation = Orientation.Vertical,
                    state = rememberDraggableState { delta ->
                        val scaledDelta = delta * dragSpeedFactor // 🔹 Reduce movement speed
                        val newHeight = (topHeight.value + scaledDelta).dp
                        topHeight = newHeight.coerceIn(minHeight, maxHeight) // Restrict movement
                    }
                )
        )

        // Bottom Section
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Text(
                text = "Bottom Section",
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}


@Composable
fun RoundedBottomSheet() {
    var isExpanded by remember { mutableStateOf(false) } // ✅ Track expanded/collapsed state

    // ✅ Animate the offset when toggling
    val offsetValueChanged by animateDpAsState(
        targetValue = if (isExpanded) 250.dp else 750.dp, // ✅ 0.dp means full scrolling mode
        animationSpec = tween(durationMillis = 300)
    )

    if (isExpanded) {
        // ✅ Full Scrollable Layout (Image + Content)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ✅ Scrollable Image
            item {
                Image(
                    painter = painterResource(id = R.drawable.hotel_images),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(700.dp) // ✅ Adjust height
                )
            }

            // ✅ Content inside scrollable view
            items(30) {
                Column(
                    modifier = Modifier
                        .background(Color.Black)
                ){
                    Text(
                        text = "Your Content Here",
                        fontSize = 18.sp,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    } else {
        // ✅ Initial Static Layout (Collapsible Box)
        Box(modifier = Modifier.fillMaxSize()) {
            // ✅ Background Image (Static)
            Image(
                painter = painterResource(id = R.drawable.hotel_images),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp) // ✅ Fixed height
            )

            // ✅ White Rounded Box (Animated Offset)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .offset(y = offsetValueChanged) // ✅ Apply animated offset
                    .background(
                        Color.White.copy(alpha = 0.9f),
                        RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp)
                    )
                    .align(Alignment.BottomCenter)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ✅ Drag Handle (Click to Expand)
                    item {
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 16.dp)
                                .size(width = 40.dp, height = 5.dp)
                                .background(Color.Gray, RoundedCornerShape(50))
                                .clickable { isExpanded = true } // ✅ Click to fully expand
                        )
                    }

                    // ✅ Example Scrollable Content
                    items(30) {
                        Text(
                            text = "Your Content Here",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun OverlappingContentTest() {
    var isExpanded by remember { mutableStateOf(false) } // 🔥 Toggle state
    val offsetValue by animateDpAsState(
        targetValue = if (isExpanded) 250.dp else 700.dp, // 🔥 Moves content up
        animationSpec = tween(300)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {

        if(isExpanded){

            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
            ){
                item{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
//                            .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                            .background(Color.White)
//                            .offset(y = offsetValue) // 🔥 Moves content smoothly
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

                            )

                            // ✅ Drag Handle
                            Box(
                                modifier = Modifier
                                    .padding(top = 8.dp, bottom = 16.dp)
                                    .size(width = 40.dp, height = 5.dp)
                                    .background(Color.Gray, RoundedCornerShape(50))
                                    .clickable { isExpanded = !isExpanded } // 🔥 Click to Expand/Collapse
                            )

                            // ✅ Scrollable Content (Properly spaced)
                            repeat(50) { // 🔥 More items to test scrolling
                                Text(
                                    text = "Item $it",
                                    fontSize = 18.sp,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                )
                            }
                        }
                    }
                }
            }

        }else{

            // ✅ Background Image
            Image(
                painter = painterResource(id = R.drawable.hotel_images),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(700.dp) // 🔥 Fixed height
            )

            // ✅ Foreground Content (Scrollable when expanded)
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .offset(y = offsetValue)
                    .clip(RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
                    .background(Color.White)
                ,
            ) {
                LazyColumn(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // ✅ Drag Handle
                    item {
                        Box(
                            modifier = Modifier
                                .padding(top = 8.dp, bottom = 16.dp)
                                .size(width = 40.dp, height = 5.dp)
                                .background(Color.Gray, RoundedCornerShape(50))
                                .clickable { isExpanded = !isExpanded } // 🔥 Click to Expand/Collapse
                        )
                    }

                    // ✅ Example Content
                    items(40) {
                        Text(
                            text = "Item $it",
                            fontSize = 18.sp,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }
            }
        }
    }
}
