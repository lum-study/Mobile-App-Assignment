package com.bookblitzpremium.upcomingproject.ui.screen.hotel

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bookblitzpremium.upcomingproject.R
import com.bookblitzpremium.upcomingproject.ui.theme.AppTheme
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRestorer
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.setValue
import com.bookblitzpremium.upcomingproject.common.enums.PaymentMethod
import com.bookblitzpremium.upcomingproject.ui.screen.payment.PaymentOptionScreen

@Composable
fun NumberRoom(

) {
    val configuration = LocalConfiguration.current
    val screenWidthDp = configuration.screenWidthDp.dp
    val isPhone = screenWidthDp < 500.dp  // Any device below 600dp is considered a phone
    val isTablet = screenWidthDp > 800.dp // Tablets start at 600dp

    Column(
        modifier = Modifier
            .padding( start = if (isPhone) 0.dp else 800.dp)
    ){
        Box(
            modifier = Modifier
                .fillMaxWidth(if (isTablet) 0.4f else 1f)
                .fillMaxHeight(if (isPhone) 0.6f else 0.63f)
                .padding(top = if (isPhone) 240.dp else 140.dp)
                .padding(horizontal = if (isPhone) 50.dp else 70.dp)
                .background(Color.LightGray, RoundedCornerShape(32.dp))

        ) {
            Column(
                verticalArrangement = spacedBy(12.dp),
                modifier = Modifier
                    .align(Alignment.TopCenter) // Center the Column vertically

            ) {
                Row(
                    modifier = Modifier
                        .padding(top = 50.dp, start = 30.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, // Align items vertically
                ) {
                    // "Number of Room" Text
                    Text(
                        text = "Number of Room",
                        style = AppTheme.typography.labelMedium
                    )

                    Row(
                        modifier = Modifier
                            .padding(start = 50.dp)
                    ){
                        // Left Arrow
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Decrease room count",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { /* Handle decrease action */ }
                        )

                        // Number Text
                        Text(
                            text = "1",
                            fontSize = 16.sp, // Consistent font size
                            modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for spacing
                        )

                        // Right Arrow
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Increase room count",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { /* Handle increase action */ }
                        )
                    }
                }


                Row(
                    modifier = Modifier
                        .padding(top = 50.dp, start = 30.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, // Align items vertically
                ) {
                    // "Number of Room" Text
                    Text(
                        text = "Adult",
                        style = AppTheme.typography.labelMedium
                    )

                    Row(
                        modifier = Modifier
                            .padding(start = 130.dp)
                    ){
                        // Left Arrow
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Decrease room count",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { /* Handle decrease action */ }
                        )

                        // Number Text
                        Text(
                            text = "1",
                            fontSize = 16.sp, // Consistent font size
                            modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for spacing
                        )

                        // Right Arrow
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Increase room count",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { /* Handle increase action */ }
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .padding(top = 50.dp, start = 30.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically, // Align items vertically
                ) {
                    // "Number of Room" Text
                    Text(
                        text = "Child",
                        style = AppTheme.typography.labelMedium
                    )

                    Row(
                        modifier = Modifier
                            .padding(start = 130.dp)
                    ){
                        // Left Arrow
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Decrease room count",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { /* Handle decrease action */ }
                        )

                        // Number Text
                        Text(
                            text = "1",
                            fontSize = 16.sp, // Consistent font size
                            modifier = Modifier.padding(horizontal = 16.dp) // Add horizontal padding for spacing
                        )

                        // Right Arrow
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowRight,
                            contentDescription = "Increase room count",
                            tint = Color.Black,
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { /* Handle increase action */ }
                        )
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = false)
@Composable
fun PreviewHotels(){
    HotelCard()
}

@Composable
fun HotelCard() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        // Row containing Image, Back Arrow, and Text
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.8f)
        ) {
            // Background Image
            Image(
                painter = painterResource(id = R.drawable.hotel_images),
                contentDescription = "Cottage by lake",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
            )

            // Back Arrow
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color.White,
                modifier = Modifier
                    .padding(16.dp)
                    .size(24.dp)
                    .clickable { /* Handle back navigation */ }
            )

            // Text Overlay
            Text(
                text = "Nordic Cottage",
                style = TextStyle(
                    color = Color.White,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        blurRadius = 4f
                    )
                ),
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(top = 200.dp)
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.White, RoundedCornerShape(32.dp)) // White background
            ,contentPadding = PaddingValues(16.dp)
        ) {
            // Row 1: Hotel Details
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Hotel Details Column
                    Column(
                        verticalArrangement = spacedBy(4.dp),
                        modifier = Modifier
                            .weight(1f) // Takes remaining space
                            .padding(start = 8.dp, top = 8.dp)
                    ) {
                        Text(
                            text = "Swiss Hotel",
                            color = Color.Black,
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = "211B Baker Street, London, England",
                            color = Color.LightGray,
                            fontSize = 14.sp
                        )

                        Row {
                            repeat(4) {
                                Text(
                                    text = "⭐",
                                    color = Color.Yellow,
                                    fontSize = 16.sp
                                )
                            }

                            Text(
                                text = "4.5-1231 Reviews",
                                color = Color.LightGray,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(top = 1.dp)
                            )
                        }

                    }

                    // Back Arrow Icon
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Back",
                        tint = Color.Black,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(24.dp)
                            .clickable { /* Handle back navigation */ }
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))

            }

            // Row 2: Description
            item {
                Column(
                    verticalArrangement = spacedBy(6.dp),
                    modifier = Modifier
                        .padding(start = 8.dp,top = 4.dp)
                ){
                    Text(
                        text = "Description",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )

                    Text(
                        text = "Hotel Room means an area that is designed and constructed to be occupied by one or more persons on Hotel Property, which is separate",
                        color = Color.Black,
                        fontSize = 14.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "Read more",
                        color = Color.Blue,
                        fontSize = 14.sp
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Row 3: Preview
            item {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp,top = 4.dp)
                ) {
                    Text(
                        text = "Preview",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Row(
                        horizontalArrangement = spacedBy(4.dp),
                        modifier = Modifier
                            .padding(top = 4.dp)
                    ){
                        repeat(3){
                            Image(
                                painter = painterResource(id = R.drawable.hotel_images),
                                contentDescription = "Hotel preview",
                                modifier = Modifier
                                    .size(80.dp)
                                    .clip(RoundedCornerShape(8.dp))
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
            }

            // Row 4: Horizontally Scrollable Reviews
            item {
                Column(
                    modifier = Modifier
                        .padding(start = 8.dp,top = 4.dp)
                ){
                    Text(
                        text = "Reviews",
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        contentPadding = PaddingValues(horizontal = 8.dp),
                        horizontalArrangement = spacedBy(16.dp)
                    ) {

                        item{

                            Column {
                                Text(
                                    text = "Reviews",
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                LazyRow(
                                    modifier = Modifier.fillMaxWidth(),
                                    contentPadding = PaddingValues(horizontal = 8.dp),
                                    horizontalArrangement = spacedBy(16.dp)
                                ) {
                                    item {
                                        Row(
                                            modifier = Modifier
                                        ) {
                                            Image(
                                                painter = painterResource(id = R.drawable.hotel_images),
                                                contentDescription = null,
                                                modifier = Modifier.size(60.dp)
                                            )

                                            Column(
                                                modifier = Modifier
                                                    .padding(start = 12.dp, top = 8.dp),
                                                verticalArrangement = spacedBy(8.dp)
                                            ) {
                                                Text(
                                                    text = "Sasha Perry",
                                                    color = Color.LightGray,
                                                    fontSize = 14.sp,
                                                    modifier = Modifier
                                                )

                                                Text(
                                                    text = "Visitor",
                                                    color = Color.LightGray,
                                                    fontSize = 14.sp,
                                                )
                                            }

                                            Row(
                                                modifier = Modifier
                                                    .padding(start = 80.dp, top = 8.dp)
                                            ) {
                                                repeat(4) {
                                                    Text(
                                                        text = "⭐",
                                                        color = Color.Yellow,
                                                        fontSize = 16.sp
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                                // New Row with review text added below LazyRow
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(top = 8.dp)
                                ) {
                                    Text(
                                        text = "Nisi vivamus neque elementum, at pharetra. Cras gravida congue in tincidunt neque, ipsum egestas. ",
                                        color = Color.LightGray,
                                        fontSize = 14.sp,
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PaymentMethod(){
    Box(
        modifier = Modifier
            .fillMaxSize()
    ){

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 50.dp),

            ) {
                Row(
                    modifier = Modifier
                        .padding(bottom = 12.dp,start = 30.dp),

                    ){
                    Text(
                        text = "Payment Method",
                        style = AppTheme.typography.titleLarge
                    )
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.98f)
                        .padding(bottom = AppTheme.size.normal) // 16.dp spacing
                        .padding(horizontal = 28.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                            .background(Color.LightGray, RoundedCornerShape(4.dp))
                            .clickable { /* Handle click action, e.g., toggle selection */ }
                            .padding(vertical = 8.dp), // Added vertical padding for better spacing
                    ) {
                        Text(
                            text = "Touch & Go",
                            color = Color.Black,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .align(Alignment.CenterStart) // Center the text horizontally and vertically
                                .padding(horizontal = 16.dp), // Optional: Add internal horizontal padding
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth(0.98f)
                        .padding(bottom = AppTheme.size.normal) // 16.dp spacing
                        .padding(horizontal = 28.dp)
                ) {

                    Text(
                        text = "Pricing",
                        style = AppTheme.typography.titleLarge
                    )

                }

                Column(
                    modifier = Modifier
                        .padding(start = 30.dp, bottom = 30.dp)
                    , verticalArrangement = spacedBy(8.dp)
                ){
                    LazyRow {
                        item {
                            Text(
                                text = "RM160.00 x 1 nights                 RM160.00"
                            )
                        }
                    }

                    Text(
                        text = "Tax                  Rm177"
                    )
                    Text(
                        text = "Total (MYR)          RM 177"
                    )
                }

                Row(

                ){
                    Button(
                        onClick = {  },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Next",
                            style = AppTheme.typography.labelMedium
                        )
                    }
                }
        }
    }
}

//@Preview(showBackground = true)


//@Composable
//fun TravelHeaderTable(){
//
//    var selectedTabIndex by remember { mutableStateOf(0) }
//
//    // List of tab items
//    val tabs = listOf("Home", "Flights", "Hotels")
//
//    // List of icons (optional)
//    val tabIcons = listOf(Icons.Default.DateRange, Icons.Default.LocationOn, Icons.Default.Person)
//
//    Row( // ✅ Use Row directly (not Box)
//        modifier = Modifier.fillMaxSize() // ✅ Ensures it takes the full screen
//    ) {
//        // Left Side (Red)
//
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxHeight()
//                .fillMaxWidth(0.5f)
//                .background(Color.White)
//        ){
//            item{
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .fillMaxHeight(0.7f)
//                        .paint(
//                            painterResource(id = R.drawable.hotel_images),
//                            contentScale = ContentScale.Crop
//                        )
//                )
//            }
//
//            item{
//
//
//                Text(
//                    text = "ddgdfgdfhfhfghgfhfghfh",
//                    fontSize = 16.sp,
//                    color = Color.Black
//                )
//            }
//        }
//
//
//        Column(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxHeight()
//                .background(Color.White, RoundedCornerShape(32.dp))
//        ){
//            Column {
//
//            }
//            TabRow(
//                selectedTabIndex = selectedTabIndex,
//                containerColor = Color(rgb(233,233,233)), // Background color
//                contentColor = Color.Black, // Text/icon color
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .height(56.dp)
//            ) {
//                tabs.forEachIndexed { index, title ->
//                    Tab(
//                        selected = selectedTabIndex == index,
//                        onClick = { selectedTabIndex = index },
//                        text = {
//                            Text(
//                                text = title,
//                                fontSize = 16.sp,
//                                color = if (selectedTabIndex == index) Color.White else Color.Gray
//                            )
//                        },
//                        icon = {
//                            Icon(
//                                imageVector = tabIcons[index],
//                                contentDescription = title,
//                                tint = if (selectedTabIndex == index) Color.White else Color.Gray
//                            )
//                        },
//                        selectedContentColor = Color.White,
//                        unselectedContentColor = Color.Gray
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//
//@Composable
//fun TabContent(text: String) {
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .padding(16.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        Text(
//            text = text,
//            fontSize = 20.sp,
//            color = Color.Black
//        )
//    }
//}

//@Composable
//fun HotelHeaderTable( onNextButtonClicked: () -> Unit ) {
//    var showNewComponent by remember { mutableStateOf(false) } // ✅ State for toggling components
//
//    Row(
//        modifier = Modifier.fillMaxSize()
//    ) {
//        // Left Side (Static)
//        Box(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxHeight()
//                .paint(
//                    painterResource(id = R.drawable.hotel_images),
//                    contentScale = ContentScale.Crop
//                )
//        ) {
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .background(Color.Transparent)
//                    .align(Alignment.BottomEnd)
//                    .padding(horizontal = 16.dp, vertical = 30.dp)
//            ) {
//                Button(
//                    onClick = {},
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Black,
//                        contentColor = Color.White
//                    ),
//                    shape = RoundedCornerShape(16.dp),
//                    modifier = Modifier
//                        .width(300.dp)
//                        .padding(end = 20.dp)
//                ) {
//                    Text(text = "Pick Date", style = AppTheme.typography.mediumBold)
//                }
//
//                Button(
//                    onClick = {},
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = Color.Black,
//                        contentColor = Color.White
//                    ),
//                    shape = RoundedCornerShape(16.dp),
//                    modifier = Modifier.width(300.dp)
//                ) {
//                    Text(text = "Pick Date", style = AppTheme.typography.mediumBold)
//                }
//            }
//        }
//
//        // Right Side (Dynamic)
//        Column(
//            modifier = Modifier
//                .weight(1f)
//                .fillMaxHeight()
//                .clip(RoundedCornerShape(32.dp))
//                .background(Color.White)
//        ) {
//            if (showNewComponent) {
//                // ✅ Render new component when button is clicked
//                PaymentDetails()
//            } else {
//                // ✅ Default components
//                LazyColumn {
//                    item { HotelInfoSection(showBackButton = 1, modifier = Modifier) }
//                    item { HotelDescriptionSection(showBackButton = 1, modifier = Modifier) }
//                    item { HotelPreviewImages(showBackButton = 1, modifier = Modifier) }
//                    item { HotelReviewsSection(showBackButton = 1, modifier = Modifier) }
//                    item {
//                        Button(
//                            colors = ButtonDefaults.buttonColors(
//                                containerColor = Color.Black,
//                                contentColor = Color.White
//                            ),
//                            shape = RoundedCornerShape(16.dp),
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp),
//                            onClick = { showNewComponent = true } // ✅ Update state on click
//                        ) {
//                            Text(text = "Next")
//                        }
//                    }
//                    item { Spacer(modifier = Modifier.height(100.dp)) }
//                }
//            }
//        }
//    }
//}


@Composable
fun NewComponent() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "New Component Loaded!",
            style = MaterialTheme.typography.headlineLarge,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { /* Navigate or perform action */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Blue,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Proceed")
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewTest(){
//    HotelHeader(showBackButton = 1)
//}
//
//@Composable
//fun HotelHeader(showBackButton: Int) {
//    val heightSize: Modifier = if (showBackButton == 1) Modifier.fillMaxSize() else Modifier.height(500.dp)
//    val pictureSize: Modifier = if (showBackButton == 1) Modifier.fillMaxSize() else Modifier.height(550.dp)
//
//    Box(
//        modifier = Modifier
//            .background(Color.White)
//    ){
//        Image(
//            painter = painterResource(id = R.drawable.hotel_images),
//            contentDescription = "Hotel Image",
//            contentScale = ContentScale.Crop, // ✅ Crops and fills the box
//            modifier = Modifier.fillMaxSize() // ✅ Fills the Box
//        )
//
//        Icon(
//            imageVector = Icons.Default.ArrowBack,
//            contentDescription = "Back",
//            tint = Color.White,
//            modifier = Modifier
//                .padding(16.dp)
//                .size(32.dp)
//                .clickable { /* Handle back navigation */ }
//                .align(Alignment.TopStart)
//        )
//
//    }
//}
//
//@Composable
//fun HeaderDetails( textResId: Int , offsetX :Dp,modifier: Modifier ){
//    Text(
//        text = stringResource(id = textResId),
//        color = Color.Black,
//        style = AppTheme.typography.largeBold,
//        modifier = Modifier
//            .padding(top = 8.dp)
//            .offset(x = offsetX)
//    )
//}

//@Composable
//fun MobileLayout( showNUmber:Int , defaultSize : Dp ,maxSize:Dp) {
//
//    var topHeight by remember { mutableStateOf(defaultSize) } // 🔹 Shared state for top section
//    val minHeight = 100.dp // 🔹 Minimum height (almost hides)
//    val maxHeight = maxSize // 🔹 Maximum height
//    val dragSpeedFactor = 0.3f // 🔹 Slow down dragging effect
//
//    Column(
//        modifier = Modifier.fillMaxSize()
//    ) {
//
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(topHeight) // 🔹 Dynamically adjust height
//                .background(Color.Black)
//        ) {
//            Image(
//                painter = painterResource(id = R.drawable.hotel_images),
//                contentDescription = "Hotel Image",
//                contentScale = ContentScale.Crop,
//                modifier = Modifier.fillMaxSize() // ✅ Fill the dynamic height
//            )
//
//            Icon(
//                imageVector = Icons.Default.ArrowBack,
//                contentDescription = "Back",
//                tint = Color.White,
//                modifier = Modifier
//                    .padding(16.dp)
//                    .size(32.dp)
//                    .clickable { /* Handle back navigation */ }
//                    .align(Alignment.TopStart)
//            )
//
//            if( showNUmber ==1 || showNUmber ==3 ){
//                Row(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .background(Color.Transparent)
//                        .align(Alignment.BottomEnd)
//                        .padding( horizontal = 48.dp, vertical = 30.dp)
//                ){
//                    Button(
//                        onClick = {},
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Black,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(16.dp),
//                        modifier = Modifier
//                            .width(300.dp)
//                            .padding(end = 20.dp)
//
//                    ){
//                        Text(
//                            text = "Pick Date",
//                            style = AppTheme.typography.mediumBold
//                        )
//                    }
//
//                    Button(
//                        onClick = {},
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Black,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(16.dp),
//                        modifier = Modifier.width(300.dp)
//                    ){
//                        Text(
//                            text = "Pick Date",
//                            style = AppTheme.typography.mediumBold
//                        )
//                    }
//                }
//            }
//        }
//
//        Column(
//            modifier = Modifier
//                .background(Color.Black)
//        ){
//
////            SelectingFigure(1)
//
//            Hello(
//                showBackButton = showNUmber,
//                topHeight = topHeight,
//                onDrag = { delta ->
//                    val scaledDelta = delta * dragSpeedFactor
//                    val newHeight = (topHeight.value + scaledDelta).dp
//                    topHeight = newHeight.coerceIn(minHeight, maxHeight)
//                }
//            )
//        }
//    }
//}


@Composable
fun Bye() {
    Text(
        text = "Bye!",
        color = Color.White,
        style = MaterialTheme.typography.headlineLarge
    )
}

//@Composable
//fun Hello(
//    showBackButton: Int,
//    topHeight: Dp,
//    onDrag: (Float) -> Unit
//) {
//    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
//    val rangeBetweenLocation = if (showBackButton == 1) 340.dp  else if (showBackButton == 2) 150.dp else 500.dp
//
//    Column(
//        modifier = Modifier
//            .fillMaxWidth()
//            .fillMaxHeight()
//            .background(Color.White, RoundedCornerShape(32.dp))
////            .verticalScroll(rememberScrollState()) // ✅ Enables scrolling
//    ) {
//        // 🔹 Draggable Divider
//        Divider(
//            modifier = Modifier
//                .align(Alignment.CenterHorizontally)
//                .fillMaxWidth(0.5f)
//                .height(6.dp)
//                .offset(y = 4.dp)
//                .clip(RoundedCornerShape(32.dp)) // ✅ Ensures shape is actually applied
//                .background(Color.LightGray) // ✅ Background color applies correctly
//                .draggable(
//                    orientation = Orientation.Vertical,
//                    state = rememberDraggableState { delta ->
//                        onDrag(delta)
//                    }
//                )
//        )
//
//
//
//        // 🔹 Hotel Title & Location Row
//        Row(verticalAlignment = Alignment.CenterVertically) {
//
//            HeaderDetails(R.string.swiss_hotel, textOffset, modifier = Modifier.padding(top = 12.dp))
//
//            Spacer(modifier = Modifier.width(8.dp))
//            Box(
//                modifier = Modifier
//                    .offset(x = rangeBetweenLocation)
//                    .size(48.dp)
//                    .background(Color.LightGray, RoundedCornerShape(16.dp))
//                    .clickable { /* Handle location click */ },
//                contentAlignment = Alignment.Center
//            ) {
//                Icon(
//                    imageVector = Icons.Default.LocationOn,
//                    contentDescription = "Location",
//                    tint = Color.Black,
//                    modifier = Modifier.size(28.dp)
//                )
//            }
//        }
//
//        Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.padding(vertical = 8.dp))
//
//        // 🔹 Hotel Address
//        Text(
//            text = "211B Baker Street, London, England",
//            color = Color.Gray,
//            fontSize = 14.sp,
//            modifier = Modifier.offset(x = textOffset)
//        )
//
//        // 🔹 Star Ratings
//        Row(modifier = Modifier.offset(x = textOffset)) {
//            repeat(4) {
//                Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
//            }
//            Text(
//                text = "4.5 - 1231 Reviews",
//                color = Color.Gray,
//                fontSize = 14.sp,
//                modifier = Modifier.padding(start = 4.dp)
//            )
//        }
//
////        SelectingFigure(1)
//        ConstraintLayout(
//            modifier = Modifier
//                .fillMaxWidth()
//                .wrapContentHeight()
//        ) {
//            val (content) = createRefs()
//
//            Column(
//                modifier = Modifier
//                    .constrainAs(content) {
//                        top.linkTo(parent.top)
//                        start.linkTo(parent.start)
//                        end.linkTo(parent.end)
//                    }
//                    .fillMaxWidth()
//                    .verticalScroll(rememberScrollState())
//                    .wrapContentHeight()
//            ) {
//                HotelDescriptionSection(showBackButton = 2 , modifier = Modifier)
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                HotelPreviewImages(showBackButton = 2, modifier = Modifier)
//
//                Spacer(modifier = Modifier.height(16.dp))
//
//                HotelReviewsSection(showBackButton = 2, modifier = Modifier)
//            }
//        }
//    }
//}
//
//
//@Composable
//fun HotelInfoSection(showBackButton: Int , modifier: Modifier) {
//
//    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
//    val rangeBetweenLocation = if (showBackButton == 1) 340.dp  else if (showBackButton == 2) 150.dp else 500.dp
//
//    Box(
//        modifier = Modifier
//            .background(Color.White , RoundedCornerShape(32.dp)) //
//    ){
//        Row(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(horizontal = 0.dp, vertical = 8.dp),
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            Column(
//                verticalArrangement = Arrangement.spacedBy(8.dp),
//                modifier = Modifier
//            ) {
//
//                if(showBackButton == 2){
//                    Divider(
//                        modifier = Modifier
//                            .offset(x = 100.dp, y = -4.dp)
//                            .fillMaxWidth(0.5f)
//                            .height(4.dp)
//                            .background(Color.Red)
//                            .shadow(
//                                elevation = 0.dp,
//                                shape = RoundedCornerShape(32.dp),
//                                clip = true,
//                                ambientColor = Color.LightGray,
//                            )
//                    )
//                } else if (showBackButton != 2 && showBackButton!= 1){
//                    Divider(
//                        modifier = Modifier
//                            .offset(x = 260.dp, y = -4.dp)
//                            .fillMaxWidth(0.3f)
//                            .height(4.dp)
//                            .background(Color.LightGray)
//                            .shadow(
//                                elevation = 0.dp,
//                                shape = RoundedCornerShape(32.dp),
//                                clip = true,
//                                ambientColor = Color.LightGray,
//                            )
//                    )
//                }
//
//                Row(
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    HeaderDetails(R.string.swiss_hotel, textOffset, modifier = Modifier)
//
//                    Spacer(modifier = Modifier.width(8.dp))
//
//                    Box(
//                        modifier = Modifier
//                            .offset(x = rangeBetweenLocation)
//                            .size(48.dp)
//                            .background(Color.LightGray, RoundedCornerShape(16.dp))
//                            .clickable { /* Handle location click */ },
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Icon(
//                            imageVector = Icons.Default.LocationOn,
//                            contentDescription = "Location",
//                            tint = Color.Black,
//                            modifier = Modifier.size(28.dp)
//                        )
//                    }
//                }
//
//                Divider(
//                    color = Color.Gray,
//                    thickness = 2.dp,
//                    modifier = Modifier.padding(vertical = 8.dp)
//                )
//
//                Text(
//                    text = "211B Baker Street, London, England",
//                    color = Color.Gray,
//                    fontSize = 14.sp,
//                    modifier = Modifier.offset(x = textOffset)
//                )
//
//                Row(modifier = Modifier.offset(x = textOffset)) {
//                    repeat(4) {
//                        Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
//                    }
//                    Text(
//                        text = "4.5 - 1231 Reviews",
//                        color = Color.Gray,
//                        fontSize = 14.sp,
//                        modifier = Modifier.padding(start = 4.dp)
//                    )
//                }
//            }
//        }
//    }
//}
//
//
//@Composable
//fun HotelDescriptionSection(showBackButton: Int, modifier: Modifier) {
//
//    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
//
//    Column(
//        verticalArrangement = spacedBy(6.dp),
//        modifier = Modifier.padding( top = 16.dp)
//    ) {
//        HeaderDetails(R.string.description, textOffset, modifier = Modifier)
//
//        Text(
//            text = "Hotel Room means an area designed for occupancy, located in the Hotel Property...",
//            color = Color.Black,
//            fontSize = 14.sp,
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis,
//            modifier = Modifier
//                .offset(x = textOffset)
//        )
//        Text(
//            text = "Read more",
//            color = Color.Blue,
//            fontSize = 14.sp,
//            modifier = Modifier
//                .offset(x = textOffset)
//                .clickable {}
//        )
//    }
//}
//
//@Composable
//fun HotelPreviewImages(showBackButton: Int,modifier: Modifier) {
//    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
//
//    Column(modifier = Modifier.padding( top = 16.dp, end = 16.dp)) {
//
//        HeaderDetails(R.string.preview, textOffset,modifier = Modifier)
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        LazyRow(
//            horizontalArrangement = spacedBy(8.dp),
//            modifier = Modifier
//                .fillMaxWidth()
//                .fillMaxHeight()
//                .padding(start = 16.dp)
//        ) {
//            items(4) {
//                Box(
//                    modifier = Modifier
//                        .size(206.dp)
//                        .background(Color.LightGray, RoundedCornerShape(16.dp))
//                        .padding(12.dp) // Padding inside the Box
//                ) {
//                    Column(
//                        modifier = Modifier
//                            .fillMaxSize(),
//
//                    ) {
//                        Image(
//                            painter = painterResource(id = R.drawable.hotel_images),
//                            contentDescription = "Hotel preview",
//                            modifier = Modifier
//                                .fillMaxWidth() // Ensures it fills available width
//                                .height(150.dp) // Adjust height to leave space for text
//                                .clip(RoundedCornerShape(8.dp))
//                        )
//
//                        // Directly placing the Text without a Row
//                        Text(
//                            text = "Hotels hghggf",
//                            fontSize = 16.sp,
//                            fontWeight = FontWeight.Bold,
//                            color = Color.Black, // Change to black for better visibility
//                            modifier = Modifier
//                                .padding(8.dp)
//                                .align(Alignment.Start) // Align text to start
//                        )
//                    }
//                }
//            }
//        }
//    }
//}
//
//@Composable
//fun HotelReviewsSection(showBackButton: Int,modifier: Modifier) {
//    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
//    Column(modifier = Modifier.padding(top = 16.dp)) {
//        HeaderDetails(R.string.reviews , textOffset,modifier = Modifier)
//        Spacer(modifier = Modifier.height(8.dp))
//
//        LazyRow(
//            modifier = Modifier.fillMaxWidth(),
//            contentPadding = PaddingValues(horizontal = 8.dp),
//            horizontalArrangement = spacedBy(16.dp)
//        ) {
//            items(5) {
//                ReviewItem(showBackButton )
//            }
//        }
//    }
//}
//
//
//
//@Composable
//fun ReviewItem(showBackButton: Int,) {
//    val textOffset = if (showBackButton == 1) 24.dp  else if (showBackButton ==2) 24.dp else 24.dp
//    Column(
//        modifier = Modifier
//            .offset(x = textOffset)
//            .fillMaxWidth()
//            .padding(end = 16.dp)
//    ) {
//        Row {
//            Image(
//                painter = painterResource(id = R.drawable.hotel_images),
//                contentDescription = null,
//                modifier = Modifier.size(60.dp)
//            )
//
//            Column(
//                modifier = Modifier.padding(start = 12.dp, top = 8.dp),
//                verticalArrangement = spacedBy(8.dp)
//            ) {
//                Text(text = "Sasha Perry", color = Color.Gray, fontSize = 14.sp)
//                Text(text = "Visitor", color = Color.Gray, fontSize = 14.sp)
//            }
//
//            Row(modifier = Modifier.padding(start = 80.dp, top = 8.dp)) {
//                repeat(4) {
//                    Text(text = "⭐", color = Color.Yellow, fontSize = 16.sp)
//                }
//            }
//        }
//
//        Text(
//            text = "Hotel Room means an area designed for occupancy, located in the Hotel Property...",
//            color = Color.Black,
//            fontSize = 14.sp,
//            maxLines = 2,
//            overflow = TextOverflow.Ellipsis,
//            modifier = Modifier.padding(top = 16.dp)
//        )
//    }
//}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InlineDateRangePicker(onDateRangeSelected: (Pair<Long?, Long?>) -> Unit) {
    val dateRangePickerState = rememberDateRangePickerState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center // Center like a dialog
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "Select date range",
                    style = MaterialTheme.typography.titleMedium
                )

                DateRangePicker(
                    state = dateRangePickerState,
                    showModeToggle = false,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(500.dp)
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = {
                        onDateRangeSelected(
                            Pair(
                                dateRangePickerState.selectedStartDateMillis,
                                dateRangePickerState.selectedEndDateMillis
                            )
                        )
                    }) {
                        Text("OK")
                    }
                }
            }
        }
    }
}

//@Preview(showBackground = true, widthDp = 360, heightDp = 800)
@Composable
fun SelectingFigurePreview() {
    SelectingFigure(showToggleTablet = 2, modifier = Modifier)
}

@Composable
fun SelectingFigure(showToggleTablet: Int , modifier: Modifier) {

    val maxHeight = if (showToggleTablet == 1) 1f else if (showToggleTablet ==2 ) 1f else 1f
    val maxWeight = if (showToggleTablet == 1) 0.9f else if (showToggleTablet ==2 ) 1f else 1f


    val modifierEdit = when (showToggleTablet) {
        1 -> Modifier
            .fillMaxHeight(maxHeight)
            .fillMaxWidth(maxWeight)
            .padding(16.dp)
            .offset(x = 12.dp)

        2 -> Modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .background(Color.LightGray)
            .padding(top = 200.dp)

        else -> Modifier
            .fillMaxHeight(maxHeight)
            .fillMaxWidth(maxWeight)
            .padding(16.dp)
    }

    Box(
          modifier = modifierEdit
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White,RoundedCornerShape( topStart = 32.dp , topEnd = 32.dp)),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {


            if (showToggleTablet == 2) {
//                Divider(
//                    modifier = Modifier
//                        .offset(x = 100.dp, y = -4.dp)
//                        .fillMaxWidth(0.5f)
//                        .height(4.dp)
//                        .background(Color.Red)
//                        .shadow(
//                            elevation = 0.dp,
//                            shape = RoundedCornerShape(32.dp),
//                            clip = true,
//                            ambientColor = Color.LightGray,
//                        )
//                )
            }



            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 18.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {

                IconButton(onClick = { /* Handle click */ }) {
                    Icon(
                        imageVector = Icons.Filled.Close,
                        contentDescription = "Close",
                        tint = Color.Black,
                        modifier = Modifier.size(30.dp)
                    )
                }

                Text(
                    text = "Figure",
                    style = AppTheme.typography.largeBold,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center, modifier = Modifier
                        .padding(start = 90.dp)
                )

//                if (showToggleTablet == 2) {
//                    Button(
//                        onClick = { /* do something */ },
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color.Black,
//                            contentColor = Color.White
//                        ),
//                        shape = RoundedCornerShape(16.dp),
//                        modifier = Modifier.width(100.dp)
//                    ) {
//                        Text(
//                            text = "Done",
//                            fontSize = 16.sp
//                        )
//                    }
//                }
            }

            // This Column will be placed at the bottom
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SelectNumber()

            }

            Column(
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Bottom, // ✅ Pushes content to the bottom
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(), // ✅ Make Row span full width
                    horizontalArrangement = Arrangement.SpaceBetween // ✅ Space buttons out
                ) {
                    Button(
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Black,
                            contentColor = Color.White
                        ),
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.width(120.dp)
                    ) {
                        Text(text = "Back")
                    }

                    IconButton(onClick = { /* Handle click */ }) {
                        Icon(
                            imageVector = Icons.Filled.KeyboardArrowRight,
                            contentDescription = "Chevron Right",
                            tint = Color.Black,
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }

            var selectedDateRange by remember { mutableStateOf<Pair<Long?, Long?>>(null to null) }
            var showRangeModal by remember { mutableStateOf(false) }

            Column {
                InlineDateRangePicker(onDateRangeSelected = {
                    selectedDateRange = it
                    showRangeModal = false
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectNumber() {
    var state by remember { mutableStateOf(0) }
    val titles = listOf("Tab 1", "Tab 2")

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp, horizontal = 24.dp,),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        // ✅ Tab Row with better styling
        TabRow(
            selectedTabIndex = state,
            containerColor = Color(0xFFF0F0F0), // ✅ Background color for tabs
            contentColor = Color.Black,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier
                        .tabIndicatorOffset(tabPositions[state])
                        .height(4.dp) // ✅ Custom indicator height
                        .clip(RoundedCornerShape(16.dp)),
                    color = Color.Black // ✅ Indicator color
                )
            },
            modifier = Modifier
                .padding(bottom = 40.dp)
        ) {
            titles.forEachIndexed { index, title ->
                Tab(
                    selected = state == index,
                    onClick = { state = index },
                    text = {
                        Text(
                            text = title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                )
            }
        }

        // ✅ Conditional Content Based on Tab Selection
        if (state == 1) {
            Column {
                Text(
                    text = "Adult",
                    fontSize = 24.sp,
                    modifier = Modifier
                        .align(Alignment.Start)

                )

                // ✅ Corrected LazyRow Implementation
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(count = 10) { index -> // ✅ Fix: Properly generate 10 items
                        Box(
                            modifier = Modifier
                                .size(40.dp) // ✅ Increased size for better touchability
                                .background(Color.LightGray, RoundedCornerShape(12.dp))
                                .clickable { /* Handle click */ },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "${index + 1}",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }
        } else {
            Text(
                text = "Hello",
                fontSize = 20.sp,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}





//@Preview(showBackground = true)
@Composable
fun SelectPeople() {
    Column( // ✅ Changed from Box to Column to stack elements properly
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 50.dp)
    ) {
        // 🔹 Title
        Row(
            modifier = Modifier.padding(bottom = 12.dp, start = 30.dp)
        ) {
            Text(
                text = "Select People",
                style = AppTheme.typography.titleLarge
            )
        }

        // 🔹 Date Selection Boxes
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp) // ✅ Added spacing between items
                    .background(Color.LightGray, RoundedCornerShape(4.dp))
                    .clickable { /* Handle click action */ }
                    .padding(vertical = 12.dp)

            ) {
                Text(
                    text = "Date",
                    color = Color.Black,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}


//@Composable
//fun HotelBookings(){
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//    ){
//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = 50.dp),
//
//            ) {
//            Row(
//                modifier = Modifier
//                    .padding(bottom = 12.dp,start = 30.dp),
//
//                ){
//                Text(
//                    text = "Date",
//                    style = AppTheme.typography.titleLarge
//                )
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(0.98f)
//                    .padding(bottom = AppTheme.size.normal) // 16.dp spacing
//                    .padding(horizontal = 28.dp)
//            ) {
////                TextField(
////                    value = "Single Bed",
////                    onValueChange = { "Single Bed" },
////                    label = { Text("Single Bed") },
////                    placeholder = { Text("Enter your text here...") },
////                    modifier = Modifier
////                        .width(400.dp)
////                )
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp)
//                        .background(Color.LightGray, RoundedCornerShape(4.dp))
//                        .clickable { /* Handle click action, e.g., toggle selection */ }
//                        .padding(vertical = 8.dp), // Added vertical padding for better spacing
//                ) {
//                    Text(
//                        text = "Start date - End date",
//                        color = Color.Black,
//                        fontSize = 16.sp,
//                        modifier = Modifier
//                            .align(Alignment.CenterStart) // Center the text horizontally and vertically
//                            .padding(horizontal = 16.dp), // Optional: Add internal horizontal padding
//                    )
//                }
//            }
//
//            Row(
//                modifier = Modifier
//                    .padding(bottom = 12.dp,start = 30.dp),
//            ){
//                Text(
//                    text = "Room Type",
//                    style = AppTheme.typography.titleLarge
//                )
//            }
//
//            Row(
//                modifier = Modifier
//                    .fillMaxWidth(0.98f)
//                    .padding(bottom = AppTheme.size.normal) // 16.dp spacing
//                    .padding(horizontal = 28.dp)
//            ) {
////                TextField(
////                    value = "Single Bed",
////                    onValueChange = { "Single Bed" },
////                    label = { Text("Single Bed") },
////                    placeholder = { Text("Enter your text here...") },
////                    modifier = Modifier
////                        .width(400.dp)
////                )
//
//                Box(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp)
//                        .background(Color.LightGray, RoundedCornerShape(4.dp))
//                        .clickable { /* Handle click action, e.g., toggle selection */ }
//                        .padding(vertical = 8.dp), // Added vertical padding for better spacing
//                ) {
//                    Text(
//                        text = "Single Bed",
//                        color = Color.Black,
//                        fontSize = 16.sp,
//                        modifier = Modifier
//                            .align(Alignment.CenterStart) // Center the text horizontally and vertically
//                            .padding(horizontal = 16.dp), // Optional: Add internal horizontal padding
//                    )
//                }
//            }
//
//            Row(
//
//            ){
//                Button(
//                    onClick = { /* do something */ },
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(horizontal = 20.dp)
//                ) {
//                    Text(
//                        text = "Next",
//                        style = AppTheme.typography.labelMedium
//                    )
//                }
//            }
//        }
//    }
//}