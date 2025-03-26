package com.bookblitzpremium.upcomingproject.ui.screen.hotel

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
fun MobileLayout(showNUmber:Int, defaultSize : Dp, maxSize: Dp) {

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

            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .size(32.dp)
                    .clickable { /* Handle back navigation */ }
                    .align(Alignment.TopStart)
            )

            if( showNUmber ==1 || showNUmber ==3 ){
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.Transparent)
                        .align(Alignment.BottomEnd)
                        .padding( horizontal = 48.dp, vertical = 30.dp),
                    horizontalArrangement = Arrangement.spacedBy(20.dp)
                ){
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = "Pick Date",
                            style = AppTheme.typography.mediumBold
                        )
                    }

                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.weight(1f)
                    ){
                        Text(
                            text = "Pick Date",
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

@Preview(showBackground = true, widthDp = 360, heightDp = 806)
@Composable
fun TestPreview(){
//    HotelHeaderTable {
//        Log.d("", "")
//    }
    MobileLayout(1,300.dp,500.dp)
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
            .background(Color.White, RoundedCornerShape(topStart = 32.dp, topEnd = 32.dp))
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

//        SelectingFigure(1)
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            val (content) = createRefs()

            Column(
                modifier = Modifier
                    .constrainAs(content) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .wrapContentHeight()
            ) {
                HotelDescriptionSection(showBackButton = 2 , modifier = Modifier)

                Spacer(modifier = Modifier.height(16.dp))

                HotelPreviewImages(showBackButton = 2, modifier = Modifier)

                Spacer(modifier = Modifier.height(16.dp))

                HotelReviewsSection(showBackButton = 2, modifier = Modifier)
            }
        }
    }
}


@Composable
fun HotelInfoSection(showBackButton: Int , modifier: Modifier) {

    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
    val rangeBetweenLocation = if (showBackButton == 1) 340.dp  else if (showBackButton == 2) 150.dp else 500.dp

    Box(
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
    Column(modifier = Modifier.padding(top = 16.dp)) {
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